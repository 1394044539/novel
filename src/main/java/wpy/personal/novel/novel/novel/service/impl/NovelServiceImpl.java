package wpy.personal.novel.novel.novel.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;
import wpy.personal.novel.base.constant.CharConstant;
import wpy.personal.novel.base.enums.DictEnums;
import wpy.personal.novel.base.enums.SqlEnums;
import wpy.personal.novel.novel.novel.mapper.NovelFileMapper;
import wpy.personal.novel.novel.novel.mapper.NovelTypeRelMapper;
import wpy.personal.novel.novel.novel.mapper.NovelVolumeMapper;
import wpy.personal.novel.novel.novel.service.*;
import wpy.personal.novel.novel.system.service.SysUserRoleService;
import wpy.personal.novel.pojo.bo.NovelBo;
import wpy.personal.novel.pojo.dto.NovelDto;
import wpy.personal.novel.pojo.dto.VolumeDto;
import wpy.personal.novel.pojo.entity.*;
import wpy.personal.novel.novel.novel.mapper.NovelMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import wpy.personal.novel.utils.FileUtils;
import wpy.personal.novel.utils.ObjectUtils;
import wpy.personal.novel.utils.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
public class NovelServiceImpl extends ServiceImpl<NovelMapper, Novel> implements NovelService {

    @Autowired
    private SysUserRoleService sysUserRoleService;
    @Autowired
    private NovelMapper novelMapper;
    @Autowired
    private NovelTypeRelMapper novelTypeRelMapper;
    @Autowired
    private NovelVolumeService novelVolumeService;
    @Autowired
    private NovelVolumeMapper novelVolumeMapper;
    @Autowired
    private NovelTypeRelService novelTypeRelService;
    @Autowired
    private NovelChapterService novelChapterService;
    @Autowired
    private NovelFileService novelFileService;

    @Value("${novel.filePath.rootPath}")
    private String rootPath;
    @Value("${novel.filePath.novelImg}")
    private String novelImgPath;

    @Override
    public Page<Novel> getNovelList(NovelDto novelDto, SysUser sysUser) {
        QueryWrapper<Novel> qw = new QueryWrapper<>();
        qw.eq("is_delete",SqlEnums.NOT_DELETE.getCode());
        List<String> roleList = sysUserRoleService.getRoleCodeListByUserId(sysUser.getUserId());
        if(roleList.contains(DictEnums.ORDINARY_USER.getKey())){
            //非管理员只能看自己的
            qw.eq("create_by",sysUser.getUserId());
        }
        if(StringUtils.isNotEmpty(novelDto.getNovelName())){
            qw.like("novel_name",novelDto.getNovelName());
        }
        if(StringUtils.isNotEmpty(novelDto.getNovelAuthor())){
            qw.like("novel_author",novelDto.getNovelAuthor());
        }
        return this.novelMapper.selectPage(new Page<>(novelDto.getPage(), novelDto.getPageSize()), qw);
    }

    @Override
    public Novel addNovel(NovelDto novelDto, SysUser sysUser) {
        Novel novel = new Novel();
        String operationUser=sysUser.getUserId();
        Date nowDate=new Date();
        BeanUtils.copyProperties(novelDto,novel);
        novel.setNovelId(StringUtils.getUuid32());
        novel.setCreateTime(nowDate);
        novel.setCreateBy(operationUser);
        novel.setUpdateTime(nowDate);
        novel.setUpdateBy(operationUser);
        novel.setIsDelete(SqlEnums.NOT_DELETE.getCode());
        //上传了封面
        novel.setNovelImg(FileUtils.uploadImg(rootPath,novelImgPath,novel.getNovelId(),novelDto.getImgFile()));

        //添加小说类型
        if(!CollectionUtils.isEmpty(novelDto.getTypeCodeList())){
            List<NovelTypeRel> typeList=new ArrayList<>();
            for (String typeCode : novelDto.getTypeCodeList()) {
                NovelTypeRel novelTypeRel = ObjectUtils.newInstance(operationUser, NovelTypeRel.class);
                novelTypeRel.setNovelTypeRelId(StringUtils.getUuid32());
                novelTypeRel.setNovelId(novel.getNovelId());
                novelTypeRel.setTypeCode(typeCode);
                typeList.add(novelTypeRel);
            }
            novelTypeRelService.saveBatch(typeList);
        }

        this.save(novel);
        return novel;
    }

    @Override
    public NovelBo getNovelInfo(String novelId, SysUser sysUser) {
        NovelBo novelBo = new NovelBo();
        //1、取出小说信息
        Novel novel = this.getById(novelId);
        BeanUtils.copyProperties(novel,novelBo);
        //2、取出小说类型
        List<SysDictParam> typeList = novelTypeRelMapper.getNovelTypeList(novelId);
        novelBo.setTypeList(typeList);
        //3、取出小说的分卷信息
        List<NovelVolume> novelVolumeList = novelVolumeMapper.selectList(
                new QueryWrapper<NovelVolume>().eq("novel_id", novelId).orderByAsc("volume_order"));
        novelBo.setNovelVolumeList(novelVolumeList);
        return novelBo;
    }

    @Override
    public Novel updateNovel(NovelDto novelDto, SysUser sysUser) {
        Novel novel = new Novel();
        BeanUtils.copyProperties(novelDto,novel);
        novel.setUpdateBy(sysUser.getUserId());
        novel.setUpdateTime(new Date());
        //注意封面是否被修改
        novel.setNovelImg(this.updateNovelImg(novel,novelDto.getImgFile()));
        this.updateById(novel);
        return novel;
    }

    @Override
    @Transactional
    public void deleteNovel(List<String> idList, SysUser sysUser) {
        //查询每一个文件是否有其他小说在使用他
        List<String> deleteFileIdList = Lists.newArrayList();
        List<NovelVolume> novelVolumeList = this.novelVolumeMapper.selectList(new QueryWrapper<NovelVolume>().in("novel_id", idList));
        if(!CollectionUtils.isEmpty(novelVolumeList)){
            //1)查询小说的全部文件id
            List<String> fileIdList = novelVolumeList.stream().map(NovelVolume::getFileId).collect(Collectors.toList());
            //2)查询文件id是否有多个小说在使用
            List<NovelVolume> volumeFileList = this.novelVolumeMapper.selectList(new QueryWrapper<NovelVolume>().in("file_id", fileIdList));
            // 按照文件id分组
            Map<String, List<NovelVolume>> fileIdMap = volumeFileList.stream().collect(Collectors.groupingBy(NovelVolume::getFileId));
            for (String fileId : fileIdMap.keySet()) {
                List<NovelVolume> novelVolumes = fileIdMap.get(fileId);
                if(novelVolumes.size()==1){
                    // 等于1，说明这个文件只被一个分卷使用过
                    deleteFileIdList.add(fileId);
                }else {
                    // 说明被多个分卷使用过，此时需要判断这几个分卷是否在本次批量删除的小说内
                    int exitsNum = 0;
                    for (NovelVolume novelVolume : novelVolumes) {
                        // 存在则+1
                        if(idList.contains(novelVolume.getNovelId())){
                            exitsNum++;
                        }
                    }
                    // 如果相等，则说明都要删除
                    if(exitsNum==novelVolumes.size()){
                        deleteFileIdList.add(fileId);
                    }
                }
            }
        }

        //1、删除小说表
        this.removeByIds(idList);
        //2、删除分卷表
        this.novelVolumeService.remove(new QueryWrapper<NovelVolume>().in("novel_id",idList));
        //3、删除章节表
        this.novelChapterService.remove(new QueryWrapper<NovelChapter>().in("novel_id",idList));
        //4、删除文件表
        this.novelFileService.deleteFiles(deleteFileIdList);
        //5、删除评论表

        //6、删除数据表

        //7、删除类型关联表
        this.novelTypeRelService.remove(new QueryWrapper<NovelTypeRel>().eq("novel_id",idList));
        //8、删除收藏表（待定）

//        //太麻烦了，还是逻辑删除吧
//        this.update(new UpdateWrapper<Novel>().set("is_delete",SqlEnums.IS_DELETE.getCode())
//                .set("update_time",new Date()).set("update_by",sysUser.getUserId())
//                .in("novel_id",idList));
    }

    @Override
    public Novel quickUpload(MultipartFile file, SysUser sysUser) {
        //1、生成小说表内容
        Novel novel = ObjectUtils.newInstance(sysUser.getUserId(), Novel.class);
        novel.setNovelId(StringUtils.getUuid32());
        VolumeDto volumeDto = new VolumeDto();
        volumeDto.setNovelId(novel.getNovelId());
        volumeDto.setVolumeFile(file);
        //2、解析文件生成分卷信息
        NovelVolume novelVolume = novelVolumeService.addVolume(volumeDto, sysUser);
        //3、通过拿到的分卷信息反向赋值给小说
        novel.setNovelImg(novelVolume.getVolumeImg());
        novel.setNovelAuthor(novelVolume.getVolumeAuthor());
        novel.setTotalLine(novelVolume.getTotalLine());
        novel.setNovelIntroduce(novelVolume.getVolumeDesc());
        novel.setPublicTime(novelVolume.getPublicTime());
        novel.setTotalWord(novelVolume.getTotalWord());
        novel.setNovelName(novelVolume.getVolumeName());
        //4、存入数据库
        this.save(novel);
        return novel;
    }

    /**
     * 修改封面
     * @param novel
     * @param imgFile
     * @return
     */
    private String updateNovelImg(Novel novel, MultipartFile imgFile) {
        if(imgFile==null){
            return null;
        }
        //1、删掉原来的封面。为内存考虑
        Novel oldNovel = this.getById(novel.getNovelId());
        String oldPath = rootPath + CharConstant.FILE_SEPARATOR + oldNovel.getNovelImg();
        FileUtils.deleteFile(oldPath);
        //开始上传
        return FileUtils.uploadImg(rootPath,novelImgPath,novel.getNovelId(),imgFile);
    }
}
