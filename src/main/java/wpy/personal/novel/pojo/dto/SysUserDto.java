package wpy.personal.novel.pojo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@Data
public class SysUserDto implements Serializable {

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
     * 角色编码
     */
    private String roleCode;

    /**
     * 验证码
     */
    private String verifyCode;
}
