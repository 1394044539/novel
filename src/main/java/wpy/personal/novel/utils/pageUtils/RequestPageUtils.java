package wpy.personal.novel.utils.pageUtils;

import lombok.Data;

import java.util.List;

/**
 * pageUtils
 */
@Data
public class RequestPageUtils<T> {

    /**
     * 页码
     */
    private Integer page=1;

    /**
     * 页数
     */
    private Integer pageSize=10;

    /**
     * 排序
     */
    private List<OrderUtils> orderList;
    /**
     * 参数
     */
    private T param;
}
