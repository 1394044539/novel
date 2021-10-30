package wpy.personal.novel.pojo.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class SysDictParamDto extends BasePageDto implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 字典id
     */
    private String dictId;

    /**
     * 字典编码
     */
    private String dictCode;

    /**
     * 参数编码
     */
    private String paramCode;

    /**
     * 参数名称
     */
    private String paramName;
}
