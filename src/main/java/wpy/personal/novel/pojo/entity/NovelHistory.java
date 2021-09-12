package wpy.personal.novel.pojo.entity;

import java.math.BigDecimal;
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
 * 
 * </p>
 *
 * @author wangpanyin
 * @since 2021-09-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("novel_history")
public class NovelHistory implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 历史记录id
     */
    @TableId("history_id")
    private String historyId;

    /**
     * ip
     */
    @TableField("ip")
    private String ip;

    /**
     * 最后访问小说id
     */
    @TableField("last_novel_id")
    private String lastNovelId;

    /**
     * 最后访问的分卷id
     */
    @TableField("last_volume_id")
    private String lastVolumeId;

    /**
     * 最后访问的章节id
     */
    @TableField("last_chapter_id")
    private String lastChapterId;

    /**
     * 书签名
     */
    @TableField("mark_name")
    private String markName;

    /**
     * 记录类型（0：历史记录：1：书签记录）
     */
    @TableField("record_type")
    private String recordType;

    /**
     * 阅读进度
     */
    @TableField("record_percentage")
    private BigDecimal recordPercentage;

    /**
     * 创建人
     */
    @TableField("create_by")
    private String createBy;

    /**
     * 创建时间
     */
    @TableField("create_time")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
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
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;


}
