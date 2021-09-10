package wpy.personal.novel.pojo.bo;

import lombok.Data;
import wpy.personal.novel.pojo.entity.SysPermission;
import wpy.personal.novel.pojo.entity.SysRole;

import java.io.Serializable;
import java.util.List;

/**
 * 用户信息
 */
@Data
public class UserInfoBo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * token
     */
    private String token;
    /**
     * 用户名
     */
    private String userName;

    /**
     * 账号
     */
    private String accountName;

    /**
     * 角色列表
     */
    List<UserRoleBo> roleList;

    /**
     * 权限列表
     */
    List<UserPermissionBo> permissionList;

}
