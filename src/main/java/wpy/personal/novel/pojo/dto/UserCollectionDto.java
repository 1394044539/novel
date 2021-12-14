package wpy.personal.novel.pojo.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class UserCollectionDto {

    /**
     * 主键id
     */
    private String collectionId;

    /**
     * 收藏类型(0:分卷,1:小说,2:文件夹)
     */
    private String collectionType;
    /**
     * 收藏类型列表
     */
    private List<String> typeList;

    /**
     * 文件夹名称
     */
    private String catalogName;

    /**
     * 文件夹id
     */
    private String parentId;

    /**
     * 分卷id
     */
    private String volumeId;

    /**
     * 小说id
     */
    private String novelId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 创建人
     */
    private String createBy;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 更新人
     */
    private String updateBy;

    /**
     * 0：否；1：是
     */
    private String isDelete;
}
