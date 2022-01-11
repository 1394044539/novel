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
 * 
 * </p>
 *
 * @author wangpanyin
 * @since 2022-01-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("feedback")
public class Feedback implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId("feedback_id")
    private String feedbackId;

    /**
     * 反馈标题
     */
    @TableField("feedback_title")
    private String feedbackTitle;

    /**
     * 反馈内容
     */
    @TableField("feedback_content")
    private String feedbackContent;

    /**
     * 反馈类型：（0:bug,1:意见）
     */
    @TableField("feedback_type")
    private String feedbackType;

    /**
     * 处理状态：（0:待处理,1:已完成）
     */
    @TableField("handle_status")
    private String handleStatus;

    /**
     * 是否删除
     */
    @TableField("is_delete")
    private String isDelete;

    /**
     * 创建人
     */
    @TableField("create_by")
    private String createBy;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;

    /**
     * 修改人
     */
    @TableField("update_by")
    private String updateBy;

    /**
     * 修改时间
     */
    @TableField("update_time")
    private Date updateTime;


}
