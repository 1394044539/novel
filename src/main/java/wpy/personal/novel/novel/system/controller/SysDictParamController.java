package wpy.personal.novel.novel.system.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wpy.personal.novel.base.annotation.SysLogs;
import wpy.personal.novel.base.result.ResponseResult;
import wpy.personal.novel.novel.system.service.SysDictParamService;
import wpy.personal.novel.pojo.dto.SysDictParamDto;
import wpy.personal.novel.pojo.entity.SysDictParam;
import wpy.personal.novel.pojo.entity.SysUser;
import wpy.personal.novel.utils.RequestUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 * 字段子类表 前端控制器
 * </p>
 *
 * @author wangpanyin
 * @since 2021-09-07
 */
@RestController
@RequestMapping("/sysDictParam")
public class SysDictParamController {

    @Autowired
    private SysDictParamService sysDictParamService;


    @PostMapping("/getDictParamList")
    @SysLogs(fun = "获得字典参数列表")
    public ResponseResult getDictParamList( @RequestBody SysDictParamDto sysDictParamDto){
        SysUser sysUser = RequestUtils.getSysUser();
        List<SysDictParam> list = sysDictParamService.getDictParamList(sysDictParamDto,sysUser);
        return ResponseResult.success(list);
    }
}
