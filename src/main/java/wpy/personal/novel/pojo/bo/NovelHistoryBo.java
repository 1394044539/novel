package wpy.personal.novel.pojo.bo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class NovelHistoryBo {

    /**
     * 历史记录id
     */
    private String historyId;

    /**
     * ip
     */
    private String ip;

    /**
     * 最后访问小说id
     */
    private String lastNovelId;

    /**
     * 最后访问的分卷id
     */
    private String lastVolumeId;

    /**
     * 最后访问的章节id
     */
    private String lastChapterId;

    /**
     * 书签名
     */
    private String markName;

    /**
     * 记录类型（0：历史记录：1：书签记录）
     */
    private String recordType;

    /**
     * 阅读进度
     */
    private BigDecimal recordPercentage;

    /**
     * 创建人
     */
    private String createBy;

    /**
     * 创建时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 修改人
     */
    private String updateBy;

    /**
     * 修改时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    /**
     * 小说名
     */
    private String volumeName;

    /**
     * 章节名
     */
    private String chapterName;
}
