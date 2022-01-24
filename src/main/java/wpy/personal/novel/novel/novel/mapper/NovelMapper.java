package wpy.personal.novel.novel.novel.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import wpy.personal.novel.pojo.entity.Novel;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import wpy.personal.novel.pojo.vo.SeriesListVo;

import java.util.List;

/**
 * <p>
 * 小说表 Mapper 接口
 * </p>
 *
 * @author wangpanyin
 * @since 2021-09-07
 */
public interface NovelMapper extends BaseMapper<Novel> {

    /**
     * 查询
     * @param param
     * @param page
     * @return
     */
    List<Novel> getSeriesList(SeriesListVo param, Page<Novel> page);
}
