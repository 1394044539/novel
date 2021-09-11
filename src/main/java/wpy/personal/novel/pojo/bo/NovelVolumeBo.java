package wpy.personal.novel.pojo.bo;

import lombok.Data;
import wpy.personal.novel.pojo.entity.NovelChapter;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 分卷信息
 */
@Data
public class NovelVolumeBo implements Serializable {

    private static final long serialVersionUID = -4158619597150055215L;
    /**
     * 主键id
     */
    private String volumeId;

    /**
     * 小说id
     */
    private String novelId;

    /**
     * 文件id
     */
    private String fileId;

    /**
     * 分卷名
     */
    private String volumeName;

    /**
     * 封面
     */
    private String volumeImg;

    /**
     * 发布时间
     */
    private Date publicTime;

    /**
     * 分卷描述
     */
    private String volumeDesc;

    /**
     * 分卷排序
     */
    private Integer volumeOrder;

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
    private Date createTime;

    /**
     * 创建人
     */
    private String createBy;

    /**
     * 更新时间
     */
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
