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
 * 小说章节表
 * </p>
 *
 * @author wangpanyin
 * @since 2021-09-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("novel_chapter")
public class NovelChapter implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId("chapter_id")
    private String chapterId;

    /**
     * 小说id
     */
    @TableField("novel_id")
    private String novelId;

    /**
     * 分卷id
     */
    @TableField("series_id")
    private String seriesId;

    /**
     * 章节名
     */
    @TableField("chapter_name")
    private String chapterName;

    /**
     * 章节排序
     */
    @TableField("chapter_order")
    private Integer chapterOrder;

    /**
     * 开始行数
     */
    @TableField("start_line")
    private Integer startLine;

    /**
     * 结束行数
     */
    @TableField("end_line")
    private Integer endLine;

    /**
     * 总行数
     */
    @TableField("total_line")
    private Integer totalLine;

    /**
     * epub路径
     */
    @TableField("epub_path")
    private String epubPath;

    /**
     * 父章节id
     */
    @TableField("parent_id")
    private String parentId;

    /**
     * 总字数
     */
    @TableField("total_word")
    private Long totalWord;
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

    /**
     * 文件类型
     */
    @TableField(exist = false)
    private String fileType;

    /**
     * 文件md5，基本上就是文件名
     */
    @TableField(exist = false)
    private String fileMd5;

    /**
     * 文件路径
     */
    @TableField(exist = false)
    private String filePath;
}
