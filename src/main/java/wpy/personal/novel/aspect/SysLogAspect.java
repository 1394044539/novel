package wpy.personal.novel.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import wpy.personal.novel.base.annotation.SysLogs;
import wpy.personal.novel.base.enums.SqlEnums;
import wpy.personal.novel.novel.system.service.SysLogService;
import wpy.personal.novel.pojo.entity.SysLog;
import wpy.personal.novel.pojo.entity.SysUser;
import wpy.personal.novel.utils.HttpClientUtils;
import wpy.personal.novel.utils.RequestUtils;
import wpy.personal.novel.utils.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Date;

/**
 * @author 13940
 */
@Aspect
@Component
@Slf4j
public class SysLogAspect {

    @Autowired
    private SysLogService sysLogService;

    @Pointcut("@annotation(wpy.personal.novel.base.annotation.SysLogs)")
    public void logPointCut(){
        if(log.isDebugEnabled()){
            log.info("logPointCut");
        }
    }

    @Around("logPointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable{
        Object proceed = point.proceed();
        saveSysLog(point);
        return proceed;
    }

    protected void saveSysLog(ProceedingJoinPoint point) {
        MethodSignature signature=(MethodSignature) point.getSignature();
        Method method=signature.getMethod();
        SysLogs sysLogs=method.getAnnotation(SysLogs.class);
        HttpServletRequest request= HttpClientUtils.getHttpServletRequest();
        SysUser sysUser = RequestUtils.getSysUser(request);
        String ip = HttpClientUtils.getIp(request);
        //名字
        String className=point.getTarget().getClass().getName();
        String methodName=point.getSignature().getName();
        //参数
        Object[] args = point.getArgs();
        String s = Arrays.toString(args);
        String param=s.length()<500?s:"参数过大，不予记录";
        log.info("请求接口：{}",className);
        log.info("方法名：{}",methodName+"("+param+")");
        log.info("请求方式：{}",request.getMethod());
        log.info("请求ip：{}",ip);
        log.info("请求人：{}",sysUser.getUserName());
        SysLog sysLog=new SysLog();
        sysLog.setLogId(StringUtils.getUuid());
        sysLog.setLogType(SqlEnums.CURRENCY_LOG.getCode());
        sysLog.setIp(ip);
        sysLog.setMethod(request.getMethod());
        sysLog.setParam(param);
        sysLog.setPath(className+"."+methodName+"()");
        sysLog.setOperatorUserName(sysUser.getUserName());
        sysLog.setOperatorAccountName(sysUser.getAccountName());
        sysLog.setCreateBy(sysUser.getUserId());
        sysLog.setCreateTime(new Date());
        sysLog.setUpdateBy(sysUser.getUserId());
        sysLog.setOperatorUserId(sysUser.getUserId());
        sysLog.setUpdateTime(new Date());
        sysLog.setLogDesc(sysLogs.fun());
        sysLog.setIsDelete(SqlEnums.NOT_DELETE.getCode());
        sysLogService.save(sysLog);
    }

}
