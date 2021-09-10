package wpy.personal.novel.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 小说文件表
 * </p>
 *
 * @author wangpanyin
 * @since 2021-09-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("novel_file")
public class NovelFile implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 文件id
     */
    @TableId("file_id")
    private String fileId;

    /**
     * 文件名
     */
    @TableField("file_name")
    private String fileName;

    /**
     * 文件路径
     */
    @TableField("file_path")
    private String filePath;

    /**
     * 文件类型
     */
    @TableField("file_type")
    private String fileType;

    /**
     * 文件md5
     */
    @TableField("file_md5")
    private String fileMd5;

    /**
     * 文件大小
     */
    @TableField("file_size")
    private Long fileSize;

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
    private String isDelete;


}
