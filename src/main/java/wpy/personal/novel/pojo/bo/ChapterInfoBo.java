package wpy.personal.novel.pojo.bo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class ChapterInfoBo {

    /**
     * 主键id
     */
    private String chapterId;

    /**
     * 小说id
     */
    private String novelId;

    /**
     * 分卷id
     */
    private String seriesId;

    /**
     * 章节名
     */
    private String chapterName;

    /**
     * 章节排序
     */
    private Integer chapterOrder;

    /**
     * 开始行数
     */
    private Integer startLine;

    /**
     * 结束行数
     */
    private Integer endLine;

    /**
     * 总行数
     */
    private Integer totalLine;

    /**
     * epub路径
     */
    private String epubPath;

    /**
     * 父章节id
     */
    private String parentId;

    /**
     * 总字数
     */
    private Long totalWord;
    /**
     * 创建时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 创建人
     */
    private String createBy;

    /**
     * 更新时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    /**
     * 更新人
     */
    private String updateBy;

    /**
     * 0：否；1：是
     */
    private String isDelete;

    /**
     * 文件类型
     */
    private String fileType;

    /**
     * 文件md5，基本上就是文件名
     */
    private String fileMd5;

    /**
     * 文件路径
     */
    private String filePath;

}
