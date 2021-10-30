package wpy.personal.novel.novel.system.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wpy.personal.novel.base.annotation.SysLogs;
import wpy.personal.novel.base.result.ResponseResult;
import wpy.personal.novel.novel.system.service.SysRegisterService;
import wpy.personal.novel.pojo.dto.SysRegisterDto;
import wpy.personal.novel.pojo.entity.SysUser;
import wpy.personal.novel.utils.RequestUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author wangpanyin
 * @since 2021-09-22
 */
@RestController
@RequestMapping("/sysRegister")
public class SysRegisterController {

    @Autowired
    private SysRegisterService sysRegisterService;

    @PostMapping("/applyRegister")
    @SysLogs(fun = "申请注册")
    public ResponseResult applyRegister(@RequestBody SysRegisterDto registerDto){
        sysRegisterService.applyRegister(registerDto);
        return ResponseResult.success();
    }

    @PostMapping("/getApplyRegisterList")
    @SysLogs(fun = "获取申请列表")
    public ResponseResult getApplyRegisterList(HttpServletRequest request, @RequestBody SysRegisterDto registerDto){
        SysUser sysUser = RequestUtils.getSysUser(request);
        return ResponseResult.success(sysRegisterService.getApplyRegisterList(sysUser,registerDto));
    }

    @PostMapping("/createRegInfo")
    @SysLogs(fun = "生成注册信息")
    public ResponseResult createRegInfo(HttpServletRequest request, @RequestBody SysRegisterDto registerDto){
        SysUser sysUser = RequestUtils.getSysUser(request);
        return ResponseResult.success(sysRegisterService.createRegInfo(sysUser,registerDto));
    }


}
