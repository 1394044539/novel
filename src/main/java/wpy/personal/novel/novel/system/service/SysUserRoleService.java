package wpy.personal.novel.novel.system.service;

import wpy.personal.novel.pojo.bo.UserRoleBo;
import wpy.personal.novel.pojo.entity.SysRole;
import wpy.personal.novel.pojo.entity.SysUser;
import wpy.personal.novel.pojo.entity.SysUserRole;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 用户角色关联表 服务类
 * </p>
 *
 * @author wangpanyin
 * @since 2021-09-07
 */
public interface SysUserRoleService extends IService<SysUserRole> {

    /**
     * 通过用户id拿到用户角色信息
     * @param userId
     * @return
     */
    List<UserRoleBo> getRoleListByUserId(String userId);

    /**
     * 获取角色code列表
     * @param userId
     * @return
     */
    List<String> getRoleCodeListByUserId(String userId);

    /**
     * 根据角色查找用户
     * @param roleId
     * @return
     */
    List<SysUser> getUserListByRole(String roleId);
}
