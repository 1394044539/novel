package wpy.personal.novel.novel.novel.service;

import org.springframework.web.multipart.MultipartFile;
import wpy.personal.novel.pojo.bo.ChapterBo;
import wpy.personal.novel.pojo.bo.NovelChapterBo;
import wpy.personal.novel.pojo.entity.NovelChapter;
import com.baomidou.mybatisplus.extension.service.IService;
import wpy.personal.novel.pojo.entity.Novel;
import wpy.personal.novel.pojo.entity.SysUser;

import java.util.List;

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
     * @param novel
     * @param volumeFile
     * @param userId
     * @return
     */
    NovelChapterBo analysisTxt(byte[] bytes, Novel novel, MultipartFile volumeFile, String userId);

    /**
     * 解析epub文件
     * @param bytes
     * @param novel
     * @param volumeFile
     * @param userId
     */
    NovelChapterBo analysisEpub(byte[] bytes, Novel novel, MultipartFile volumeFile, String userId);

    /**
     * 获取章节内容
     * @param chapterId
     * @param sysUser
     * @return
     */
    ChapterBo getChapterContent(String chapterId, SysUser sysUser);

    /**
     * 获取章节列表
     * @param volumeId
     * @param sysUser
     * @return
     */
    List<NovelChapter> getChapterList(String volumeId, SysUser sysUser);
}
