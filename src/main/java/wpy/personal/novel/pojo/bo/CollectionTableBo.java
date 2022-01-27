package wpy.personal.novel.pojo.bo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class CollectionTableBo {

    /**
     * 主键id
     */
    private String collectionId;

    /**
     * 收藏类型(0:分卷,1:小说,2:文件夹)
     */
    private String collectionType;

    /**
     * 名称
     */
    private String name;

    /**
     * 父文件夹id
     */
    private String parentId;

    /**
     * 父文件夹名
     */
    private String parentName;

    /**
     * 系列id
     */
    private String seriesId;
    /**
     * 系列名
     */
    private String seriesName;

    /**
     * 小说id
     */
    private String novelId;

    /**
     * 小说名
     */
    private String novelName;


    /**
     * 创建时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 创建人
     */
    private String createBy;

    /**
     * 创建人名
     */
    private String createByName;

}
