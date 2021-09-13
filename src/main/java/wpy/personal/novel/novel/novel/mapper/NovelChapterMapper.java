package wpy.personal.novel.novel.novel.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;
import wpy.personal.novel.pojo.entity.NovelChapter;

/**
 * <p>
 * 小说章节表 Mapper 接口
 * </p>
 *
 * @author wangpanyin
 * @since 2021-09-07
 */
public interface NovelChapterMapper extends BaseMapper<NovelChapter> {

    /**
     * 查询信息
     * @param chapterId
     * @return
     */
    NovelChapter getChapterFileInfo(String chapterId);
}
