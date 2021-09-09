package wpy.personal.novel.utils;

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
//        String token = request.getHeader(StrConstant.AUTHORIZATION);
//        if(StringUtils.isEmpty(token)){
//            return new SysUser();
//        }
//        JwtToken jwtInfo = JwtUtils.getJwtInfo(token);
//        SysUser sysUser = SpringUtils.getBean(SysUserMapper.class).selectOne(new QueryWrapper<SysUser>()
//                .eq(SqlConstant.ACCOUNT_NAME, jwtInfo.getAccountName()));
        return new SysUser();
    }
}
