package wpy.personal.novel.pojo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 系列dto
 */
@Data
public class SeriesDto extends BasePageDto implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 主键id
     */
    private String seriesId;

    /**
     * 作者
     */
    private String seriesAuthor;

    /**
     * 发布日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date publicTime;

    /**
     * 系列名
     */
    private String seriesName;

    /**
     * 封面
     */
    private String seriesImg;

    /**
     * 系列描述
     */
    private String seriesDesc;

    /**
     * 系列介绍
     */
    private String seriesIntroduce;

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
     * 系列类型
     */
    private List<String> typeCodeList;

    /**
     * 系列封面
     */
    private MultipartFile imgFile;
}
