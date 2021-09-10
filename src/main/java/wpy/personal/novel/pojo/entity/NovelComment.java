package wpy.personal.novel.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 小说评论表
 * </p>
 *
 * @author wangpanyin
 * @since 2021-09-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("novel_comment")
public class NovelComment implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId("comment_id")
    private String commentId;

    /**
     * 评论类型(0:小说,1:分卷,2:章节)
     */
    @TableField("comment_type")
    private String commentType;

    /**
     * 小说id
     */
    @TableField("novel_id")
    private String novelId;

    /**
     * 分卷id
     */
    @TableField("volume_id")
    private String volumeId;

    /**
     * 章节id
     */
    @TableField("chapter_id")
    private String chapterId;

    /**
     * 评论内容
     */
    @TableField("comment_content")
    private String commentContent;

    /**
     * 评论层级(0:第一个评论,1:回复第一层评论的人,2:回复第二层人的，剩下的都算第三层)
     */
    @TableField("comment_level")
    private String commentLevel;

    /**
     * 回复的主键id
     */
    @TableField("reply_id")
    private String replyId;

    /**
     * 评论点赞数
     */
    @TableField("comment_up")
    private Long commentUp;

    /**
     * 评论数量
     */
    @TableField("comment_num")
    private Long commentNum;

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
