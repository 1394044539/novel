package wpy.personal.novel.pojo.bo;

import lombok.Data;

@Data
public class SeriesCountBo {

    /**
     * 主键id
     */
    private String seriesId;

    /**
     * 总字数
     */
    private Long totalWord;

    /**
     * 总章节数
     */
    private int totalChapter;
}
