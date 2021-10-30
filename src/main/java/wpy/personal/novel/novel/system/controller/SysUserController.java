package wpy.personal.novel.novel.system.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import wpy.personal.novel.base.annotation.SysLogs;
import wpy.personal.novel.base.result.ResponseResult;
import wpy.personal.novel.novel.system.service.SysUserService;
import wpy.personal.novel.pojo.bo.UserInfoBo;
import wpy.personal.novel.pojo.dto.SysUserDto;
import wpy.personal.novel.pojo.entity.SysUser;
import wpy.personal.novel.utils.RequestUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author wangpanyin
 * @since 2021-09-07
 */
@RestController
@RequestMapping("/sysUser")
public class SysUserController {

    @Autowired
    private SysUserService sysUserService;

    @PostMapping("/loginByAccount")
    @SysLogs(fun = "登录")
    public ResponseResult loginByAccount(@RequestBody SysUserDto sysUserDto){
        UserInfoBo user = sysUserService.loginByAccount(sysUserDto);
        return ResponseResult.success(user);
    }

    @PostMapping("/loginByPhone")
    @SysLogs(fun = "手机号登录")
    public ResponseResult loginByPhone(@RequestBody SysUserDto sysUserDto){
        UserInfoBo user = sysUserService.loginByPhone(sysUserDto);
        return ResponseResult.success(user);
    }

    @PostMapping("/checkPhone")
    @SysLogs(fun = "校验手机号")
    public ResponseResult checkPhone(@RequestBody SysUserDto sysUserDto){
        return ResponseResult.success(sysUserService.checkPhone(sysUserDto));
    }

    @GetMapping("/getVerifyCode")
    @SysLogs(fun = "获取验证码")
    public ResponseResult getVerifyCode(@RequestParam("phone")String phone){
        return ResponseResult.success(sysUserService.getVerifyCode(phone));
    }

    @PostMapping("/addUser")
    @SysLogs(fun = "添加用户")
    public ResponseResult addUser(HttpServletRequest request, @RequestBody SysUserDto sysUserDto){
        SysUser sysUser = RequestUtils.getSysUser(request);
        sysUserService.addUser(sysUserDto,sysUser);
        return ResponseResult.success();
    }

    @PutMapping("/updatePassword")
    @SysLogs(fun = "修改密码")
    public ResponseResult updatePassword(@RequestBody SysUserDto sysUserDto){
        sysUserService.updatePassword(sysUserDto);
        return ResponseResult.success();
    }

    @PutMapping("/updateUser")
    @SysLogs(fun = "修改用户")
    public ResponseResult updateUser(HttpServletRequest request, @RequestBody SysUserDto sysUserDto){
        SysUser sysUser = RequestUtils.getSysUser(request);
        sysUserService.updateUser(sysUserDto,sysUser);
        return ResponseResult.success();
    }

    @PostMapping("/logout")
    @SysLogs(fun = "退出登录")
    public ResponseResult logon(HttpServletRequest request){
        sysUserService.logon(request);
        return ResponseResult.success();
    }

    @PostMapping("/disableUser")
    @SysLogs(fun = "禁用用户")
    public ResponseResult disableUser(HttpServletRequest request,@RequestBody List<String> ids){
        SysUser sysUser = RequestUtils.getSysUser(request);
        sysUserService.disableUser(sysUser,ids);
        return ResponseResult.success();
    }

}
