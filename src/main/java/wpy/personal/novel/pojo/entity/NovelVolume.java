package wpy.personal.novel.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

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
@TableName("novel_volume")
public class NovelVolume implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId("volume_id")
    private String volumeId;

    /**
     * 小说id
     */
    @TableField("novel_id")
    private String novelId;

    /**
     * 文件id
     */
    @TableField("file_id")
    private String fileId;

    /**
     * 分卷名
     */
    @TableField("volume_name")
    private String volumeName;

    /**
     * 封面
     */
    @TableField("volume_img")
    private String volumeImg;

    /**
     * 发布时间
     */
    @TableField("public_time")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date publicTime;

    /**
     * 分卷描述
     */
    @TableField("volume_desc")
    private String volumeDesc;

    /**
     * 分卷排序
     */
    @TableField("volume_order")
    private Integer volumeOrder;

    /**
     * 总行数
     */
    @TableField("total_line")
    private Integer totalLine;

    /**
     * 总字数
     */
    @TableField("total_word")
    private Long totalWord;

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


}
