package wpy.personal.novel.pojo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import wpy.personal.novel.pojo.entity.SysDictParam;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class SysDictDto extends BasePageDto implements Serializable {

    private static final long serialVersionUID = 736280753028137043L;
    /**
     * 主键id
     */
    private String dictId;

    /**
     * 字典编码
     */
    private String dictCode;

    /**
     * 字典名称
     */
    private String dictName;

    /**
     * 字段描述
     */
    private String dictDesc;

    /**
     * 字典类型(0，字符串,1:开关,2:集合)
     */
    private String dictType;

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
     * 字典子项
     */
    private List<SysDictParam> paramList;
}
