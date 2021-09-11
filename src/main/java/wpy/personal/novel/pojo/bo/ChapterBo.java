package wpy.personal.novel.pojo.bo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 *
 */
@Data
public class ChapterBo implements Serializable {
    private static final long serialVersionUID = -1135159043030391384L;

    /**
     * 章节名称
     */
    private String chapterName;

    /**
     * 上一章id
     */
    private String lastChapterId;

    /**
     * 上一章名称
     */
    private String lastChapterName;

    /**
     * 下一章id
     */
    private String nextChapterId;

    /**
     * 下一章名称
     */
    private String nextChapterName;

    /**
     * 内容
     */
    private List<String> context;
}
