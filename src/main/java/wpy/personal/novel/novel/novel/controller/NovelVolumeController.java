package wpy.personal.novel.novel.novel.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import wpy.personal.novel.base.annotation.SysLogs;
import wpy.personal.novel.base.result.ResponseResult;
import wpy.personal.novel.novel.novel.service.NovelVolumeService;
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

    @PostMapping("/addVolume")
    @SysLogs(fun = "添加分卷内容")
    public ResponseResult addVolume(HttpServletRequest request, @RequestBody VolumeDto volumeDto){
        SysUser sysUser = RequestUtils.getSysUser(request);
        NovelVolume novelVolume = novelVolumeService.addVolume(volumeDto,sysUser);
        return ResponseResult.success(novelVolume);
    }

}
