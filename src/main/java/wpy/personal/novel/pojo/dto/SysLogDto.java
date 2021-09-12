package wpy.personal.novel.pojo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@Data
public class SysLogDto extends BasePageDto implements Serializable {
    private static final long serialVersionUID = -2446862471980493524L;

    /**
     * 主键id
     */
    private String logId;

    /**
     * 操作人用户名
     */
    private String operatorUserName;

    /**
     * 操作人账户
     */
    private String operatorAccountName;

    /**
     * 操作人id
     */
    private String operatorUserId;

    /**
     * 请求方式
     */
    private String method;

    /**
     * 请求参数
     */
    private String param;

    /**
     * 请求地址
     */
    private String ip;

    /**
     * 请求路径
     */
    private String path;

    /**
     * 日志描述
     */
    private String logDesc;

    /**
     * 日志类型（通用类型0;其他为1）
     */
    private String logType;

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

}
