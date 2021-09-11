package wpy.personal.novel.novel.novel.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import wpy.personal.novel.base.annotation.SysLogs;
import wpy.personal.novel.base.result.ResponseResult;
import wpy.personal.novel.novel.novel.service.NovelService;
import wpy.personal.novel.pojo.bo.NovelBo;
import wpy.personal.novel.pojo.dto.NovelDto;
import wpy.personal.novel.pojo.entity.Novel;
import wpy.personal.novel.pojo.entity.SysUser;
import wpy.personal.novel.utils.RequestUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 * 小说表 前端控制器
 * </p>
 *
 * @author wangpanyin
 * @since 2021-09-07
 */
@RestController
@RequestMapping("/novel")
public class NovelController {

    @Autowired
    private NovelService novelService;

    @PostMapping("/getNovelList")
    @SysLogs(fun = "获取小说列表")
    public ResponseResult getNovelList(HttpServletRequest request, @RequestBody NovelDto novelDto){
        SysUser sysUser = RequestUtils.getSysUser(request);
        return ResponseResult.success(novelService.getNovelList(novelDto,sysUser));
    }

    @GetMapping("/getNovelInfo")
    @SysLogs(fun = "获取小说信息")
    public ResponseResult getNovelInfo(HttpServletRequest request,@RequestParam("novelId") String novelId){
        SysUser sysUser = RequestUtils.getSysUser(request);
        NovelBo novelBo = novelService.getNovelInfo(novelId,sysUser);
        return ResponseResult.success(novelBo);
    }

    @PutMapping("/addNovel")
    @SysLogs(fun = "新增小说")
    public ResponseResult addNovel(HttpServletRequest request,@RequestBody NovelDto novelDto){
        SysUser sysUser = RequestUtils.getSysUser(request);
        Novel  novel = novelService.addNovel(novelDto,sysUser);
        return ResponseResult.success(novel);
    }

    @PostMapping("/updateNovel")
    @SysLogs(fun = "修改小说")
    public ResponseResult updateNovel(HttpServletRequest request,@RequestBody NovelDto novelDto){
        SysUser sysUser = RequestUtils.getSysUser(request);
        Novel  novel = novelService.updateNovel(novelDto,sysUser);
        return ResponseResult.success(novel);
    }

    @DeleteMapping("/deleteNovel")
    @SysLogs(fun = "删除小说")
    public ResponseResult deleteNovel(HttpServletRequest request,@RequestBody List<String> idList){
        SysUser sysUser = RequestUtils.getSysUser(request);
        novelService.deleteNovel(idList,sysUser);
        return ResponseResult.success();
    }



}
