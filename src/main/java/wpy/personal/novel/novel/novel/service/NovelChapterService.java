package wpy.personal.novel.novel.novel.service;

import org.springframework.web.multipart.MultipartFile;
import wpy.personal.novel.pojo.bo.VolumeChapterBo;
import wpy.personal.novel.pojo.entity.NovelChapter;
import com.baomidou.mybatisplus.extension.service.IService;
import wpy.personal.novel.pojo.entity.NovelVolume;

/**
 * <p>
 * 小说章节表 服务类
 * </p>
 *
 * @author wangpanyin
 * @since 2021-09-07
 */
public interface NovelChapterService extends IService<NovelChapter> {

    /**
     * 解析txt文件
     * @param bytes
     * @param novelVolume
     * @param volumeFile
     * @param userId
     * @return
     */
    VolumeChapterBo analysisTxt(byte[] bytes, NovelVolume novelVolume, MultipartFile volumeFile, String userId);

    /**
     * 解析epub文件
     * @param bytes
     * @param novelVolume
     * @param volumeFile
     * @param imgFile
     * @param userId
     */
    VolumeChapterBo analysisEpub(byte[] bytes, NovelVolume novelVolume, MultipartFile volumeFile, String userId);
}
