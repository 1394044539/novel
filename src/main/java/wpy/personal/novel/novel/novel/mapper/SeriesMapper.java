package wpy.personal.novel.novel.novel.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import wpy.personal.novel.pojo.bo.SeriesCountBo;
import wpy.personal.novel.pojo.bo.SeriesListBo;
import wpy.personal.novel.pojo.entity.Series;
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
public interface SeriesMapper extends BaseMapper<Series> {

    /**
     * 查询
     * @param param
     * @param page
     * @return
     */
    List<SeriesListBo> getSeriesList(@Param("param") SeriesListVo param, Page<SeriesListBo> page);

    /**
     * 查询count
     * @param seriesId
     * @return
     */
    SeriesCountBo countSeriesInfo(String seriesId);
}
