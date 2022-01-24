package wpy.personal.novel.novel.novel.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;
import wpy.personal.novel.base.constant.CharConstant;
import wpy.personal.novel.base.constant.StrConstant;
import wpy.personal.novel.base.enums.DictEnums;
import wpy.personal.novel.novel.novel.mapper.NovelMapper;
import wpy.personal.novel.novel.novel.mapper.SeriesMapper;
import wpy.personal.novel.novel.novel.mapper.SeriesTypeRelMapper;
import wpy.personal.novel.novel.novel.service.*;
import wpy.personal.novel.novel.system.service.SysUserRoleService;
import wpy.personal.novel.pojo.bo.SeriesBo;
import wpy.personal.novel.pojo.bo.SeriesListBo;
import wpy.personal.novel.pojo.dto.NovelDto;
import wpy.personal.novel.pojo.dto.SeriesDto;
import wpy.personal.novel.pojo.entity.*;
import wpy.personal.novel.pojo.vo.SeriesListVo;
import wpy.personal.novel.utils.FileUtils;
import wpy.personal.novel.utils.ObjectUtils;
import wpy.personal.novel.utils.StringUtils;
import wpy.personal.novel.utils.pageUtils.RequestPageUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * <p>
 * 小说表 服务实现类
 * </p>
 *
 * @author wangpanyin
 * @since 2021-09-07
 */
@Service
@Transactional
public class SeriesServiceImpl extends ServiceImpl<SeriesMapper, Series> implements SeriesService {

    @Autowired
    private SysUserRoleService sysUserRoleService;
    @Autowired
    private SeriesMapper seriesMapper;
    @Autowired
    private SeriesTypeRelMapper seriesTypeRelMapper;
    @Autowired
    private NovelService novelService;
    @Autowired
    private NovelMapper novelMapper;
    @Autowired
    private SeriesTypeRelService seriesTypeRelService;
    @Autowired
    private NovelChapterService novelChapterService;
    @Autowired
    private NovelFileService novelFileService;

    @Value("${novel.filePath.rootPath}")
    private String rootPath;
    @Value("${novel.filePath.novelImg}")
    private String novelImgPath;

    @Override
    public Page<SeriesListBo> getSeriesList(RequestPageUtils<SeriesListVo> condition, SysUser sysUser) {
        SeriesListVo param = condition.getParam();
        if(sysUserRoleService.hasRole(sysUser.getUserId(),DictEnums.ORDINARY_USER)){
            //非管理员只能看自己的
            param.setCreateBy(sysUser.getUserId());
        }
        Page<SeriesListBo> page = new Page<>(condition.getPage(), condition.getPageSize());
        List<SeriesListBo> list = this.seriesMapper.getSeriesList(param,page);
        for (SeriesListBo seriesListBo : list) {
            seriesListBo.setTypeCodeList(StringUtils.commaStrToList(seriesListBo.getTypeCodes()));
        }
        return page.setRecords(list);
    }

    @Override
    public Series addSeries(SeriesDto seriesDto, SysUser sysUser) {
        Series series = ObjectUtils.newInstance(sysUser.getUserId(),Series.class);
        ObjectUtils.copyProperties(seriesDto,series);
        series.setSeriesId(StringUtils.getUuid32());
        //上传了封面
        series.setSeriesImg(FileUtils.uploadImg(rootPath,novelImgPath,series.getSeriesId(), seriesDto.getImgFile()));

        //添加小说类型
        if(!CollectionUtils.isEmpty(seriesDto.getTypeCodeList())){
            List<SeriesTypeRel> typeList=new ArrayList<>();
            for (String typeCode : seriesDto.getTypeCodeList()) {
                SeriesTypeRel seriesTypeRel = new SeriesTypeRel();
                seriesTypeRel.setSeriesTypeRelId(StringUtils.getUuid32());
                seriesTypeRel.setSeriesId(series.getSeriesId());
                seriesTypeRel.setTypeCode(typeCode);
                typeList.add(seriesTypeRel);
            }
            seriesTypeRelService.saveBatch(typeList);
        }

        this.save(series);
        return series;
    }

    @Override
    public SeriesBo getSeriesInfo(String seriesId, SysUser sysUser) {
        SeriesBo seriesBo = new SeriesBo();
        //1、取出小说信息
        Series series = this.getById(seriesId);
        BeanUtils.copyProperties(series, seriesBo);
        //2、取出小说类型
        List<SysDictParam> typeList = seriesTypeRelMapper.getNovelTypeList(seriesId);
        seriesBo.setTypeList(typeList);
        //3、取出小说的分卷信息
        List<Novel> novelList = novelMapper.selectList(
                new QueryWrapper<Novel>().eq("novel_id", seriesId).orderByAsc("volume_order"));
        seriesBo.setNovelList(novelList);
        return seriesBo;
    }

    @Override
    public Series updateSeries(SeriesDto seriesDto, SysUser sysUser) {
        Series series = new Series();
        BeanUtils.copyProperties(seriesDto,series);
        series.setUpdateBy(sysUser.getUserId());
        series.setUpdateTime(new Date());
        //注意封面是否被修改
        series.setSeriesImg(this.updateNovelImg(series, seriesDto.getImgFile()));
        seriesTypeRelService.updateSeriesType(series.getSeriesId(), seriesDto.getTypeCodeList(),sysUser);
        this.updateById(series);
        return series;
    }

    @Override
    @Transactional
    public void deleteSeries(List<String> idList, SysUser sysUser) {
        //查询每一个文件是否有其他小说在使用他
//        List<Novel> novelList = this.novelMapper.selectList(new QueryWrapper<Novel>().in("series_id", idList));
//        List<String> deleteFileIdList = novelService.getDeleteFileIds(novelList,idList);

        //1、删除系列表
        this.removeByIds(idList);
        //2、删除分卷表
        this.novelService.remove(new QueryWrapper<Novel>().in("series_id",idList));
        //3、删除章节表
        this.novelChapterService.remove(new QueryWrapper<NovelChapter>().in("series_id",idList));
        //4、删除文件表 todo 定时任务删除
//        this.novelFileService.deleteFiles(deleteFileIdList);
        //5、删除评论表

        //6、删除数据表

        //7、删除类型关联表
        this.seriesTypeRelService.remove(new QueryWrapper<SeriesTypeRel>().eq("series_id",idList));
        //8、删除收藏表（待定）

//        //太麻烦了，还是逻辑删除吧
//        this.update(new UpdateWrapper<Novel>().set("is_delete",SqlEnums.IS_DELETE.getCode())
//                .set("update_time",new Date()).set("update_by",sysUser.getUserId())
//                .in("novel_id",idList));
    }

    @Override
    public Series quickUpload(MultipartFile file, SysUser sysUser) {
        //1、生成系列表内容
        Series series = ObjectUtils.newInstance(sysUser.getUserId(), Series.class);
        series.setSeriesId(StringUtils.getUuid32());
        NovelDto novelDto = new NovelDto();
        novelDto.setSeriesId(series.getSeriesId());
        novelDto.setNovelFile(file);
        //2、解析文件生成分卷信息
        Novel novel = novelService.addNovel(novelDto, sysUser);
        //3、通过拿到的分卷信息反向赋值给小说
        series.setSeriesImg(novel.getNovelImg());
        series.setSeriesAuthor(novel.getNovelAuthor());
        series.setSeriesDesc(novel.getNovelDesc());
        series.setPublicTime(novel.getPublicTime());
        series.setSeriesName(novel.getNovelName());
        //4、存入数据库
        this.save(series);
        return series;
    }

    @Override
    public void updateTotal(String novelId, SysUser sysUser) {
        Series novel = this.getById(novelId);
        List<Novel> novelList = this.novelMapper.selectList(new QueryWrapper<Novel>().in("novel_id", novelId));
        for (Novel novelVolume : novelList) {
//            novel.setTotalWord(NumberUtils.add(novel.getTotalWord(),novelVolume.getTotalWord()));
//            novel.setTotalLine(NumberUtils.add(novel.getTotalLine(),novelVolume.getTotalLine()));
        }
        novel.setUpdateTime(new Date());
        novel.setUpdateBy(sysUser.getUserId());
        this.updateById(novel);
    }

    @Override
    public void download(String novelId, SysUser sysUser, HttpServletRequest request, HttpServletResponse response) {
        //1、以自己的名字打成压缩包
        Series novel = this.getById(novelId);
        String zipName = novel.getSeriesName()+CharConstant.SEPARATOR+ StrConstant.ZIP;
        response.setContentType("application/octet-stream;charset=UTF-8");
        response.setContentType("multipart/form-data");
        response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
//        response.setHeader("Accept-Ranges", "bytes");
//        response.addHeader("Content-Length", "" + fileSize);
        try {
            request.setCharacterEncoding("utf-8");
            response.setHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode(zipName,StrConstant.DEFAULT_CHARSET));
        }catch (Exception e){
            log.error(e.getMessage(),e);
        }
        //2、获取当前小说的全部分卷
        List<Novel> volumeList = this.novelService.getNovelList(novelId, sysUser);
        try (
                ZipOutputStream zipos = new ZipOutputStream(new BufferedOutputStream(response.getOutputStream()));
                DataOutputStream os = new DataOutputStream(zipos)
        ){
            zipos.setMethod(ZipOutputStream.DEFLATED);
            for (Novel novelVolume : volumeList) {
                NovelFile file = this.novelFileService.getById(novelVolume.getFileId());
                ZipEntry zipEntry=new ZipEntry(novelVolume.getNovelName()+CharConstant.SEPARATOR+file.getFileType());
                zipos.putNextEntry(zipEntry);
                //这里才开始获取流
                InputStream inputStream=new FileInputStream(new File(file.getFilePath()));
                int b = 0;
                byte[] buffer = new byte[1024];
                while ((b = inputStream.read(buffer)) != -1) {
                    os.write(buffer, 0, b);
                }
            }
        }catch (Exception e){
            log.error(e.getMessage());
        }
    }

    /**
     * 修改封面
     * @param series
     * @param imgFile
     * @return
     */
    private String updateNovelImg(Series series, MultipartFile imgFile) {
        if(imgFile==null){
            return null;
        }
        //1、删掉原来的封面。为内存考虑
        Series oldNovel = this.getById(series.getIsDelete());
        String oldPath = rootPath + CharConstant.FILE_SEPARATOR + oldNovel.getSeriesImg();
        FileUtils.deleteFile(oldPath);
        //开始上传
        return FileUtils.uploadImg(rootPath,novelImgPath,series.getIsDelete(),imgFile);
    }
}
