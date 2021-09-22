package wpy.personal.novel.novel.system.service;

import wpy.personal.novel.pojo.bo.ZhenZiResultBo;

/**
 * 发送短信服务类
 */
public interface SendMessageService {

    /**
     * 发送短信
     * @param number 手机号
     * @param verifyCode 验证码
     * @return
     */
    ZhenZiResultBo sendMessage(String number, String verifyCode);
}
