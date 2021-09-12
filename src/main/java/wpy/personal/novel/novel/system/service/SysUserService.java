package wpy.personal.novel.novel.system.service;

import wpy.personal.novel.pojo.bo.UserInfoBo;
import wpy.personal.novel.pojo.dto.SysUserDto;
import wpy.personal.novel.pojo.entity.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;

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
    UserInfoBo loginByAccount(SysUserDto sysUserDto);

    /**
     * 添加用户
     * @param sysUserDto
     * @param sysUser
     */
    void addUser(SysUserDto sysUserDto, SysUser sysUser);

    /**
     * 拿到用户信息
     * @param sysUser
     * @return
     */
    UserInfoBo getUserInfo(SysUser sysUser);

    /**
     * 退出登录
     * @param request
     */
    void logon(HttpServletRequest request);

    /**
     * 修改用户
     * @param sysUserDto
     * @param sysUser
     */
    SysUser updateUser(SysUserDto sysUserDto, SysUser sysUser);
}
