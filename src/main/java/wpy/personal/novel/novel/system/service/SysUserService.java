package wpy.personal.novel.novel.system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import wpy.personal.novel.pojo.bo.UserInfoBo;
import wpy.personal.novel.pojo.bo.ZhenZiResultBo;
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

    /**
     * 手机号登录
     * @param sysUserDto
     * @return
     */
    UserInfoBo loginByPhone(SysUserDto sysUserDto);

    /**
     * 获取验证码
     * @param phone
     * @return
     */
    ZhenZiResultBo getVerifyCode(String phone);

    /**
     * 校验手机号
     * @param sysUserDto
     */
    UserInfoBo checkPhone(SysUserDto sysUserDto);

    /**
     * 更新用户密码
     * @param sysUserDto
     */
    void updatePassword(SysUserDto sysUserDto);

    /**
     * 获取用户列表
     * @param sysUser
     * @param sysUserDto
     */
    Page<SysUser> getUserList(SysUser sysUser, SysUserDto sysUserDto);
}
