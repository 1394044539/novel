package wpy.personal.novel.novel.novel.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import wpy.personal.novel.base.annotation.SysLogs;
import wpy.personal.novel.base.result.ResponseResult;
import wpy.personal.novel.novel.novel.service.NovelService;
import wpy.personal.novel.pojo.bo.NovelBo;
import wpy.personal.novel.pojo.dto.NovelDto;
import wpy.personal.novel.pojo.entity.Novel;
import wpy.personal.novel.pojo.entity.SysUser;
import wpy.personal.novel.pojo.vo.SeriesListVo;
import wpy.personal.novel.utils.RequestUtils;
import wpy.personal.novel.utils.pageUtils.RequestPageUtils;

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
    public ResponseResult getNovelList( @RequestBody RequestPageUtils<SeriesListVo> param){
        SysUser sysUser = RequestUtils.getSysUser();
        return ResponseResult.success(novelService.getSeriesList(param,sysUser));
    }

    @GetMapping("/getNovelInfo")
    @SysLogs(fun = "获取小说信息")
    public ResponseResult getNovelInfo(@RequestParam("novelId") String novelId){
        SysUser sysUser = RequestUtils.getSysUser();
        NovelBo novelBo = novelService.getNovelInfo(novelId,sysUser);
        return ResponseResult.success(novelBo);
    }

    @PostMapping("/addNovel")
    @SysLogs(fun = "新增小说")
    public ResponseResult addNovel( NovelDto novelDto){
        SysUser sysUser = RequestUtils.getSysUser();
        Novel novel = novelService.addNovel(novelDto,sysUser);
        return ResponseResult.success(novel);
    }

    @PutMapping("/updateNovel")
    @SysLogs(fun = "修改小说")
    public ResponseResult updateNovel(NovelDto novelDto){
        SysUser sysUser = RequestUtils.getSysUser();
        Novel  novel = novelService.updateNovel(novelDto,sysUser);
        return ResponseResult.success(novel);
    }

    @DeleteMapping("/deleteNovel")
    @SysLogs(fun = "删除小说")
    public ResponseResult deleteNovel(@RequestBody List<String> idList){
        SysUser sysUser = RequestUtils.getSysUser();
        novelService.deleteNovel(idList,sysUser);
        return ResponseResult.success();
    }

    @PostMapping("/quickUpload")
    @SysLogs(fun = "快速上传")
    public ResponseResult quickUpload(@RequestParam("file")MultipartFile file){
        SysUser sysUser = RequestUtils.getSysUser();
        Novel novel = novelService.quickUpload(file,sysUser);
        return ResponseResult.success(novel);
    }



}
