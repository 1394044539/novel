package wpy.personal.novel.pojo.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class HistoryListVo {

    /**
     * 书签名
     */
    private String markName;

    /**
     * 章节名称
     */
    private String chapterName;

    /**
     * 小说名
     */
    private String novelName;

    /**
     * 系列名
     */
    private String seriesName;

    /**
     * 创建人
     */
    private String createBy;

    /**
     * ip
     */
    private String ip;

    /**
     * 创建人姓名
     */
    private String createByName;

    /**
     * 记录类型（0：历史记录：1：书签记录）
     */
    private String recordType;

    /**
     * 创建开始时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date createStartTime;

    /**
     * 修改结束时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date createEndTime;

    /**
     * 修改开始时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date updateStartTime;

    /**
     * 修改结束时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date updateEndTime;
}
