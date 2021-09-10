package wpy.personal.novel.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author wangpanyin
 * @since 2021-09-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_user")
public class SysUser implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId("user_id")
    private String userId;

    /**
     * 用户名称
     */
    @TableField("user_name")
    private String userName;

    /**
     * 用户账号
     */
    @TableField("account_name")
    private String accountName;

    /**
     * 手机号
     */
    @TableField("phone")
    private String phone;

    /**
     * 邮箱
     */
    @TableField("email")
    private String email;

    /**
     * 密码
     */
    @TableField("password")
    private String password;

    /**
     * 身份证
     */
    @TableField("identity_card")
    private String identityCard;

    /**
     * 真实姓名
     */
    @TableField("true_name")
    private String trueName;

    /**
     * 头像
     */
    @TableField("photo")
    private String photo;

    /**
     * QQ号
     */
    @TableField("qq_number")
    private String qqNumber;

    /**
     * 微信号
     */
    @TableField("wechat_number")
    private String wechatNumber;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;

    /**
     * 创建人
     */
    @TableField("create_by")
    private String createBy;

    /**
     * 更新时间
     */
    @TableField("update_time")
    private Date updateTime;

    /**
     * 更新人
     */
    @TableField("update_by")
    private String updateBy;

    /**
     * 0：否；1：是
     */
    @TableField("is_delete")
    private String isDelete;


}
