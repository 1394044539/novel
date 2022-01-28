package wpy.personal.novel.pojo.bo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class UploadListBo {

    /**
     * 文件名
     */
    private String fileName;

    /**
     * 文件id
     */
    private String fileId;

    /**
     * 文件大小
     */
    private Long fileSize;

    /**
     * 小说名
     */
    private String novelName;

    /**
     * 小说id
     */
    private String novelId;

    /**
     * 系列名
     */
    private String seriesName;

    /**
     * 系列id
     */
    private String seriesId;

    /**
     * 创建时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 上传人
     */
    private String createByName;

    /**
     * 上传人id
     */
    private String createBy;
}
