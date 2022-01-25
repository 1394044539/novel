package wpy.personal.novel.pojo.bo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Data
public class SeriesListBo {

    /**
     * 系列Id
     */
    private String seriesId;

    /**
     * 系列名
     */
    private String seriesName;

    /**
     * 系列作者
     */
    private String seriesAuthor;

    /**
     * 发布时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date publicTime;

    /**
     * 封面
     */
    private String seriesImg;

    /**
     * 描述
     */
    private String seriesIntroduce;
    /**
     * 描述
     */
    private String seriesDesc;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd  HH:mm:ss")
    private String createTime;

    /**
     * 创建人
     */
    private String createByName;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd  HH:mm:ss")
    private String updateTime;

    /**
     * 类型
     */
    private String types;

    /**
     * 类型集合
     */
    private String typeCodes;

    /**
     * 类型集合
     */
    private List<String> typeCodeList;

    /**
     * 创建人
     */
    private String createBy;

    /**
     * 总字数
     */
    private Long totalWord;

    /**
     * 总章节数
     */
    private int totalChapter;
}
