package wpy.personal.novel.pojo.bo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@Data
public class NovelChapterBo implements Serializable {
    private static final long serialVersionUID = -726601285100705560L;

    /**
     * 文件id
     */
    private String fileId;

    /**
     * epub封面
     */
    private String novelImg;

    /**
     * 发布时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date publicTime;

    /**
     * 描述
     */
    private String novelDesc;

    /**
     * 名称
     */
    private String novelName;

    /**
     * 作者
     */
    private String novelAuthor;

    /**
     * 总字数
     */
    private Long totalWord;
}
