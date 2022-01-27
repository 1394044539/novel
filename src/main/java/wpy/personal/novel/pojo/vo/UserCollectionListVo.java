package wpy.personal.novel.pojo.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class UserCollectionListVo {

    /**
     * 名称
     */
    private String catalogName;

    /**
     * 收藏类型
     */
    private String collectionType;

    /**
     * 父级名称
     */
    private String parentName;

    /**
     * 操作人
     */
    private String createByName;

    /**
     * 创建开始时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date createStartTime;

    /**
     * 创建结束时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date createEndTime;

    /**
     * 创建人
     */
    private String createBy;
}
