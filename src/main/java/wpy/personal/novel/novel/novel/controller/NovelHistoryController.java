package wpy.personal.novel.novel.novel.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import wpy.personal.novel.base.annotation.SysLogs;
import wpy.personal.novel.base.result.ResponseResult;
import wpy.personal.novel.novel.novel.service.NovelHistoryService;
import wpy.personal.novel.pojo.dto.HistoryDto;
import wpy.personal.novel.pojo.entity.NovelHistory;
import wpy.personal.novel.pojo.entity.SysUser;
import wpy.personal.novel.utils.RequestUtils;
import wpy.personal.novel.utils.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author wangpanyin
 * @since 2021-09-12
 */
@RestController
@RequestMapping("/novelHistory")
public class NovelHistoryController {

    @Autowired
    private NovelHistoryService novelHistoryService;

    @PostMapping("/getHistoryList")
    @SysLogs(fun = "获取历史或书签列表记录")
    public ResponseResult getHistoryList( HistoryDto dto){
        SysUser sysUser = RequestUtils.getSysUser();
        novelHistoryService.getHistoryList(dto,sysUser);
        return ResponseResult.success();
    }

    @PutMapping("/saveHistory")
    @SysLogs(fun = "记录历史记录")
    public ResponseResult saveHistory(HistoryDto historyDto){
        SysUser sysUser = RequestUtils.getSysUser();
        String ip = RequestUtils.getIp(null);
        NovelHistory novelHistory = novelHistoryService.saveHistory(historyDto,sysUser,ip);
        return ResponseResult.success(novelHistory);
    }

    @GetMapping("/getHistory")
    @SysLogs(fun = "获取历史记录")
    public ResponseResult getHistory(@RequestParam("historyId")String historyId){
        SysUser sysUser = RequestUtils.getSysUser();
        NovelHistory novelHistory = novelHistoryService.getHistory(historyId,sysUser);
        return ResponseResult.success(novelHistory);
    }
}
