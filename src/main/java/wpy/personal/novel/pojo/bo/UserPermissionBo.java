package wpy.personal.novel.pojo.bo;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserPermissionBo implements Serializable {

    private static final long serialVersionUID = 2227224851005562825L;

    /**
     * 权限编码
     */
    private String permissionCode;

    /**
     * 权限名称
     */
    private String permissionName;
}
