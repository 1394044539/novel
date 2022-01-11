package wpy.personal.novel.novel.system.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import wpy.personal.novel.base.annotation.SysLogs;
import wpy.personal.novel.base.result.ResponseResult;
import wpy.personal.novel.novel.system.service.FeedbackService;
import wpy.personal.novel.pojo.dto.FeedbackDto;
import wpy.personal.novel.pojo.entity.SysUser;
import wpy.personal.novel.utils.RequestUtils;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author wangpanyin
 * @since 2022-01-07
 */
@RestController
@RequestMapping("/feedback")
public class FeedbackController {

    @Autowired
    private FeedbackService feedbackService;

    @PostMapping("/list")
    @SysLogs(fun = "反馈列表")
    public ResponseResult list(@RequestBody FeedbackDto dto){
        SysUser sysUser = RequestUtils.getSysUser();
        feedbackService.list(dto,sysUser);
        return ResponseResult.success();
    }

    @PostMapping("/insert")
    @SysLogs(fun = "新增反馈")
    public ResponseResult insert(@RequestBody FeedbackDto dto){
        SysUser sysUser = RequestUtils.getSysUser();
        feedbackService.insert(dto,sysUser);
        return ResponseResult.success();
    }

    @PutMapping("/update")
    @SysLogs(fun = "修改反馈")
    public ResponseResult update(@RequestBody FeedbackDto dto){
        SysUser sysUser = RequestUtils.getSysUser();
        feedbackService.update(dto,sysUser);
        return ResponseResult.success();
    }
}
