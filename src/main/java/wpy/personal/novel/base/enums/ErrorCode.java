package wpy.personal.novel.base.enums;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * 错误返回码
 */
@NoArgsConstructor
@AllArgsConstructor
public enum ErrorCode {

    SUCCESS("操作成功"),
    FAIL("操作失败"),
    PARAM_ERROR("参数错误"),

    /**
     *     用户模块
     */
    USER_NOT_LOGIN("用户未登录"),
    USER_NOT_EXISTS("用户不存在"),
    USER_PASSWORD_ERROR("用户名或密码错误"),
    NOT_AUTHORIZATION("未含授权标识，禁止访问"),
    USER_NOT_AUTH("用户权不足"),
    USER_INFO_REPEAT("用户重复"),

    ACCOUNT_NAME_NOT_EMPTY("用户名不能为空"),
    PASSWORD_NOT_EMPTY("密码不能为空"),
    PHONE_NOT_EMPTY("手机号不能为空"),
    USER_LOGIN_FAIL("用户登录失败"),
    USER_ALREADY_EXISTS("该用户已存在"),
    PHONE_ALREADY_EXISTS("该手机号已被注册"),
    PHONE_NOT_EXISTS("该手机号不存在"),

    VERIFY_CODE_NOT_EMPTY("验证码不能为空"),
    VERIFY_CODE_IS_INVALID("验证码已失效"),
    VERIFY_CODE_ERROR("验证码错误"),
    PHONE_MESSAGE_SEND_FAIL("短信发送失败"),

    FILE_INPUT_ERROR("上传的文件流异常"),

    ANALYSIS_TXT_ERROR("解析TXT文件生成章节异常"),

    FILE_TYPE_ERROR("文件类型错误,暂不支持该类型文件"),




    /**
     * 角色模块
     */
    ROLE_HAS_USER("该角色有用户正在使用，无法删除"),
    ROLE_CODE_REPEAT("角色编码重复")
    ;

    public String msg;

    public void ErrorCode(String msg){
        this.msg=msg;
    }

    public String getMsg(){
        return msg;
    }
}
