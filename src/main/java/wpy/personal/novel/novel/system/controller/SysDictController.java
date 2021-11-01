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
import java.util.List;

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

    @PostMapping("/addDict")
    @SysLogs(fun = "添加字典")
    public ResponseResult addDict( @RequestBody SysDictDto sysDictDto){
        SysUser sysUser = RequestUtils.getSysUser();
        SysDict sysDict = sysDictService.addDict(sysDictDto,sysUser);
        return ResponseResult.success(sysDict);
    }

    @PostMapping("/getDictList")
    @SysLogs(fun = "获取字典信息")
    public ResponseResult getDictList(@RequestBody SysDictDto sysDictDto){
        SysUser sysUser = RequestUtils.getSysUser();
        return ResponseResult.success(sysDictService.getDictList(sysDictDto,sysUser));
    }

    @GetMapping("/getDictInfo")
    @SysLogs(fun = "获取字典详情")
    public ResponseResult getDictInfo(@RequestParam("dictId")String dictId){
        SysUser sysUser = RequestUtils.getSysUser();
        return ResponseResult.success(sysDictService.getDictInfo(dictId,sysUser));
    }

    @PutMapping("/updateDict")
    @SysLogs(fun = "修改字典")
    public ResponseResult updateDict( @RequestBody SysDictDto sysDictDto){
        SysUser sysUser = RequestUtils.getSysUser();
        SysDict sysDict = sysDictService.updateDict(sysDictDto,sysUser);
        return ResponseResult.success(sysDict);
    }

    @DeleteMapping("/deleteDict")
    @SysLogs(fun = "删除字典")
    public ResponseResult deleteDict( @RequestBody List<String> ids) {
        SysUser sysUser = RequestUtils.getSysUser();
        sysDictService.deleteDict(ids, sysUser);
        return ResponseResult.success();
    }

}
