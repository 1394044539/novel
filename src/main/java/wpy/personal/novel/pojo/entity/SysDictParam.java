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
 * 字段子类表
 * </p>
 *
 * @author wangpanyin
 * @since 2021-09-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_dict_param")
public class SysDictParam implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId("dict_param_id")
    private String dictParamId;

    /**
     * 父字典id
     */
    @TableField("dict_id")
    private String dictId;

    /**
     * 参数编码
     */
    @TableField("param_code")
    private String paramCode;

    /**
     * 参数名称
     */
    @TableField("param_name")
    private String paramName;

    /**
     * 字典值
     */
    @TableField("param_value")
    private String paramValue;

    /**
     * 参数描述
     */
    @TableField("param_desc")
    private String paramDesc;

    /**
     * 参数排序
     */
    @TableField("param_order")
    private Integer paramOrder;

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
