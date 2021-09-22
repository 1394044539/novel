package wpy.personal.novel.config.message;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * 榛子云短信配置
 * @author pywang
 * @date 2020/12/6
 */
@Data
@Component
@ConfigurationProperties(prefix = "zhen-zi-message")
public class ZhenZiConfig {

    private String url;

    private String appId;

    private String appSecret;

    private String templateId;

    private String expireTime;

}
