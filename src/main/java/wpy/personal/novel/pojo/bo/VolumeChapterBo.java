package wpy.personal.novel.pojo.bo;

import lombok.Data;

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
    private Date publicTime;

    /**
     * 描述
     */
    private String volumeDesc;

    /**
     * 名称
     */
    private String volumeName;
}
