package wpy.personal.novel.novel.novel.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import wpy.personal.novel.base.annotation.SysLogs;
import wpy.personal.novel.base.result.ResponseResult;
import wpy.personal.novel.novel.novel.service.NovelService;
import wpy.personal.novel.pojo.bo.NovelBo;
import wpy.personal.novel.pojo.dto.NovelDto;
import wpy.personal.novel.pojo.dto.NovelOrderDto;
import wpy.personal.novel.pojo.entity.Novel;
import wpy.personal.novel.pojo.entity.SysUser;
import wpy.personal.novel.utils.RequestUtils;

import java.util.List;

/**
 * <p>
 * 小说分卷表 前端控制器
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

    @PutMapping("/addNovel")
    @SysLogs(fun = "添加小说内容")
    public ResponseResult addNovel(NovelDto novelDto){
        SysUser sysUser = RequestUtils.getSysUser();
        Novel novel = novelService.addUploadNovel(novelDto,sysUser);
        return ResponseResult.success(novel);
    }

    @PostMapping("/updateNovel")
    @SysLogs(fun = "更新小说")
    public ResponseResult updateNovel(NovelDto novelDto){
        SysUser sysUser = RequestUtils.getSysUser();
        Novel novel = novelService.updateNovel(novelDto,sysUser);
        return ResponseResult.success(novel);
    }

    @GetMapping("/getNovelInfo")
    @SysLogs(fun = "获取小说信息")
    public ResponseResult getNovelInfo(@RequestParam("novelId")String novelId){
        SysUser sysUser = RequestUtils.getSysUser();
        NovelBo novelBo = novelService.getNovelInfo(novelId,sysUser);
        return ResponseResult.success(novelBo);
    }

    @GetMapping("/getNovelList")
    @SysLogs(fun = "获取小说列表")
    public ResponseResult getNovelList(@RequestParam("seriesId")String seriesId){
        SysUser sysUser = RequestUtils.getSysUser();
        return ResponseResult.success(novelService.getNovelList(seriesId,sysUser));
    }

    @PostMapping("/batchUploadNovel")
    @SysLogs(fun = "批量上传小说信息")
    public ResponseResult batchUploadNovel(@RequestParam("files") MultipartFile[] files
            ,@RequestParam("seriesId")String seriesId){
        SysUser sysUser = RequestUtils.getSysUser();
        novelService.batchUploadNovel(files,seriesId,sysUser);
        return ResponseResult.success();
    }

    @PutMapping("/updateOrder")
    @SysLogs(fun = "更新排序规则")
    public ResponseResult updateOrder(@RequestBody NovelOrderDto novelOrderDto){
        SysUser sysUser = RequestUtils.getSysUser();
        novelService.updateOrder(novelOrderDto,sysUser);
        return ResponseResult.success();
    }

    @DeleteMapping("/deleteNovel")
    @SysLogs(fun = "删除分卷")
    public ResponseResult deleteNovel(@RequestBody List<String> idList){
        SysUser sysUser = RequestUtils.getSysUser();
        novelService.deleteNovel(idList,sysUser);
        return ResponseResult.success();
    }
}
