package wpy.personal.novel.novel.system.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import wpy.personal.novel.base.annotation.SysLogs;
import wpy.personal.novel.base.result.ResponseResult;
import wpy.personal.novel.novel.system.service.SysDictService;
import wpy.personal.novel.pojo.dto.SysDictDto;
import wpy.personal.novel.pojo.entity.SysDict;
import wpy.personal.novel.pojo.entity.SysUser;
import wpy.personal.novel.utils.RequestUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 字典表 前端控制器
 * </p>
 *
 * @author wangpanyin
 * @since 2021-09-07
 */
@RestController
@RequestMapping("/sysDict")
public class SysDictController {

    @Autowired
    private SysDictService sysDictService;

    @PutMapping("/addDict")
    @SysLogs(fun = "添加字典")
    public ResponseResult addDict(HttpServletRequest request, @RequestBody SysDictDto sysDictDto){
        SysUser sysUser = RequestUtils.getSysUser(request);
        SysDict sysDict = sysDictService.addDict(sysDictDto,sysUser);
        return ResponseResult.success(sysDict);
    }

    @PostMapping("/getDictList")
    @SysLogs(fun = "获取字典信息")
    public ResponseResult getDictList(HttpServletRequest request,@RequestBody SysDictDto sysDictDto){
        SysUser sysUser = RequestUtils.getSysUser(request);
        return ResponseResult.success(sysDictService.getDictList(sysDictDto,sysUser));
    }

    @GetMapping("/getDictInfo")
    @SysLogs(fun = "获取字典详情")
    public ResponseResult getDictInfo(HttpServletRequest request,@RequestParam("dictId")String dictId){
        SysUser sysUser = RequestUtils.getSysUser(request);
        return ResponseResult.success(sysDictService.getDictInfo(dictId,sysUser));
    }

    @PostMapping("/updateDict")
    @SysLogs(fun = "修改字典")
    public ResponseResult updateDict(HttpServletRequest request, @RequestBody SysDictDto sysDictDto){
        SysUser sysUser = RequestUtils.getSysUser(request);
        SysDict sysDict = sysDictService.updateDict(sysDictDto,sysUser);
        return ResponseResult.success(sysDict);
    }

}
