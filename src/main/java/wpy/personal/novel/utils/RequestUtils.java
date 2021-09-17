package wpy.personal.novel.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import wpy.personal.novel.base.constant.StrConstant;
import wpy.personal.novel.base.enums.UtilsEnums;
import wpy.personal.novel.base.exception.UtilsException;
import wpy.personal.novel.config.jwt.JwtToken;
import wpy.personal.novel.config.jwt.JwtUtils;
import wpy.personal.novel.novel.system.mapper.SysUserMapper;
import wpy.personal.novel.pojo.entity.SysUser;

import javax.servlet.http.HttpServletRequest;

/**
 * 请求工具类
 * @author pywang
 * @date 2020/11/27
 */

@Slf4j
public class RequestUtils {

    /**
     * 获取当前登录用户信息
     * @param request
     * @return
     */
    public static SysUser getSysUser(HttpServletRequest request){
        String token = getToken(request);
        if(StringUtils.isEmpty(token)||"null".equals(token)){
            throw UtilsException.fail(UtilsEnums.USER_ERROR);
        }
        JwtToken jwtInfo = JwtUtils.getJwtInfo(token);
        SysUser sysUser = SpringUtils.getBean(SysUserMapper.class).selectById(jwtInfo.getId());
        if(sysUser == null){
            throw UtilsException.fail(UtilsEnums.USER_ERROR);
        }
        return sysUser;
    }

    /**
     * 获取当前登录用户信息
     * @param request
     * @return
     */
    public static SysUser getSysUserByNew(HttpServletRequest request){
        String token = getToken(request);
        if(StringUtils.isEmpty(token)||"null".equals(token)||"undefined".equals(token)){
            return new SysUser();
        }
        JwtToken jwtInfo = JwtUtils.getJwtInfo(token);
        SysUser sysUser = SpringUtils.getBean(SysUserMapper.class).selectById(jwtInfo.getId());
        if(sysUser == null){
            return new SysUser();
        }
        return sysUser;
    }

    /**
     * 获取token
     * @param request
     * @return
     */
    public static String getToken(HttpServletRequest request){
        String authorization=request.getHeader("authorization");
        if(authorization==null){
            authorization=request.getHeader("Authorization");
            if(authorization==null){
                authorization=request.getParameter("authorization");
                if(authorization==null){
                    authorization=request.getParameter("Authorization");
                }
            }
        }
        if(StringUtils.isEmpty(authorization)){
            throw UtilsException.fail("未含授权标识，禁止访问");
        }
        return authorization;
    }

    private static String unknowStr= "unknown";

    /**
     * 获取访问的id
     * @param request
     * @return
     */
    public static String getIp(HttpServletRequest request){
        String ip = "";
        try{
            ip = request.getHeader("x forwarded for");
            if (StringUtils.isEmpty(ip) || unknowStr. equalsIgnoreCase(ip)) {
                ip = request.getHeader("Proxy -Client IP");
            }
            if (StringUtils.isEmpty(ip) || ip. length() == 0 || unknowStr.equalsIgnoreCase(ip)){
                ip = request.getHeader("WL-Proxy-Client-IP");
            }
            if (StringUtils.isEmpty(ip) || unknowStr. equalsIgnoreCase(ip)){
                ip = request.getHeader("HTTP_ CLIENT IP");
            }
            if (StringUtils.isEmpty(ip) || unknowStr. equalsIgnoreCase(ip)){
                ip = request.getHeader("HTTP_ X FORWARDED FOR");
            }
            if (StringUtils.isEmpty(ip)|| unknowStr.equalsIgnoreCase(ip)) {
                ip = request.getRemoteAddr();
            }
        }catch (Exception e){
            log.error(" IPUtils ERROR",e) ;
            return ip ;
        }
        return ip;
    }

    /**
     * 获取request
     * @return
     */
    public static HttpServletRequest getHttpServletRequest(){
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }
}
