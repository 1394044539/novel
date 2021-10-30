package wpy.personal.novel.novel.system.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import wpy.personal.novel.pojo.dto.SysUserDto;
import wpy.personal.novel.pojo.entity.SysUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author wangpanyin
 * @since 2021-09-07
 */
public interface SysUserMapper extends BaseMapper<SysUser> {

    /**
     * 查询用户列表
     * @param sysUserDto
     * @param page
     * @return
     */
    List<SysUser> selectUserInfo(SysUserDto sysUserDto, Page<SysUser> page);
}
