package wpy.personal.novel.novel.novel.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import wpy.personal.novel.base.annotation.SysLogs;
import wpy.personal.novel.base.result.ResponseResult;
import wpy.personal.novel.novel.novel.service.NovelVolumeService;
import wpy.personal.novel.pojo.bo.NovelVolumeBo;
import wpy.personal.novel.pojo.dto.VolumeDto;
import wpy.personal.novel.pojo.entity.NovelVolume;
import wpy.personal.novel.pojo.entity.SysUser;
import wpy.personal.novel.utils.RequestUtils;

import javax.servlet.http.HttpServletRequest;

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
    public ResponseResult addVolume(HttpServletRequest request, VolumeDto volumeDto){
        SysUser sysUser = RequestUtils.getSysUser(request);
        NovelVolume novelVolume = novelVolumeService.addVolume(volumeDto,sysUser);
        return ResponseResult.success(novelVolume);
    }

    @GetMapping("/getVolumeInfo")
    @SysLogs(fun = "获取分卷信息")
    public ResponseResult getVolumeInfo(HttpServletRequest request,@RequestParam("volumeId")String volumeId){
        SysUser sysUser = RequestUtils.getSysUser(request);
        NovelVolumeBo novelVolumeBo = novelVolumeService.getVolumeInfo(volumeId,sysUser);
        return ResponseResult.success(novelVolumeBo);
    }
}
