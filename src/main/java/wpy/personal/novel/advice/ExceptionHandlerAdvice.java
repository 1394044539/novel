package wpy.personal.novel.advice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import wpy.personal.novel.base.enums.ResponseCode;
import wpy.personal.novel.base.exception.BusinessException;
import wpy.personal.novel.base.exception.RequestException;
import wpy.personal.novel.base.exception.UtilsException;
import wpy.personal.novel.base.result.ResponseResult;

@Slf4j
@RestControllerAdvice
public class ExceptionHandlerAdvice {

    @ExceptionHandler(value = RuntimeException.class)
    public ResponseResult requestExceptionHandler(RuntimeException exception){
        if(exception!=null){
            log.error(exception.getMessage(),exception);
        }
        return ResponseResult.error();
    }

    @ExceptionHandler(value = RequestException.class)
    public ResponseResult requestExceptionHandler(RequestException exception){
        if(exception.getE()!=null){
            log.error(exception.getMsg(),exception.getE());
        }
        return ResponseResult.error(exception.getStatus(),exception.getMsg());
    }

    @ExceptionHandler(value = BusinessException.class)
    public ResponseResult businessExceptionHandler(BusinessException exception){
        if(exception.getE()!=null){
            log.error(exception.getMsg(),exception.getE());
        }
        return ResponseResult.error(exception.getStatus(),exception.getMsg());
    }

    @ExceptionHandler(value = UtilsException.class)
    public ResponseResult UtilsExceptionHandler(UtilsException exception){
        if(exception.getE()!=null){
            log.error(exception.getMessage(),exception.getE());
        }
        return ResponseResult.error(ResponseCode.FAIL.getCode(),exception.getMsg());
    }
}
