package wpy.personal.novel.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 用户收藏表
 * </p>
 *
 * @author wangpanyin
 * @since 2021-09-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("user_collection")
public class UserCollection implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId("collection_id")
    private String collectionId;

    /**
     * 收藏类型(0:分卷,1:小说,2:文件夹)
     */
    @TableField("collection_type")
    private Integer collectionType;

    /**
     * 文件夹名称
     */
    @TableField("catalog_name")
    private String catalogName;

    /**
     * 文件夹id
     */
    @TableField("parent_id")
    private String parentId;

    /**
     * 分卷id
     */
    @TableField("volume_id")
    private String volumeId;

    /**
     * 小说id
     */
    @TableField("novel_id")
    private String novelId;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;

    /**
     * 创建人
     */
    @TableField("create_by")
    private String createBy;

    /**
     * 更新时间
     */
    @TableField("update_time")
    private Date updateTime;

    /**
     * 更新人
     */
    @TableField("update_by")
    private String updateBy;

    /**
     * 0：否；1：是
     */
    @TableField("is_delete")
    private Integer isDelete;


}
