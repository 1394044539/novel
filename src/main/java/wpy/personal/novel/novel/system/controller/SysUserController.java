package wpy.personal.novel.novel.system.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import wpy.personal.novel.base.annotation.SysLogs;
import wpy.personal.novel.base.enums.ResponseCode;
import wpy.personal.novel.base.result.ResponseResult;
import wpy.personal.novel.novel.system.service.SysUserService;
import wpy.personal.novel.pojo.bo.UserInfoBo;
import wpy.personal.novel.pojo.dto.SysUserDto;
import wpy.personal.novel.pojo.entity.SysUser;
import wpy.personal.novel.utils.RequestUtils;

import javax.servlet.http.HttpServletRequest;

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

    @PutMapping("/addUser")
    @SysLogs(fun = "添加用户")
    public ResponseResult addUser(HttpServletRequest request, @RequestBody SysUserDto sysUserDto){
        SysUser sysUser = RequestUtils.getSysUser(request);
        sysUserService.addUser(sysUserDto,sysUser);
        return ResponseResult.success();
    }

    @PostMapping("/updateUser")
    @SysLogs(fun = "修改用户")
    public ResponseResult updateUser(HttpServletRequest request, @RequestBody SysUserDto sysUserDto){
        SysUser sysUser = RequestUtils.getSysUser(request);
        sysUserService.updateUser(sysUserDto,sysUser);
        return ResponseResult.success();
    }

    @PostMapping("/logon")
    @SysLogs(fun = "退出登录")
    public ResponseResult logon(HttpServletRequest request){
        sysUserService.logon(request);
        return ResponseResult.success();
    }

}
