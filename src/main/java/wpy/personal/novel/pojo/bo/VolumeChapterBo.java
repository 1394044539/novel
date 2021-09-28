package wpy.personal.novel.pojo.bo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@Data
public class VolumeChapterBo implements Serializable {
    private static final long serialVersionUID = -726601285100705560L;

    /**
     * 总行数
     */
    private Integer totalLine;

    /**
     * 总字数
     */
    private Long totalWord;

    /**
     * 文件id
     */
    private String fileId;

    /**
     * epub封面
     */
    private String volumeImg;

    /**
     * 发布时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date publicTime;

    /**
     * 描述
     */
    private String volumeDesc;

    /**
     * 名称
     */
    private String volumeName;

    /**
     * 作者
     */
    private String volumeAuthor;
}
