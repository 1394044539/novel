package wpy.personal.novel.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * <p>
 * 系统公告表
 * </p>
 *
 * @author wangpanyin
 * @since 2021-09-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_notice")
public class SysNotice implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId("notice_id")
    private String noticeId;

    /**
     * 公告标题
     */
    @TableField("notice_title")
    private String noticeTitle;

    /**
     * 公告内容
     */
    @TableField("notice_content")
    private String noticeContent;

    /**
     * 首页展示(0:不展示,1:展示)
     */
    @TableField("main_show")
    private String mainShow;

    /**
     * 首页排序
     */
    @TableField("notice_order")
    private Integer noticeOrder;

    /**
     * 是否发布(0:否,1:是)
     */
    @TableField("is_public")
    private String isPublic;

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
