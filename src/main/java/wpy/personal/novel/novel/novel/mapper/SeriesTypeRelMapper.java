package wpy.personal.novel.novel.novel.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import wpy.personal.novel.pojo.entity.SeriesTypeRel;
import wpy.personal.novel.pojo.entity.SysDictParam;

import java.util.List;

/**
 * <p>
 * 小说类型关联表 Mapper 接口
 * </p>
 *
 * @author wangpanyin
 * @since 2021-09-07
 */
public interface SeriesTypeRelMapper extends BaseMapper<SeriesTypeRel> {

    /**
     * 获取小说类型
     * @param novelId
     * @return
     */
    List<SysDictParam> getNovelTypeList(String novelId);
}
