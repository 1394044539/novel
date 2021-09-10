package wpy.personal.novel.novel.system.service;

import wpy.personal.novel.pojo.dto.SysUserDto;
import wpy.personal.novel.pojo.entity.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author wangpanyin
 * @since 2021-09-07
 */
public interface SysUserService extends IService<SysUser> {

    /**
     * 通过账户登录系统
     * @param sysUserDto
     * @return
     */
    SysUser loginByAccount(SysUserDto sysUserDto);
}
