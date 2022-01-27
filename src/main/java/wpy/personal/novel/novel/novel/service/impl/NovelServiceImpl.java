package wpy.personal.novel.novel.novel.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;
import wpy.personal.novel.base.constant.CharConstant;
import wpy.personal.novel.base.enums.BusinessEnums;
import wpy.personal.novel.base.enums.ErrorCode;
import wpy.personal.novel.base.exception.BusinessException;
import wpy.personal.novel.novel.novel.mapper.NovelMapper;
import wpy.personal.novel.novel.novel.service.NovelChapterService;
import wpy.personal.novel.novel.novel.service.NovelFileService;
import wpy.personal.novel.novel.novel.service.SeriesService;
import wpy.personal.novel.novel.novel.service.NovelService;
import wpy.personal.novel.pojo.bo.NovelBo;
import wpy.personal.novel.pojo.bo.NovelChapterBo;
import wpy.personal.novel.pojo.dto.NovelDto;
import wpy.personal.novel.pojo.dto.NovelOrderDto;
import wpy.personal.novel.pojo.entity.NovelChapter;
import wpy.personal.novel.pojo.entity.NovelFile;
import wpy.personal.novel.pojo.entity.Novel;
import wpy.personal.novel.pojo.entity.SysUser;
import wpy.personal.novel.utils.FileUtils;
import wpy.personal.novel.utils.ObjectUtils;
import wpy.personal.novel.utils.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 小说分卷表 服务实现类
 * </p>
 *
 * @author wangpanyin
 * @since 2021-09-07
 */
@Service
@Transactional
public class NovelServiceImpl extends ServiceImpl<NovelMapper, Novel> implements NovelService {

    @Autowired
    private NovelMapper novelMapper;
    @Autowired
    private NovelFileService novelFileService;
    @Autowired
    private NovelChapterService novelChapterService;
    @Autowired
    private SeriesService seriesService;

    @Value("${novel.filePath.rootPath}")
    private String rootPath;
    @Value("${novel.filePath.novelImg}")
    private String novelImg;


    @Override
    public Novel addNovel(NovelDto novelDto, SysUser sysUser) {
        Novel novel = ObjectUtils.newInstance(sysUser.getUserId(),Novel.class);
        ObjectUtils.copyProperties(novelDto, novel);
        //设置小说排序信息
        if(novel.getNovelOrder()==null){
            Novel one = this.getOne(new QueryWrapper<Novel>()
                    .select("max(novel_order) as novelOrder").eq("series_id", novelDto.getSeriesId()));
            novel.setNovelOrder(one==null? 0:one.getNovelOrder()+1);
        }
        novel.setNovelId(StringUtils.getUuid32());
        //设置封面
        novel.setNovelImg(FileUtils.uploadImg(rootPath,novelImg, novel.getNovelId(), novelDto.getImgFile()));
        //开始解析文件了
        NovelChapterBo novelChapterBo = this.analysisFile(novel, novelDto.getNovelFile(), novelDto.getImgFile(),sysUser.getUserId());
        this.fillParam(novelChapterBo, novel);
        this.save(novel);
        return novel;
    }

    @Override
    public NovelBo getNovelInfo(String novel_id, SysUser sysUser) {
        Novel novel = this.getById(novel_id);
        if(novel ==null){
            throw BusinessException.fail("该小说不存在");
        }
        NovelBo novelBo = new NovelBo();
        BeanUtils.copyProperties(novel, novelBo);

        List<NovelChapter> chapterList = novelChapterService.getBaseMapper().selectList(
                new QueryWrapper<NovelChapter>().eq("novel_id", novel_id).orderByAsc("chapter_order"));
        novelBo.setChapterList(chapterList);
        return novelBo;
    }

    @Override
    public void batchUploadNovel(MultipartFile[] files, String seriesId, SysUser sysUser) {
        Novel one = this.getOne(new QueryWrapper<Novel>()
                .select("max(novel_order) as novelOrder").eq("series_id",seriesId));
        int count = one==null?0:one.getNovelOrder();
        for (MultipartFile file : files) {
            NovelDto novelDto = new NovelDto();
            novelDto.setNovelFile(file);
            novelDto.setSeriesId(seriesId);
            novelDto.setNovelOrder(++count);
            addNovel(novelDto, sysUser);
        }
        // 更新小说字数
//        seriesService.updateTotal(novelId,sysUser);
    }

    @Override
    public void updateOrder(NovelOrderDto novelOrderDto, SysUser sysUser) {
        List<String> novelIdList = novelOrderDto.getNovelIdList();
        List<Novel> updateList = Lists.newArrayList();
        for (int i = 0; i < novelIdList.size(); i++) {
            Novel novel = new Novel();
            novel.setNovelId(novelIdList.get(i));
            novel.setNovelOrder(i);
            updateList.add(novel);
        }
        if(!CollectionUtils.isEmpty(updateList)){
            this.updateBatchById(updateList);
        }
    }

    @Override
    public void deleteNovel(List<String> idList, SysUser sysUser) {
        //1、查看哪些文件需要被删除
        //1)查询分卷的信息
        List<Novel> novels = this.novelMapper.selectBatchIds(idList);
        //todo 删文件改成定时任务
//        List<String> deleteFileIds = this.getDeleteFileIds(novels, idList);
        // 1、删除分卷表
        this.removeByIds(idList);
        // 2、删除章节表
        this.novelChapterService.remove(new QueryWrapper<NovelChapter>().in("novel_id",idList));
        // 3、删除文件表
//        this.novelFileService.deleteFiles(deleteFileIds);
    }

    @Override
    public List<String> getDeleteFileIds(List<Novel> novelList, List<String> idList) {
        List<String> deleteFileIdList = Lists.newArrayList();
        if(!CollectionUtils.isEmpty(novelList)){
            //1)查询小说的全部文件id
            List<String> fileIdList = novelList.stream().map(Novel::getFileId).collect(Collectors.toList());
            //2)查询文件id是否有多个小说在使用
            List<Novel> volumeFileList = this.novelMapper.selectList(new QueryWrapper<Novel>().in("file_id", fileIdList));
            // 按照文件id分组
            Map<String, List<Novel>> fileIdMap = volumeFileList.stream().collect(Collectors.groupingBy(Novel::getFileId));
            for (String fileId : fileIdMap.keySet()) {
                List<Novel> novels = fileIdMap.get(fileId);
                if(novels.size()==1){
                    // 等于1，说明这个文件只被一个分卷使用过
                    deleteFileIdList.add(fileId);
                }else {
                    // 说明被多个分卷使用过，此时需要判断这几个分卷是否在本次批量删除的小说内
                    int exitsNum = 0;
                    for (Novel novel : novels) {
                        // 存在则+1
                        if(idList.contains(novel.getNovelId())){
                            exitsNum++;
                        }
                    }
                    // 如果相等，则说明都要删除
                    if(exitsNum== novels.size()){
                        deleteFileIdList.add(fileId);
                    }
                }
            }
        }
        return deleteFileIdList;
    }

    @Override
    public Novel addUploadNovel(NovelDto novelDto, SysUser sysUser) {
        Novel novel = this.addNovel(novelDto, sysUser);
        return novel;
    }

    @Override
    public List<Novel> getNovelList(String seriesId, SysUser sysUser) {
        List<Novel> novelList = novelMapper.selectList(
                new QueryWrapper<Novel>().eq("series_id", seriesId).orderByAsc("novel_order"));
        return novelList;
    }

    @Override
    public Novel updateNovel(NovelDto novelDto, SysUser sysUser) {
        Novel novel = new Novel();
        BeanUtils.copyProperties(novelDto, novel);
        novel.setUpdateBy(sysUser.getUserId());
        novel.setUpdateTime(new Date());
        //注意封面是否被修改
        novel.setNovelImg(FileUtils.uploadImg(rootPath,novelImg, novel.getNovelId(), novelDto.getImgFile()));
        this.updateById(novel);
        return novel;
    }

    @Override
    public void download(String volumeId, SysUser sysUser, HttpServletRequest request, HttpServletResponse response) {
        Novel novel = this.getById(volumeId);
        NovelFile fileInfo = novelFileService.getById(novel.getFileId());
        FileUtils.download(fileInfo.getFilePath()+ CharConstant.FILE_SEPARATOR+fileInfo.getFileMd5(),novel.getNovelName(),fileInfo.getFileType(),fileInfo.getFileSize(),request,response);
    }

    /**
     * 填充参数
     * @param novelChapterBo
     * @param novel
     */
    private void fillParam(NovelChapterBo novelChapterBo, Novel novel) {
        novel.setFileId(novelChapterBo.getFileId());
//        novel.setTotalLine(novelChapterBo.getTotalLine());
//        novel.setTotalWord(novelChapterBo.getTotalWord());
        novel.setNovelAuthor(novelChapterBo.getNovelAuthor());
        if(StringUtils.isEmpty(novel.getNovelImg())){
            novel.setNovelImg(novelChapterBo.getNovelImg());
        }
        if(StringUtils.isEmpty(novel.getNovelName())){
            novel.setNovelName(novelChapterBo.getNovelName());
        }
        if(StringUtils.isEmpty(novel.getNovelDesc())){
            novel.setNovelDesc(novelChapterBo.getNovelDesc());
        }
        if(novel.getPublicTime()==null){
            novel.setPublicTime(novelChapterBo.getPublicTime());
        }
        if(novel.getTotalWord()==null){
            novel.setTotalWord(novelChapterBo.getTotalWord());
        }
    }

    /**
     * 解析文件开始
     * @param volumeFile
     * @param novel
     * @param userId
     */
    private NovelChapterBo analysisFile(Novel novel, MultipartFile volumeFile, MultipartFile imgFile, String userId) {
        //因为此时的文件流信息需要多次使用，所以采用字节数组的内存化保存一下流信息
        byte[] bytes = this.getFileStream(volumeFile);
        //1、上传文件，并且保存到数据库中
        NovelFile novelFile = novelFileService.saveFile(volumeFile, novel.getNovelId(), userId);

        //2、开始真正解析文件，这里应该会生成很多的List<Chapter>
        NovelChapterBo novelChapterBo = this.analysisChapter(bytes, novel, volumeFile, userId);
        novelChapterBo.setFileId(novelFile.getFileId());
        return novelChapterBo;
    }

    /**
     * 解析文件生成章节信息
     * @param bytes
     * @param novel
     * @param volumeFile
     * @param userId
     * @return
     */
    private NovelChapterBo analysisChapter(byte[] bytes, Novel novel, MultipartFile volumeFile, String userId) {
        //判断类型
        String fileType = FileUtils.getFileType(volumeFile.getOriginalFilename());
        if(BusinessEnums.TXT.getCode().equalsIgnoreCase(fileType)){
            //txt
            return novelChapterService.analysisTxt(bytes, novel, volumeFile, userId);
        }else if(BusinessEnums.EPUB.getCode().equalsIgnoreCase(fileType)){
            //epub
            return novelChapterService.analysisEpub(bytes, novel,volumeFile,userId);
        }else {
            throw BusinessException.fail(ErrorCode.FILE_TYPE_ERROR);
        }
    }

    /**
     * 获取文件的byte数组结构数据
     * @param volumeFile
     * @return
     */
    private byte[] getFileStream(MultipartFile volumeFile) {
        try {
            //当文件是TXT格式的时候，需要根据编码生成不同的格式的流
            String type = FileUtils.getFileType(volumeFile.getOriginalFilename());
            byte[] bytes=null;
            //是否需要无编码格式转换
            boolean flag=true;
            if(BusinessEnums.TXT.getCode().equals(type)) {
                //获得文件的编码格式
                String code = FileUtils.getTxtFileCode(volumeFile.getInputStream());
                if(StringUtils.isNotEmpty(code)){
                    InputStreamReader inputStreamReader = new InputStreamReader(volumeFile.getInputStream(), code);
                    bytes = IOUtils.toByteArray(inputStreamReader);
                    //
                    flag=false;
                }
            }
            if(flag){
                bytes = FileUtils.inputStreamToByte(volumeFile.getInputStream());
            }
            return bytes;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw BusinessException.fail(ErrorCode.FILE_INPUT_ERROR);
        }
    }
}
