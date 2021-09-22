package wpy.personal.novel.pojo.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@Data
public class SysRegisterDto implements Serializable {
    private static final long serialVersionUID = -7554506572912636850L;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 注册消息
     */
    private String registerMessage;

    /**
     * 过期时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date expireTime;

    /**
     * 是否发送消息
     */
    private String sendMessage;
}
