package wpy.personal.novel.pojo.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

/**
 * 小说管理列表的参数
 */
@Data
public class SeriesListVo {

    /**
     * 系列名
     */
    private String seriesName;

    /**
     * 系列作者
     */
    private String seriesAuthor;

    /**
     * 类型集合
     */
    private List<String> typeCodeList;

    /**
     * 发布开始日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date publicStartTime;

    /**
     * 发布结束日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date publicEndTime;

    /**
     * 创建开始日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date createStartTime;

    /**
     * 创建结束日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date createEndTime;

    /**
     * 创建人
     */
    private String createByName;

    /**
     * 创建人
     */
    private String createBy;
}
