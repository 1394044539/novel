package wpy.personal.novel.pojo.dto;

import lombok.Data;

import java.util.List;

@Data
public class NovelOrderDto {

    /**
     * 小说id
     */
    private String series;

    /**
     * 分卷id集合
     */
    private List<String> novelIdList;
}
