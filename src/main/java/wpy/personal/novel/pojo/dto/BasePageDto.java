package wpy.personal.novel.pojo.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class BasePageDto implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 页码
     */
    private int page=1;

    /**
     * 页面大小
     */
    private int pageSize=10;

    /**
     * 是否倒叙
     */
    private boolean desc = true;
}
