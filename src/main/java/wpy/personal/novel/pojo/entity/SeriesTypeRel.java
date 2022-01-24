package wpy.personal.novel.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 小说类型关联表
 * </p>
 *
 * @author wangpanyin
 * @since 2021-09-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("series_type_rel")
public class SeriesTypeRel implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId("series_type_rel_id")
    private String seriesTypeRelId;

    /**
     * 小说id
     */
    @TableField("series_id")
    private String seriesId;

    /**
     * 类型编码
     */
    @TableField("type_code")
    private String typeCode;

}
