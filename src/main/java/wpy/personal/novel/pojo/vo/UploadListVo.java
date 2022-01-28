package wpy.personal.novel.pojo.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class UploadListVo {

    /**
     * 文件名
     */
    private String fileName;

    /**
     * 小说名
     */
    private String novelName;

    /**
     * 系列名
     */
    private String seriesName;

    /**
     * 上传开始时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date createStartTime;

    /**
     * 上传结束时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date createEndTime;

    /**
     * 上传人
     */
    private String createByName;

    /**
     * 创建人id
     */
    private String createBy;
}
