package wpy.personal.novel.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * <p>
 * 日志表
 * </p>
 *
 * @author wangpanyin
 * @since 2021-09-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_log")
public class SysLog implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId("log_id")
    private String logId;

    /**
     * 操作人用户名
     */
    @TableField("operator_user_name")
    private String operatorUserName;

    /**
     * 操作人账户
     */
    @TableField("operator_account_name")
    private String operatorAccountName;

    /**
     * 操作人id
     */
    @TableField("operator_user_id")
    private String operatorUserId;

    /**
     * 请求方式
     */
    @TableField("method")
    private String method;

    /**
     * 请求参数
     */
    @TableField("param")
    private String param;

    /**
     * 请求地址
     */
    @TableField("ip")
    private String ip;

    /**
     * 请求路径
     */
    @TableField("path")
    private String path;

    /**
     * 日志描述
     */
    @TableField("log_desc")
    private String logDesc;

    /**
     * 日志类型（通用类型0;其他为1）
     */
    @TableField("log_type")
    private String logType;

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
     * 更新时间
     */
    @TableField("update_time")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
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
