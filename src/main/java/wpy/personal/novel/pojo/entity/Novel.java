package wpy.personal.novel.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 小说分卷表
 * </p>
 *
 * @author wangpanyin
 * @since 2021-09-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("novel")
public class Novel implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId("novel_id")
    private String novelId;

    /**
     * 小说id
     */
    @TableField("series_id")
    private String seriesId;

    /**
     * 文件id
     */
    @TableField("file_id")
    private String fileId;

    /**
     * 小说名
     */
    @TableField("novel_name")
    private String novelName;

    /**
     * 封面
     */
    @TableField("novel_img")
    private String novelImg;

    /**
     * 发布时间
     */
    @TableField("public_time")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date publicTime;

    /**
     * 小说描述
     */
    @TableField("novel_desc")
    private String novelDesc;

    /**
     * 小说排序
     */
    @TableField("novel_order")
    private Integer novelOrder;

    /**
     * 创建时间
     */
    @TableField("create_time")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
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
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
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

    /**
     * 作者信息
     */
    @TableField(exist = false)
    private String novelAuthor;

}
