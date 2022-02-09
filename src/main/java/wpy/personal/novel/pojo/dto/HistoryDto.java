package wpy.personal.novel.pojo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class HistoryDto extends BasePageDto implements Serializable {
    private static final long serialVersionUID = -3175235496330394948L;

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
     * 最后访问的系列id
     */
    private String lastSeriesId;

    /**
     * 最后访问的章节id
     */
    private String lastChapterId;

    /**
     * 书签名
     */
    private String markName;

    /**
     * 章节名称
     */
    private String chapterName;

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

}
