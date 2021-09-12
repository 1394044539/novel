package wpy.personal.novel.novel.system.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import wpy.personal.novel.base.annotation.SysLogs;
import wpy.personal.novel.base.result.ResponseResult;
import wpy.personal.novel.novel.system.service.SysLogService;
import wpy.personal.novel.pojo.dto.SysLogDto;
import wpy.personal.novel.pojo.entity.SysUser;
import wpy.personal.novel.utils.RequestUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 日志表 前端控制器
 * </p>
 *
 * @author wangpanyin
 * @since 2021-09-07
 */
@RestController
@RequestMapping("/sysLog")
public class SysLogController {

    @Autowired
    private SysLogService sysLogService;

    @PostMapping("/getLogList")
    @SysLogs(fun = "查看日志")
    public ResponseResult getLogList(HttpServletRequest request, @RequestBody SysLogDto sysLogDto){
        SysUser sysUser = RequestUtils.getSysUser(request);
        return ResponseResult.success(sysLogService.getLogList(sysLogDto,sysUser));
    }
}
