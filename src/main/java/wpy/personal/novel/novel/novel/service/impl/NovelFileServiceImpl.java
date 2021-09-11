package wpy.personal.novel.novel.novel.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;
import wpy.personal.novel.base.constant.CharConstant;
import wpy.personal.novel.pojo.entity.NovelFile;
import wpy.personal.novel.novel.novel.mapper.NovelFileMapper;
import wpy.personal.novel.novel.novel.service.NovelFileService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import wpy.personal.novel.utils.FileUtils;
import wpy.personal.novel.utils.ObjectUtils;
import wpy.personal.novel.utils.StringUtils;

/**
 * <p>
 * 小说文件表 服务实现类
 * </p>
 *
 * @author wangpanyin
 * @since 2021-09-07
 */
@Service
public class NovelFileServiceImpl extends ServiceImpl<NovelFileMapper, NovelFile> implements NovelFileService {

    @Value("${novel.filePath.rootPath}")
    private String rootPath;
    @Value("${novel.filePath.novelFile}")
    private String novelFilePath;

    @Override
    public NovelFile saveFile(MultipartFile volumeFile, String novelId, String userId) {
        NovelFile novelFile = ObjectUtils.newInstance(userId, NovelFile.class);
        novelFile.setFileId(StringUtils.getUuid());
        novelFile.setFileSize(volumeFile.getSize());
        novelFile.setFileType(FileUtils.getFileType(volumeFile.getOriginalFilename()));
        //上传文件,用md5作为文件名，路径用配置+小说id表示
        String fileNamMd5 = FileUtils.getMd5(volumeFile);
        String uploadFilePath = rootPath + CharConstant.FILE_SEPARATOR+novelFilePath+CharConstant.FILE_SEPARATOR+novelId;
        FileUtils.upload(volumeFile,uploadFilePath,fileNamMd5);
        novelFile.setFileMd5(fileNamMd5);
        novelFile.setFilePath(novelFilePath+CharConstant.FILE_SEPARATOR+novelId);
        this.save(novelFile);
        return novelFile;
    }
}
