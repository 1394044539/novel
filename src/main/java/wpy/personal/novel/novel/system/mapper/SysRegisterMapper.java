package wpy.personal.novel.novel.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import wpy.personal.novel.pojo.dto.SysRegisterDto;
import wpy.personal.novel.pojo.entity.SysRegister;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author wangpanyin
 * @since 2021-09-22
 */
public interface SysRegisterMapper extends BaseMapper<SysRegister> {

    /**
     * 获取注册信息
     * @param registerDto
     * @param page
     * @return
     */
    List<SysRegister> getApplyRegisterList(SysRegisterDto registerDto, Page<SysRegister> page);
}
