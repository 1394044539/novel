package wpy.personal.novel.novel.novel.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import wpy.personal.novel.base.enums.BusinessEnums;
import wpy.personal.novel.base.enums.ErrorCode;
import wpy.personal.novel.base.enums.SqlEnums;
import wpy.personal.novel.base.exception.BusinessException;
import wpy.personal.novel.novel.novel.mapper.NovelVolumeMapper;
import wpy.personal.novel.novel.novel.service.NovelChapterService;
import wpy.personal.novel.novel.novel.service.NovelFileService;
import wpy.personal.novel.novel.novel.service.NovelVolumeService;
import wpy.personal.novel.pojo.bo.NovelVolumeBo;
import wpy.personal.novel.pojo.bo.VolumeChapterBo;
import wpy.personal.novel.pojo.dto.VolumeDto;
import wpy.personal.novel.pojo.entity.NovelChapter;
import wpy.personal.novel.pojo.entity.NovelFile;
import wpy.personal.novel.pojo.entity.NovelVolume;
import wpy.personal.novel.pojo.entity.SysUser;
import wpy.personal.novel.utils.FileUtils;
import wpy.personal.novel.utils.StringUtils;

import java.io.InputStreamReader;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 小说分卷表 服务实现类
 * </p>
 *
 * @author wangpanyin
 * @since 2021-09-07
 */
@Service
public class NovelVolumeServiceImpl extends ServiceImpl<NovelVolumeMapper, NovelVolume> implements NovelVolumeService {

    @Autowired
    private NovelFileService novelFileService;
    @Autowired
    private NovelChapterService novelChapterService;

    @Value("${novel.filePath.rootPath}")
    private String rootPath;
    @Value("${novel.filePath.volumeImg}")
    private String volumeImgPath;


    @Override
    public NovelVolume addVolume(VolumeDto volumeDto, SysUser sysUser) {
        NovelVolume novelVolume = new NovelVolume();
        BeanUtils.copyProperties(volumeDto,novelVolume);
        novelVolume.setVolumeId(StringUtils.getUuid());
        novelVolume.setCreateBy(sysUser.getUserId());
        novelVolume.setCreateTime(new Date());
        novelVolume.setUpdateBy(sysUser.getUserId());
        novelVolume.setUpdateTime(new Date());
        novelVolume.setIsDelete(SqlEnums.NOT_DELETE.getCode());
        //设置封面
        novelVolume.setVolumeImg(FileUtils.uploadImg(rootPath,volumeImgPath,novelVolume.getVolumeId(),volumeDto.getImgFile()));

        //开始解析文件了
        VolumeChapterBo volumeChapterBo = this.analysisFile(novelVolume, volumeDto.getVolumeFile(), volumeDto.getImgFile(),sysUser.getUserId());
        this.fillParam(volumeChapterBo,novelVolume);
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

    /**
     * 填充参数
     * @param volumeChapterBo
     * @param novelVolume
     */
    private void fillParam(VolumeChapterBo volumeChapterBo, NovelVolume novelVolume) {
        novelVolume.setFileId(volumeChapterBo.getFileId());
        novelVolume.setTotalLine(volumeChapterBo.getTotalLine());
        novelVolume.setTotalWord(volumeChapterBo.getTotalWord());
        if(StringUtils.isNotEmpty(novelVolume.getVolumeImg())){
            novelVolume.setVolumeImg(volumeChapterBo.getVolumeImg());
        }
        if(StringUtils.isNotEmpty(novelVolume.getVolumeName())){
            novelVolume.setVolumeName(volumeChapterBo.getVolumeName());
        }
        if(StringUtils.isNotEmpty(novelVolume.getVolumeDesc())){
            novelVolume.setVolumeDesc(volumeChapterBo.getVolumeDesc());
        }
        if(novelVolume.getPublicTime()!=null){
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
