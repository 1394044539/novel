package wpy.personal.novel.novel.system.service.impl;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.zhenzi.sms.ZhenziSmsClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wpy.personal.novel.base.exception.BusinessException;
import wpy.personal.novel.config.message.ZhenZiConfig;
import wpy.personal.novel.novel.system.service.SendMessageService;
import wpy.personal.novel.pojo.bo.ZhenZiResultBo;

import java.util.Map;

@Service
@Slf4j
public class SendMessageServiceImpl implements SendMessageService {

    @Autowired
    private ZhenZiConfig zhenZiConfig;

    @Override
    public ZhenZiResultBo sendMessage(String number, String verifyCode) {
        ZhenziSmsClient client = new ZhenziSmsClient(zhenZiConfig.getUrl(), zhenZiConfig.getAppId(), zhenZiConfig.getAppSecret());
        Map<String,Object> params= Maps.newHashMap();
        //模板id，在平台上面配置的
        params.put("templateId",zhenZiConfig.getTemplateId());
        String[] templateParams = new String[2];
        templateParams[0]=verifyCode;
        //失效时间（分钟）
        templateParams[1]=zhenZiConfig.getExpireTime();
        params.put("templateParams",templateParams);
        params.put("number",number);
        String resultStr="";
        try {
            log.info("发送短信，号码:{},验证码:{}",number,verifyCode);
            resultStr = client.send(params);
            log.info("发送短信验证码调用成功：{}",resultStr);
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            throw BusinessException.fail("验证码发送失败");
        }
        return JSON.parseObject(resultStr,ZhenZiResultBo.class);
    }
}
