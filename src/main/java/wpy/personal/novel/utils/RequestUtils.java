package wpy.personal.novel.utils;

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

public class RequestUtils {

    /**
     * 获取当前登录用户信息
     * @param request
     * @return
     */
    public static SysUser getSysUser(HttpServletRequest request){
        String token = getToken(request);
        if(StringUtils.isEmpty(token)){
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
}
