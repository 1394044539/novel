package wpy.personal.novel.novel.novel.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;
import wpy.personal.novel.base.annotation.SysLogs;
import wpy.personal.novel.base.result.ResponseResult;
import wpy.personal.novel.novel.novel.service.NovelVolumeService;
import wpy.personal.novel.pojo.bo.NovelVolumeBo;
import wpy.personal.novel.pojo.dto.VolumeDto;
import wpy.personal.novel.pojo.dto.VolumeOrderDto;
import wpy.personal.novel.pojo.entity.NovelVolume;
import wpy.personal.novel.pojo.entity.SysUser;
import wpy.personal.novel.utils.RequestUtils;

import javax.servlet.http.HttpServletRequest;
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
@RequestMapping("/novelVolume")
public class NovelVolumeController {

    @Autowired
    private NovelVolumeService novelVolumeService;

    @PutMapping("/addVolume")
    @SysLogs(fun = "添加分卷内容")
    public ResponseResult addVolume(VolumeDto volumeDto){
        SysUser sysUser = RequestUtils.getSysUser();
        NovelVolume novelVolume = novelVolumeService.addVolumeUpdateNovel(volumeDto,sysUser);
        return ResponseResult.success(novelVolume);
    }

    @GetMapping("/getVolumeInfo")
    @SysLogs(fun = "获取分卷信息")
    public ResponseResult getVolumeInfo(@RequestParam("volumeId")String volumeId){
        SysUser sysUser = RequestUtils.getSysUser();
        NovelVolumeBo novelVolumeBo = novelVolumeService.getVolumeInfo(volumeId,sysUser);
        return ResponseResult.success(novelVolumeBo);
    }

    @GetMapping("/getVolumeList")
    @SysLogs(fun = "获取分卷信息")
    public ResponseResult getVolumeList(@RequestParam("novelId")String novelId){
        SysUser sysUser = RequestUtils.getSysUser();
        return ResponseResult.success(novelVolumeService.getVolumeList(novelId,sysUser));
    }

    @PostMapping("/batchUploadVolume")
    @SysLogs(fun = "批量上传分卷信息")
    public ResponseResult batchUploadVolume(@RequestParam("files") MultipartFile[] files
            ,@RequestParam("novelId")String novelId){
        SysUser sysUser = RequestUtils.getSysUser();
        novelVolumeService.batchUploadVolume(files,novelId,sysUser);
        return ResponseResult.success();
    }

    @PutMapping("/updateOrder")
    @SysLogs(fun = "更新排序规则")
    public ResponseResult updateOrder(@RequestBody VolumeOrderDto volumeOrderDto){
        SysUser sysUser = RequestUtils.getSysUser();
        novelVolumeService.updateOrder(volumeOrderDto,sysUser);
        return ResponseResult.success();
    }

    @DeleteMapping("/deleteVolume")
    @SysLogs(fun = "删除分卷")
    public ResponseResult deleteVolume(@RequestBody List<String> idList){
        SysUser sysUser = RequestUtils.getSysUser();
        novelVolumeService.deleteVolume(idList,sysUser);
        return ResponseResult.success();
    }
}
