package wpy.personal.novel.novel.novel.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import wpy.personal.novel.base.annotation.SysLogs;
import wpy.personal.novel.base.result.ResponseResult;
import wpy.personal.novel.novel.novel.service.UserCollectionService;
import wpy.personal.novel.pojo.dto.UserCollectionDto;
import wpy.personal.novel.pojo.entity.SysUser;
import wpy.personal.novel.pojo.entity.UserCollection;
import wpy.personal.novel.utils.RequestUtils;

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
        userCollectionService.deleteCollection(userCollectionDto.getCollectionId());
        return ResponseResult.success();
    }

    @PostMapping("/getCollectionList")
    @SysLogs(fun = "获取收藏列表")
    public ResponseResult getCollectionList(@RequestBody UserCollectionDto userCollectionDto){
        SysUser sysUser = RequestUtils.getSysUser();
        List<UserCollection> list = userCollectionService.getCollectionList(userCollectionDto,sysUser);
        return ResponseResult.success(list);
    }
}
