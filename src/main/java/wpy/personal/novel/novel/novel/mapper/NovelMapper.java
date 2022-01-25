package wpy.personal.novel.novel.novel.mapper;

import wpy.personal.novel.pojo.bo.NovelFileBo;
import wpy.personal.novel.pojo.entity.Novel;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 小说分卷表 Mapper 接口
 * </p>
 *
 * @author wangpanyin
 * @since 2021-09-07
 */
public interface NovelMapper extends BaseMapper<Novel> {

    /**
     * 查询小说信息
     * @param seriesId
     * @return
     */
    List<NovelFileBo> getNovelFile(String seriesId);
}
