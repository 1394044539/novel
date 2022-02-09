package wpy.personal.novel.novel.novel.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import wpy.personal.novel.pojo.bo.NovelHistoryBo;
import wpy.personal.novel.pojo.dto.HistoryDto;
import wpy.personal.novel.pojo.entity.NovelHistory;
import wpy.personal.novel.pojo.entity.SysUser;
import wpy.personal.novel.pojo.vo.HistoryListVo;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author wangpanyin
 * @since 2021-09-12
 */
public interface NovelHistoryMapper extends BaseMapper<NovelHistory> {

    /**
     * 查询
     * @param param
     * @param page
     * @return
     */
    List<NovelHistoryBo> getHistoryList(@Param("param") HistoryListVo param, Page<NovelHistoryBo> page);
}
