package wpy.personal.novel.novel.system.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import wpy.personal.novel.base.annotation.SysLogs;
import wpy.personal.novel.base.result.ResponseResult;
import wpy.personal.novel.novel.system.service.SysNoticeService;
import wpy.personal.novel.pojo.dto.SysNoticeDto;
import wpy.personal.novel.pojo.entity.SysUser;
import wpy.personal.novel.utils.RequestUtils;

/**
 * <p>
 * 系统公告表 前端控制器
 * </p>
 *
 * @author wangpanyin
 * @since 2021-09-07
 */
@RestController
@RequestMapping("/sysNotice")
public class SysNoticeController {

    @Autowired
    private SysNoticeService sysNoticeService;

    @PostMapping("/list")
    @SysLogs(fun = "公告列表")
    public ResponseResult list(SysNoticeDto dto){
        SysUser sysUser = RequestUtils.getSysUser();
        return ResponseResult.success(sysNoticeService.list(dto,sysUser));
    }

    @GetMapping("/openNotice")
    @SysLogs(fun = "获取打开的公告信息")
    public ResponseResult openNotice(){
        return ResponseResult.success(sysNoticeService.openNotice());
    }
}
