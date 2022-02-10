package wpy.personal.novel.novel.novel.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import wpy.personal.novel.base.annotation.SysLogs;
import wpy.personal.novel.base.result.ResponseResult;
import wpy.personal.novel.novel.novel.service.NovelHistoryService;
import wpy.personal.novel.pojo.dto.HistoryDto;
import wpy.personal.novel.pojo.entity.NovelHistory;
import wpy.personal.novel.pojo.entity.SysUser;
import wpy.personal.novel.pojo.vo.HistoryListVo;
import wpy.personal.novel.utils.RequestUtils;
import wpy.personal.novel.utils.pageUtils.RequestPageUtils;

import java.util.List;

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
    public ResponseResult getHistoryList(@RequestBody RequestPageUtils<HistoryListVo> dto){
        SysUser sysUser = RequestUtils.getSysUser();
        return ResponseResult.success(novelHistoryService.getHistoryList(dto,sysUser));
    }

    @PutMapping("/saveHistory")
    @SysLogs(fun = "记录历史记录")
    public ResponseResult saveHistory(@RequestBody HistoryDto historyDto){
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

    @DeleteMapping("/batchDelete")
    @SysLogs(fun = "删除历史")
    public ResponseResult batchDelete(@RequestBody List<String> ids){
        SysUser sysUser = RequestUtils.getSysUser();
        novelHistoryService.batchDelete(ids,sysUser);
        return ResponseResult.success();
    }

    @DeleteMapping("/clearHistory")
    @SysLogs(fun = "删除历史")
    public ResponseResult clearHistory(@RequestBody HistoryDto historyDto){
        SysUser sysUser = RequestUtils.getSysUser();
        novelHistoryService.clearHistory(historyDto,sysUser);
        return ResponseResult.success();
    }
}
