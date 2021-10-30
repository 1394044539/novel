package wpy.personal.novel.pojo.bo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class UserListBo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    private String userId;

    /**
     * 用户名称
     */
    private String userName;

    /**
     * 用户账号
     */
    private String accountName;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 密码
     */
    private String password;

    /**
     * 身份证
     */
    private String identityCard;

    /**
     * 真实姓名
     */
    private String trueName;

    /**
     * 头像
     */
    private String photo;

    /**
     * QQ号
     */
    private String qqNumber;

    /**
     * 微信号
     */
    private String wechatNumber;

    /**
     * 用户状态(0：正常；1：禁用)
     */
    private String userStatus;

    /**
     * 总空间，单位M
     */
    private BigDecimal fullMemory;

    /**
     * 可用空间，单位M
     */
    private BigDecimal useMemory;

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
     * 角色编号
     */
    private String roleCode;

    /**
     * 角色名称
     */
    private String roleName;
}
