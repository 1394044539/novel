package wpy.personal.novel.pojo.bo;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserRoleBo implements Serializable {
    private static final long serialVersionUID = 2774319395107496824L;



    /**
     * 角色编号
     */
    private String roleCode;

    /**
     * 角色名称
     */
    private String roleName;
}
