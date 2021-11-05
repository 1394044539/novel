package wpy.personal.novel.pojo.dto;

import lombok.Data;

import java.util.List;

@Data
public class VolumeOrderDto {

    /**
     * 小说id
     */
    private String novelId;

    /**
     * 分卷id集合
     */
    private List<String> volumeIdList;
}
