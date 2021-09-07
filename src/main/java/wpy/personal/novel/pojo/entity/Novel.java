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
 * 小说表
 * </p>
 *
 * @author wangpanyin
 * @since 2021-09-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("novel")
public class Novel implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId("novel_id")
    private String novelId;

    /**
     * 作者
     */
    @TableField("novel_author")
    private String novelAuthor;

    /**
     * 发布日期
     */
    @TableField("public_time")
    private Date publicTime;

    /**
     * 小说名
     */
    @TableField("novel_name")
    private String novelName;

    /**
     * 总行数
     */
    @TableField("total_line")
    private Integer totalLine;

    /**
     * 总字数
     */
    @TableField("total_word")
    private Long totalWord;

    /**
     * 封面
     */
    @TableField("novel_img")
    private String novelImg;

    /**
     * 小说描述
     */
    @TableField("novel_desc")
    private String novelDesc;

    /**
     * 小说介绍
     */
    @TableField("novel_introduce")
    private String novelIntroduce;

    /**
     * 小说热度
     */
    @TableField("novel_hot")
    private Long novelHot;

    /**
     * 小说点赞次数
     */
    @TableField("novel_up")
    private Long novelUp;

    /**
     * 小说点击次数
     */
    @TableField("novel_click")
    private Long novelClick;

    /**
     * 小说评论次数
     */
    @TableField("novel_comment")
    private Long novelComment;

    /**
     * 小说分享次数
     */
    @TableField("novel_share")
    private Long novelShare;

    /**
     * 小说收藏次数
     */
    @TableField("novel_collect")
    private Long novelCollect;

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
    private Integer isDelete;


}
