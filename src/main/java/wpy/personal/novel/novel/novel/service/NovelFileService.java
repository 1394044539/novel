package wpy.personal.novel.novel.novel.service;

import org.springframework.web.multipart.MultipartFile;
import wpy.personal.novel.pojo.entity.NovelFile;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 小说文件表 服务类
 * </p>
 *
 * @author wangpanyin
 * @since 2021-09-07
 */
public interface NovelFileService extends IService<NovelFile> {

    NovelFile saveFile(MultipartFile volumeFile, String novelId, String userId);

    void deleteFiles(List<String> deleteFileIdList);
}
