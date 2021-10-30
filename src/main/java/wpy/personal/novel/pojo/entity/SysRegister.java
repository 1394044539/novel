package wpy.personal.novel.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author wangpanyin
 * @since 2021-09-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_register")
public class SysRegister implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId("register_id")
    private String registerId;

    /**
     * 手机号
     */
    @TableField("phone")
    private String phone;

    /**
     * 备注
     */
    @TableField("remark")
    private String remark;

    /**
     * 申请消息
     */
    @TableField("register_message")
    private String registerMessage;

    /**
     * 注册状态(0：待审批，1：已发送，2：已注册；3：已作废)
     */
    @TableField("register_status")
    private String registerStatus;

    /**
     * 注册用户id
     */
    @TableField("register_user_id")
    private String registerUserId;

    /**
     * 注册账号
     */
    @TableField(exist = false)
    private String accountName;
    /**
     * 用户姓名
     */
    @TableField(exist = false)
    private String userName;
    /**
     * 用户注册时间
     */
    @TableField(exist = false)
    private String userCreateTime;

    /**
     * 过期时间
     */
    @TableField("expire_time")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date expireTime;

    /**
     * 创建时间
     */
    @TableField("create_time")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 创建人
     */
    @TableField("create_by")
    private String createBy;

    /**
     * 修改时间
     */
    @TableField("update_time")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    /**
     * 修改人
     */
    @TableField("update_by")
    private String updateBy;

    /**
     * 是否删除
     */
    @TableField("is_delete")
    private String isDelete;


}
