package wpy.personal.novel.base.exception;

import lombok.Builder;
import lombok.Data;
import wpy.personal.novel.base.enums.ErrorCode;
import wpy.personal.novel.base.enums.ResponseCode;

import java.io.Serializable;

/**
 * @author 13940
 */
@Data
@Builder
public class RequestException extends RuntimeException implements Serializable {
    private Integer status;
    private String msg;
    private Exception e;

    /**
     * 自定义异常
     * @param
     * @param e
     */
    public RequestException(ErrorCode errorCode, Exception e) {
        this.msg=errorCode.msg;
        this.e = e;
    }

    public RequestException(Integer code,String msg, Exception e) {
        this.status=code;
        this.msg=msg;
        this.e = e;
    }

    public RequestException(ErrorCode errorCode) {
        this.msg=errorCode.msg;
    }

    public static RequestException fail(String msg){
        return RequestException.builder()
                .status(ResponseCode.FAIL.getCode())
                .msg(msg)
                .build();
    }

    public static RequestException fail(String msg,Exception e){
        return RequestException.builder()
                .status(ResponseCode.FAIL.getCode())
                .msg(msg)
                .e(e)
                .build();
    }

    public static RequestException fail(Integer code,String msg){
        return RequestException.builder()
                .status(code)
                .msg(msg)
                .build();
    }

    public static RequestException fail(Integer code,String msg,Exception e){
        return RequestException.builder()
                .status(code)
                .msg(msg)
                .e(e)
                .build();
    }

    public static BusinessException fail(ErrorCode errorCode){
        return BusinessException.builder()
                .status(ResponseCode.FAIL.getCode())
                .msg(errorCode.getMsg())
                .build();
    }

    public static BusinessException fail(ErrorCode errorCode,Exception e){
        return BusinessException.builder()
                .status(ResponseCode.FAIL.getCode())
                .msg(errorCode.getMsg())
                .e(e)
                .build();
    }

}
