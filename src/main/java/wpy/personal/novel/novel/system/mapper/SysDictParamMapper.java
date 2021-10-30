package wpy.personal.novel.novel.system.mapper;

import wpy.personal.novel.pojo.dto.SysDictParamDto;
import wpy.personal.novel.pojo.entity.SysDictParam;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 字段子类表 Mapper 接口
 * </p>
 *
 * @author wangpanyin
 * @since 2021-09-07
 */
public interface SysDictParamMapper extends BaseMapper<SysDictParam> {

    /**
     * 查询字典参数列表
     * @param sysDictParamDto
     * @return
     */
    List<SysDictParam> getDictParamList(SysDictParamDto sysDictParamDto);
}
