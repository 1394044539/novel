package wpy.personal.novel.novel.novel.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import wpy.personal.novel.base.annotation.SysLogs;
import wpy.personal.novel.base.result.ResponseResult;
import wpy.personal.novel.novel.novel.service.SeriesService;
import wpy.personal.novel.pojo.bo.SeriesBo;
import wpy.personal.novel.pojo.dto.SeriesDto;
import wpy.personal.novel.pojo.entity.Series;
import wpy.personal.novel.pojo.entity.SysUser;
import wpy.personal.novel.pojo.vo.SeriesListVo;
import wpy.personal.novel.utils.RequestUtils;
import wpy.personal.novel.utils.pageUtils.RequestPageUtils;

import java.util.List;

/**
 * <p>
 * 系列表 前端控制器
 * </p>
 *
 * @author wangpanyin
 * @since 2021-09-07
 */
@RestController
@RequestMapping("/series")
public class SeriesController {

    @Autowired
    private SeriesService seriesService;

    @PostMapping("/getSeriesList")
    @SysLogs(fun = "获取系列列表")
    public ResponseResult getSeriesList( @RequestBody RequestPageUtils<SeriesListVo> param){
        SysUser sysUser = RequestUtils.getSysUser();
        return ResponseResult.success(seriesService.getSeriesList(param,sysUser));
    }

    @GetMapping("/getSeriesInfo")
    @SysLogs(fun = "获取系列信息")
    public ResponseResult getSeriesInfo(@RequestParam("seriesId") String seriesId){
        SysUser sysUser = RequestUtils.getSysUser();
        SeriesBo seriesBo = seriesService.getSeriesInfo(seriesId,sysUser);
        return ResponseResult.success(seriesBo);
    }

    @PostMapping("/addSeries")
    @SysLogs(fun = "新增系列")
    public ResponseResult addSeries(SeriesDto seriesDto){
        SysUser sysUser = RequestUtils.getSysUser();
        Series series = seriesService.addSeries(seriesDto,sysUser);
        return ResponseResult.success(series);
    }

    @PutMapping("/updateSeries")
    @SysLogs(fun = "修改系列")
    public ResponseResult updateSeries(SeriesDto seriesDto){
        SysUser sysUser = RequestUtils.getSysUser();
        Series series = seriesService.updateSeries(seriesDto,sysUser);
        return ResponseResult.success(series);
    }

    @DeleteMapping("/deleteSeries")
    @SysLogs(fun = "删除系列")
    public ResponseResult deleteSeries(@RequestBody List<String> idList){
        SysUser sysUser = RequestUtils.getSysUser();
        seriesService.deleteSeries(idList,sysUser);
        return ResponseResult.success();
    }

    @PostMapping("/quickUpload")
    @SysLogs(fun = "快速上传")
    public ResponseResult quickUpload(@RequestParam("file")MultipartFile file){
        SysUser sysUser = RequestUtils.getSysUser();
        Series series = seriesService.quickUpload(file,sysUser);
        return ResponseResult.success(series);
    }



}
