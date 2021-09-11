package wpy.personal.novel.pojo.dto;

import lombok.Data;

import java.io.Serializable;

/**
 *
 */
@Data
public class NovelChapterDto implements Serializable {

    private static final long serialVersionUID = 6052942867448493350L;

    /**
     * 分卷id
     */
    private String volumeId;

    /**
     * 小说id
     */
    private String novelId;

    /**
     *
     */
    private String chapterId;
}
