package wpy.personal.novel.pojo.bo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import wpy.personal.novel.pojo.entity.NovelChapter;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 分卷信息
 */
@Data
public class NovelBo implements Serializable {

    private static final long serialVersionUID = -4158619597150055215L;
    /**
     * 主键id
     */
    private String novelId;

    /**
     * 系列id
     */
    private String seriesId;

    /**
     * 文件id
     */
    private String fileId;

    /**
     * 分卷名
     */
    private String novelName;

    /**
     * 封面
     */
    private String novelImg;

    /**
     * 发布时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date publicTime;

    /**
     * 分卷描述
     */
    private String novelDesc;

    /**
     * 分卷排序
     */
    private Integer novelOrder;

    /**
     * 总行数
     */
    private Integer totalLine;

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
     * 章节列表
     */
    private List<NovelChapter> chapterList;
}
