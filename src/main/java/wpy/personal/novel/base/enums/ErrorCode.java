package wpy.personal.novel.base.enums;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * 错误返回码
 */
@NoArgsConstructor
@AllArgsConstructor
public enum ErrorCode {

    SUCCESS(200,"操作成功"),
    FAIL(0,"操作失败"),
    PARAM_ERROR(0,"参数错误"),

    //用户模块
    USER_NOT_LOGIN(-10000,"用户未登录"),
    USER_NOT_EXISTS(-100,"用户不存在"),
    USER_PASSWORD_ERROR(-100,"用户名或密码错误"),
    NOT_AUTHORIZATION(-200,"未含授权标识，禁止访问"),
    USER_NOT_AUTH(-100,"用户权不足"),

    ACCOUNT_NAME_NOT_EMPTY(-2,"用户名不能为空"),
    PASSWORD_NOT_EMPTY(-3,"密码不能为空"),
    PHONE_NOT_EMPTY(-4,"手机号不能为空"),
    USER_LOGIN_FAIL(-4,"用户登录失败"),
    USER_ALREADY_EXISTS(-5,"该用户已存在"),
    PHONE_ALREADY_EXISTS(-6,"该手机号已被注册"),
    PHONE_NOT_EXISTS(-7,"该手机号不存在"),

    VERIFY_CODE_NOT_EMPTY(-6,"验证码不能为空"),
    VERIFY_CODE_IS_INVALID(-7,"验证码已失效"),
    VERIFY_CODE_ERROR(-8,"验证码错误"),
    PHONE_MESSAGE_SEND_FAIL(-100,"短信发送失败"),

    FILE_INPUT_ERROR(-101,"上传的文件流异常"),

    ANALYSIS_TXT_ERROR(-102,"解析TXT文件生成章节异常")

    ;

    public Integer code;
    public String msg;

    public void ErrorCode(int code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    public void ErrorCode(String msg){
        this.msg=msg;
    }

    public String getMsg(){
        return msg;
    }

    public void ErrorCode(int code, String msg){
        this.code=code;
        this.msg=msg;
    }
}
