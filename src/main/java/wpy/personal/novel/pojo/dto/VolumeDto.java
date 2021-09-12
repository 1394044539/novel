package wpy.personal.novel.pojo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.util.Date;

/**
 * 分卷表dto
 */
@Data
public class VolumeDto implements Serializable {

    private static final long serialVersionUID = 1L;

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
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
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
     * 分卷文件
     */
    private MultipartFile volumeFile;

    /**
     * 封面文件
     */
    private MultipartFile imgFile;

    /**
     * 文件类型
     */
    private String fileType;

}
