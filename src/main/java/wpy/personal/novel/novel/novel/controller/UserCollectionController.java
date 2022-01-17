package wpy.personal.novel.novel.novel.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import wpy.personal.novel.base.annotation.SysLogs;
import wpy.personal.novel.base.result.ResponseResult;
import wpy.personal.novel.novel.novel.service.UserCollectionService;
import wpy.personal.novel.pojo.bo.CollectionBo;
import wpy.personal.novel.pojo.bo.CollectionTableBo;
import wpy.personal.novel.pojo.dto.UserCollectionDto;
import wpy.personal.novel.pojo.entity.SysUser;
import wpy.personal.novel.pojo.entity.UserCollection;
import wpy.personal.novel.utils.RequestUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * <p>
 * 用户收藏表 前端控制器
 * </p>
 *
 * @author wangpanyin
 * @since 2021-09-07
 */
@RestController
@RequestMapping("/userCollection")
public class UserCollectionController {

    @Autowired
    private UserCollectionService userCollectionService;

    @PutMapping("/addCollection")
    @SysLogs(fun = "加入收藏")
    public ResponseResult addCollection(@RequestBody UserCollectionDto userCollectionDto){
        SysUser sysUser = RequestUtils.getSysUser();
        UserCollection userCollection = userCollectionService.addCollection(userCollectionDto,sysUser);
        return ResponseResult.success(userCollection);
    }

    @GetMapping("/getCollection")
    @SysLogs(fun = "获取收藏记录")
    public ResponseResult getCollection(@RequestParam("id")String id,@RequestParam("collectionType")String collectionType){
        SysUser sysUser = RequestUtils.getSysUser();
        UserCollection userCollection = userCollectionService.getCollection(id,collectionType,sysUser);
        return ResponseResult.success(userCollection);
    }

    @DeleteMapping("/deleteCollection")
    @SysLogs(fun = "删除收藏")
    public ResponseResult deleteCollection(@RequestBody UserCollectionDto userCollectionDto){
        userCollectionService.deleteCollection(userCollectionDto.getCollectionId(),userCollectionDto.getCollectionType());
        return ResponseResult.success();
    }

    @DeleteMapping("/batchCancelCollection")
    @SysLogs(fun = "批量删除收藏")
    public ResponseResult batchCancelCollection(@RequestBody List<UserCollectionDto> list){
        userCollectionService.batchCancelCollection(list);
        return ResponseResult.success();
    }

    @DeleteMapping("/removeAll")
    @SysLogs(fun = "清空收藏")
    public ResponseResult batchCancelCollection(@RequestBody UserCollectionDto dto){
        SysUser sysUser = RequestUtils.getSysUser();
        userCollectionService.removeAll(dto,sysUser);
        return ResponseResult.success();
    }

    @PostMapping("/updateCollection")
    @SysLogs(fun = "修改收藏")
    public ResponseResult updateCollection(@RequestBody UserCollectionDto userCollectionDto){
        SysUser sysUser = RequestUtils.getSysUser();
        userCollectionService.updateCollection(userCollectionDto,sysUser);
        return ResponseResult.success();
    }

    @PostMapping("/getCollectionList")
    @SysLogs(fun = "获取收藏列表")
    public ResponseResult getCollectionList(@RequestBody UserCollectionDto userCollectionDto){
        SysUser sysUser = RequestUtils.getSysUser();
        List<CollectionBo> list = userCollectionService.getCollectionList(userCollectionDto,sysUser);
        return ResponseResult.success(list);
    }

    @PostMapping("/list")
    @SysLogs(fun = "收藏列表管理")
    public ResponseResult list(@RequestBody UserCollectionDto userCollectionDto){
        SysUser sysUser = RequestUtils.getSysUser();
        Page<CollectionTableBo> page = userCollectionService.list(userCollectionDto,sysUser);
        return ResponseResult.success(page);
    }

    @PostMapping("/copyOrMove")
    @SysLogs(fun = "移动或复制")
    public ResponseResult copyOrMove(@RequestBody UserCollectionDto userCollectionDto){
        SysUser sysUser = RequestUtils.getSysUser();
        userCollectionService.copyOrMove(userCollectionDto,sysUser);
        return ResponseResult.success();
    }

    @GetMapping("/download")
    @SysLogs(fun = "下载")
    public void download(UserCollectionDto userCollectionDto, HttpServletRequest request, HttpServletResponse response){
        SysUser sysUser = RequestUtils.getSysUser();
        userCollectionService.download(userCollectionDto,sysUser,request,response);
    }

    @GetMapping("/downloadAll")
    @SysLogs(fun = "下载全部")
    public void downloadAll(HttpServletRequest request, HttpServletResponse response){
        SysUser sysUser = RequestUtils.getSysUser();
        userCollectionService.downloadAll(sysUser,request,response);
    }

}
