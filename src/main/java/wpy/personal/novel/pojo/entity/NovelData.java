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
 * 小说数据表
 * </p>
 *
 * @author wangpanyin
 * @since 2021-09-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("novel_data")
public class NovelData implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 小说数据主键
     */
    @TableId("novel_data_id")
    private String novelDataId;

    /**
     * 小说id
     */
    @TableField("novel_id")
    private String novelId;

    /**
     * 总热度
     */
    @TableField("total_hot")
    private Long totalHot;

    /**
     * 总点赞
     */
    @TableField("total_up")
    private Long totalUp;

    /**
     * 总点击
     */
    @TableField("total_click")
    private Long totalClick;

    /**
     * 总评论
     */
    @TableField("total_comment")
    private Long totalComment;

    /**
     * 总收藏
     */
    @TableField("total_collect")
    private Long totalCollect;

    /**
     * 总分享
     */
    @TableField("total_share")
    private Long totalShare;

    /**
     * 月热度
     */
    @TableField("month_hot")
    private Long monthHot;

    /**
     * 月点赞
     */
    @TableField("month_up")
    private Long monthUp;

    /**
     * 月点击
     */
    @TableField("month_click")
    private Long monthClick;

    /**
     * 月评论
     */
    @TableField("month_comment")
    private Long monthComment;

    /**
     * 月收藏
     */
    @TableField("month_collect")
    private Long monthCollect;

    /**
     * 月分享
     */
    @TableField("month_share")
    private Long monthShare;

    /**
     * 周热度
     */
    @TableField("week_hot")
    private Long weekHot;

    /**
     * 周点赞
     */
    @TableField("week_up")
    private Long weekUp;

    /**
     * 周点击
     */
    @TableField("week_clike")
    private Long weekClike;

    /**
     * 周评论
     */
    @TableField("week_comment")
    private Long weekComment;

    /**
     * 周收藏
     */
    @TableField("week_collect")
    private Long weekCollect;

    /**
     * 周分享
     */
    @TableField("week_share")
    private Long weekShare;

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
