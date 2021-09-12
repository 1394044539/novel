package wpy.personal.novel.novel.system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import wpy.personal.novel.pojo.bo.RoleInfoBo;
import wpy.personal.novel.pojo.dto.SysRoleDto;
import wpy.personal.novel.pojo.entity.SysRole;
import com.baomidou.mybatisplus.extension.service.IService;
import wpy.personal.novel.pojo.entity.SysUser;

/**
 * <p>
 * 角色表 服务类
 * </p>
 *
 * @author wangpanyin
 * @since 2021-09-07
 */
public interface SysRoleService extends IService<SysRole> {

    /**
     * 获取角色列表
     * @param sysRoleDto
     * @param sysUser
     * @return
     */
    Page<SysRole> getRoleList(SysRoleDto sysRoleDto, SysUser sysUser);

    /**
     * 添加角色
     * @param sysRoleDto
     * @param sysUser
     * @return
     */
    SysRole addRole(SysRoleDto sysRoleDto, SysUser sysUser);

    /**
     * 更新角色
     * @param sysRoleDto
     * @param sysUser
     * @return
     */
    void updateRole(SysRoleDto sysRoleDto, SysUser sysUser);

    /**
     * 获取角色信息
     * @param roleId
     * @param sysUser
     * @return
     */
    RoleInfoBo getRoleInfo(String roleId, SysUser sysUser);

    /**
     * 删除用户
     * @param roleId
     * @param sysUser
     */
    void deleteRole(String roleId, SysUser sysUser);

    /**
     * 给用户增加角色
     * @param userId
     * @param roleCode
     * @param operationUserId
     */
    void userAddRole(String userId, String roleCode, String operationUserId);
}
