package wpy.personal.novel.novel.system.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import wpy.personal.novel.base.annotation.SysLogs;
import wpy.personal.novel.base.result.ResponseResult;
import wpy.personal.novel.novel.system.service.SysRoleService;
import wpy.personal.novel.pojo.bo.RoleInfoBo;
import wpy.personal.novel.pojo.dto.SysRoleDto;
import wpy.personal.novel.pojo.entity.SysRole;
import wpy.personal.novel.pojo.entity.SysUser;
import wpy.personal.novel.utils.RequestUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 角色表 前端控制器
 * </p>
 *
 * @author wangpanyin
 * @since 2021-09-07
 */
@RestController
@RequestMapping("/sysRole")
public class SysRoleController {

    @Autowired
    private SysRoleService sysRoleService;

    @PostMapping("/getRoleList")
    @SysLogs(fun = "查看角色列表")
    public ResponseResult getRoleList(HttpServletRequest request, @RequestBody SysRoleDto sysRoleDto){
        SysUser sysUser = RequestUtils.getSysUser(request);
        return ResponseResult.success(sysRoleService.getRoleList(sysRoleDto,sysUser));
    }

    @GetMapping("/geyRoleInfo")
    @SysLogs(fun = "获取角色详细信息")
    public ResponseResult getRoleInfo(HttpServletRequest request,@RequestParam("roleId")String roleId){
        SysUser sysUser = RequestUtils.getSysUser(request);
        RoleInfoBo roleInfoBo = sysRoleService.getRoleInfo(roleId,sysUser);
        return ResponseResult.success(roleInfoBo);
    }

    @PutMapping("/addRole")
    @SysLogs(fun = "添加角色")
    public ResponseResult addRole(HttpServletRequest request,@RequestBody SysRoleDto sysRoleDto){
        SysUser sysUser = RequestUtils.getSysUser(request);
        SysRole sysRole = sysRoleService.addRole(sysRoleDto,sysUser);
        return ResponseResult.success(sysRole);
    }

    @PostMapping("/updateRole")
    @SysLogs(fun = "添加角色")
    public ResponseResult updateRole(HttpServletRequest request,@RequestBody SysRoleDto sysRoleDto){
        SysUser sysUser = RequestUtils.getSysUser(request);
        sysRoleService.updateRole(sysRoleDto,sysUser);
        return ResponseResult.success();
    }

    @DeleteMapping("/deleteRole")
    @SysLogs(fun = "删除角色")
    public ResponseResult deleteRole(HttpServletRequest request, @RequestParam("roleId") String roleId){
        SysUser sysUser = RequestUtils.getSysUser(request);
        sysRoleService.deleteRole(roleId,sysUser);
        return ResponseResult.success();
    }
}
