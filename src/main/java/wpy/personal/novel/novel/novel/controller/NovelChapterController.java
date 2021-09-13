package wpy.personal.novel.novel.novel.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import wpy.personal.novel.base.annotation.SysLogs;
import wpy.personal.novel.base.result.ResponseResult;
import wpy.personal.novel.novel.novel.service.NovelChapterService;
import wpy.personal.novel.pojo.bo.ChapterBo;
import wpy.personal.novel.pojo.entity.NovelChapter;
import wpy.personal.novel.pojo.entity.SysUser;
import wpy.personal.novel.utils.RequestUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 * 小说章节表 前端控制器
 * </p>
 *
 * @author wangpanyin
 * @since 2021-09-07
 */
@RestController
@RequestMapping("/novelChapter")
public class NovelChapterController {

    @Autowired
    private NovelChapterService novelChapterService;

    @GetMapping("/getChapterContent")
    @SysLogs(fun = "获取章节内容")
    public ResponseResult getChapterContent(HttpServletRequest request, @RequestParam("chapterId")String chapterId){
        SysUser sysUser = RequestUtils.getSysUser(request);
        ChapterBo chapterBo = novelChapterService.getChapterContent(chapterId,sysUser);
        return ResponseResult.success(chapterBo);
    }

    @GetMapping("/getChapterList")
    @SysLogs(fun = "获得章节列表")
    public ResponseResult getChapterList(HttpServletRequest request,@RequestParam("volume_id")String volumeId){
        SysUser sysUser = RequestUtils.getSysUser(request);
        List<NovelChapter> list = novelChapterService.getChapterList(volumeId,sysUser);
        return ResponseResult.success(list);
    }
}
