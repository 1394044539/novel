package wpy.personal.novel.novel.novel.service;

import wpy.personal.novel.pojo.entity.SeriesTypeRel;
import com.baomidou.mybatisplus.extension.service.IService;
import wpy.personal.novel.pojo.entity.SysUser;

import java.util.List;

/**
 * <p>
 * 小说类型关联表 服务类
 * </p>
 *
 * @author wangpanyin
 * @since 2021-09-07
 */
public interface SeriesTypeRelService extends IService<SeriesTypeRel> {

    /**
     * 修改小说类型
     * @param seriesId
     * @param typeCodeList
     * @param sysUser
     */
    void updateSeriesType(String seriesId, List<String> typeCodeList, SysUser sysUser);
}
