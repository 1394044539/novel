package wpy.personal.novel.pojo.bo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class CollectionBo {

    /**
     * 主键id
     */
    private String collectionId;

    /**
     * 收藏类型(0:分卷,1:小说,2:文件夹)
     */
    private String collectionType;

    /**
     * 文件夹名称
     */
    private String catalogName;

    /**
     * 文件夹id
     */
    private String parentId;

    /**
     * 系列id
     */
    private String seriesId;

    /**
     * 小说id
     */
    private String novelId;

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
     * 更新时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    /**
     * 更新人
     */
    private String updateBy;

    /**
     * 0：否；1：是
     */
    private String isDelete;

    /**
     * 图片路径
     */
    private String imgPath;

    /**
     * 小说名
     */
    private String novelName;

    /**
     * 小说图片
     */
    private String novelImg;

    /**
     * 系列名
     */
    private String seriesName;

    /**
     * 系列封面
     */
    private String seriesImg;
}
