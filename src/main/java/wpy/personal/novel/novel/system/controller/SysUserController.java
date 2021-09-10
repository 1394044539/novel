package wpy.personal.novel.novel.system.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import wpy.personal.novel.base.annotation.SysLogs;
import wpy.personal.novel.base.enums.ResponseCode;
import wpy.personal.novel.base.result.ResponseResult;
import wpy.personal.novel.novel.system.service.SysUserService;
import wpy.personal.novel.pojo.dto.SysUserDto;
import wpy.personal.novel.pojo.entity.SysUser;

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
        SysUser sysUser = sysUserService.loginByAccount(sysUserDto);
        return ResponseResult.success(ResponseCode.LOGIN_SUCCESS.getMsg(),sysUser);
    }

}
