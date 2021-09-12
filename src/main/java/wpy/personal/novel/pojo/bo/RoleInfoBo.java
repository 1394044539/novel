package wpy.personal.novel.pojo.bo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import wpy.personal.novel.pojo.entity.SysUser;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 角色信息BO
 */
@Data
public class RoleInfoBo implements Serializable {

    private static final long serialVersionUID = -3568636065618715440L;
    /**
     * 角色id
     */
    private String roleId;

    /**
     * 角色编号
     */
    private String roleCode;

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 角色描述
     */
    private String roleDesc;

    /**
     * 创建时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 创建人
     */
    private String createBy;

    /**
     * 更新时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    /**
     * 更新人
     */
    private String updateBy;

    /**
     * 0：否；1：是
     */
    private String isDelete;

    /**
     * 用户列表
     */
    List<SysUser> userList;

    /**
     * 权限表
     */
    List<UserPermissionBo> permissionList;
}
