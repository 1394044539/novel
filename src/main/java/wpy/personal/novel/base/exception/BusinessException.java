package wpy.personal.novel.base.exception;

import lombok.Builder;
import lombok.Data;
import wpy.personal.novel.base.enums.ErrorCode;
import wpy.personal.novel.base.enums.ResponseCode;

import java.io.Serializable;

/**
 * 业务层异常
 */
@Data
@Builder
public class BusinessException extends RuntimeException implements Serializable {

    private Integer status;
    private String msg;
    private Exception e;

    /**
     * 自定义异常
     * @param errorCode
     * @param e
     */
    public BusinessException(ErrorCode errorCode, Exception e) {
        this.status=errorCode.code;
        this.msg=errorCode.msg;
        this.e = e;
    }

    public BusinessException(Integer code,String msg, Exception e) {
        this.status=code;
        this.msg=msg;
        this.e = e;
    }

    public BusinessException(ErrorCode errorCode) {
        this.status=errorCode.code;
        this.msg=errorCode.msg;
    }

    public static BusinessException fail(String msg){
        return BusinessException.builder()
                .status(ResponseCode.FAIL.getCode())
                .msg(msg)
                .build();
    }

    public static BusinessException fail(String msg,Exception e){
        return BusinessException.builder()
                .status(ResponseCode.FAIL.getCode())
                .msg(msg)
                .e(e)
                .build();
    }

    public static BusinessException fail(Integer code,String msg){
        return BusinessException.builder()
                .status(code)
                .msg(msg)
                .build();
    }

    public static BusinessException fail(Integer code,String msg,Exception e){
        return BusinessException.builder()
                .status(code)
                .msg(msg)
                .e(e)
                .build();
    }

    public static BusinessException fail(ErrorCode errorCode){
        return BusinessException.builder()
                .status(errorCode.getCode())
                .msg(errorCode.getMsg())
                .build();
    }

}
