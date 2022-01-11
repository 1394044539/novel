package wpy.personal.novel.pojo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class FeedbackDto extends BasePageDto{

    /**
     * 主键id
     */
    private String id;

    /**
     * 反馈标题
     */
    private String feedbackTitle;

    /**
     * 反馈内容
     */
    private String feedbackContent;

    /**
     * 反馈类型：（0:bug,1:意见）
     */
    private String feedbackType;

    /**
     * 处理状态：（0:待处理,1:已完成）
     */
    private String handleStatus;

    /**
     * 是否删除
     */
    private String isDelete;

    /**
     * 创建人
     */
    private String createBy;

    /**
     * 创建时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 修改人
     */
    private String updateBy;

    /**
     * 修改时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
}
