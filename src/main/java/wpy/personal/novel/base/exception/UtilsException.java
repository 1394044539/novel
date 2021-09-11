package wpy.personal.novel.base.exception;

import lombok.Builder;
import lombok.Data;
import wpy.personal.novel.base.enums.UtilsEnums;

import java.io.Serializable;

@Data
@Builder
public class UtilsException extends RuntimeException implements Serializable {
    private static final long serialVersionUID = 3502298916979847238L;
    private String msg;
    private Exception e;

    public static UtilsException fail(String msg){
        return UtilsException.builder()
                .msg(msg)
                .build();
    }

    public static UtilsException fail(String msg,Exception e){
        return UtilsException.builder()
                .msg(msg)
                .e(e)
                .build();
    }

    public static UtilsException fail(UtilsEnums errorCode){
        return UtilsException.builder()
                .msg(errorCode.getMsg())
                .build();
    }

    public static UtilsException fail(UtilsEnums errorCode,Exception e){
        return UtilsException.builder()
                .msg(errorCode.getMsg())
                .e(e)
                .build();
    }

}
