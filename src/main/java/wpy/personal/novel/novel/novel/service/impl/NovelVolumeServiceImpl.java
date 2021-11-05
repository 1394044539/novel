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
import wpy.personal.novel.base.enums.BusinessEnums;
import wpy.personal.novel.base.enums.ErrorCode;
import wpy.personal.novel.base.enums.SqlEnums;
import wpy.personal.novel.base.exception.BusinessException;
import wpy.personal.novel.novel.novel.mapper.NovelVolumeMapper;
import wpy.personal.novel.novel.novel.service.NovelChapterService;
import wpy.personal.novel.novel.novel.service.NovelFileService;
import wpy.personal.novel.novel.novel.service.NovelService;
import wpy.personal.novel.novel.novel.service.NovelVolumeService;
import wpy.personal.novel.pojo.bo.NovelVolumeBo;
import wpy.personal.novel.pojo.bo.VolumeChapterBo;
import wpy.personal.novel.pojo.dto.VolumeDto;
import wpy.personal.novel.pojo.dto.VolumeOrderDto;
import wpy.personal.novel.pojo.entity.NovelChapter;
import wpy.personal.novel.pojo.entity.NovelFile;
import wpy.personal.novel.pojo.entity.NovelVolume;
import wpy.personal.novel.pojo.entity.SysUser;
import wpy.personal.novel.utils.FileUtils;
import wpy.personal.novel.utils.StringUtils;

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
public class NovelVolumeServiceImpl extends ServiceImpl<NovelVolumeMapper, NovelVolume> implements NovelVolumeService {

    @Autowired
    private NovelVolumeMapper novelVolumeMapper;
    @Autowired
    private NovelFileService novelFileService;
    @Autowired
    private NovelChapterService novelChapterService;
    @Autowired
    private NovelService novelService;

    @Value("${novel.filePath.rootPath}")
    private String rootPath;
    @Value("${novel.filePath.volumeImg}")
    private String volumeImgPath;


    @Override
    public NovelVolume addVolume(VolumeDto volumeDto, SysUser sysUser) {
        NovelVolume novelVolume = new NovelVolume();
        BeanUtils.copyProperties(volumeDto,novelVolume);
        if(novelVolume.getVolumeOrder()==null){
            NovelVolume one = this.getOne(new QueryWrapper<NovelVolume>()
                    .select("max(volume_order) as volumeOrder").eq("novel_id",volumeDto.getNovelId()));
            novelVolume.setVolumeOrder(one==null? 0:one.getVolumeOrder());
        }
        novelVolume.setVolumeId(StringUtils.getUuid32());
        novelVolume.setCreateBy(sysUser.getUserId());
        novelVolume.setCreateTime(new Date());
        novelVolume.setUpdateBy(sysUser.getUserId());
        novelVolume.setUpdateTime(new Date());
        novelVolume.setIsDelete(SqlEnums.NOT_DELETE.getCode());
        //设置封面
        novelVolume.setVolumeImg(FileUtils.uploadImg(rootPath,volumeImgPath,novelVolume.getVolumeId(),volumeDto.getImgFile()));

        //开始解析文件了
        VolumeChapterBo volumeChapterBo = this.analysisFile(novelVolume, volumeDto.getVolumeFile(), volumeDto.getImgFile(),sysUser.getUserId());
        this.fillParam(volumeChapterBo, novelVolume);
        this.save(novelVolume);
        return novelVolume;
    }

    @Override
    public NovelVolumeBo getVolumeInfo(String volumeId, SysUser sysUser) {
        NovelVolume novelVolume = this.getById(volumeId);
        NovelVolumeBo novelVolumeBo = new NovelVolumeBo();
        BeanUtils.copyProperties(novelVolume,novelVolumeBo);

        List<NovelChapter> chapterList = novelChapterService.getBaseMapper().selectList(
                new QueryWrapper<NovelChapter>().eq("volume_id", volumeId).orderByAsc("chapter_order"));
        novelVolumeBo.setChapterList(chapterList);
        return novelVolumeBo;
    }

    @Override
    public void batchUploadVolume(MultipartFile[] files, String novelId, SysUser sysUser) {
        for (MultipartFile file : files) {
            VolumeDto volumeDto = new VolumeDto();
            volumeDto.setVolumeFile(file);
            volumeDto.setNovelId(novelId);
            addVolume(volumeDto, sysUser);
        }
        // 更新小说字数
        novelService.updateTotal(novelId,sysUser);
    }

    @Override
    public void updateOrder(VolumeOrderDto volumeOrderDto, SysUser sysUser) {
        List<String> volumeIdList = volumeOrderDto.getVolumeIdList();
        List<NovelVolume> updateList = Lists.newArrayList();
        for (int i = 0; i < volumeIdList.size(); i++) {
            NovelVolume novelVolume = new NovelVolume();
            novelVolume.setVolumeId(volumeIdList.get(i));
            novelVolume.setVolumeOrder(i);
            updateList.add(novelVolume);
        }
        if(!CollectionUtils.isEmpty(updateList)){
            this.updateBatchById(updateList);
        }
    }

    @Override
    public void deleteVolume(List<String> idList, SysUser sysUser) {
        //1、查看哪些文件需要被删除
        //1)查询分卷的信息
        List<NovelVolume> novelVolumes = this.novelVolumeMapper.selectBatchIds(idList);
        List<String> deleteFileIds = this.getDeleteFileIds(novelVolumes, idList);
        // 1、删除分卷表
        this.removeByIds(idList);
        // 2、删除章节表
        this.novelChapterService.remove(new QueryWrapper<NovelChapter>().in("volume_id",idList));
        // 3、删除文件表
        this.novelFileService.deleteFiles(deleteFileIds);
    }

    @Override
    public List<String> getDeleteFileIds(List<NovelVolume> novelVolumeList, List<String> idList) {
        List<String> deleteFileIdList = Lists.newArrayList();
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
        return deleteFileIdList;
    }

    @Override
    public NovelVolume addVolumeUpdateNovel(VolumeDto volumeDto, SysUser sysUser) {
        NovelVolume novelVolume = this.addVolume(volumeDto, sysUser);
        // 更新小说字数
        novelService.updateTotal(novelVolume.getNovelId(),sysUser);
        return novelVolume;
    }

    @Override
    public List<NovelVolume> getVolumeList(String novelId, SysUser sysUser) {
        List<NovelVolume> novelVolumeList = novelVolumeMapper.selectList(
                new QueryWrapper<NovelVolume>().eq("novel_id", novelId).orderByAsc("volume_order"));
        return novelVolumeList;
    }

    /**
     * 填充参数
     * @param volumeChapterBo
     * @param novelVolume
     */
    private void fillParam(VolumeChapterBo volumeChapterBo, NovelVolume novelVolume) {
        novelVolume.setFileId(volumeChapterBo.getFileId());
        novelVolume.setTotalLine(volumeChapterBo.getTotalLine());
        novelVolume.setTotalWord(volumeChapterBo.getTotalWord());
        novelVolume.setVolumeAuthor(volumeChapterBo.getVolumeAuthor());
        if(StringUtils.isEmpty(novelVolume.getVolumeImg())){
            novelVolume.setVolumeImg(volumeChapterBo.getVolumeImg());
        }
        if(StringUtils.isEmpty(novelVolume.getVolumeName())){
            novelVolume.setVolumeName(volumeChapterBo.getVolumeName());
        }
        if(StringUtils.isEmpty(novelVolume.getVolumeDesc())){
            novelVolume.setVolumeDesc(volumeChapterBo.getVolumeDesc());
        }
        if(novelVolume.getPublicTime()==null){
            novelVolume.setPublicTime(volumeChapterBo.getPublicTime());
        }
    }

    /**
     * 解析文件开始
     * @param volumeFile
     * @param novelVolume
     * @param userId
     */
    private VolumeChapterBo analysisFile(NovelVolume novelVolume, MultipartFile volumeFile,MultipartFile imgFile, String userId) {
        //因为此时的文件流信息需要多次使用，所以采用字节数组的内存化保存一下流信息
        byte[] bytes = this.getFileStream(volumeFile);
        //1、上传文件，并且保存到数据库中
        NovelFile novelFile = novelFileService.saveFile(volumeFile, novelVolume.getNovelId(), userId);

        //2、开始真正解析文件，这里应该会生成很多的List<Chapter>
        VolumeChapterBo volumeChapterBo = this.analysisChapter(bytes, novelVolume, volumeFile, userId);
        volumeChapterBo.setFileId(novelFile.getFileId());
        return volumeChapterBo;
    }

    /**
     * 解析文件生成章节信息
     * @param bytes
     * @param novelVolume
     * @param volumeFile
     * @param userId
     * @return
     */
    private VolumeChapterBo analysisChapter(byte[] bytes, NovelVolume novelVolume, MultipartFile volumeFile, String userId) {
        //判断类型
        String fileType = FileUtils.getFileType(volumeFile.getOriginalFilename());
        if(BusinessEnums.TXT.getCode().equalsIgnoreCase(fileType)){
            //txt
            return novelChapterService.analysisTxt(bytes, novelVolume, volumeFile, userId);
        }else if(BusinessEnums.EPUB.getCode().equalsIgnoreCase(fileType)){
            //epub
            return novelChapterService.analysisEpub(bytes,novelVolume,volumeFile,userId);
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
