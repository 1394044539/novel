package wpy.personal.novel.novel.novel.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import wpy.personal.novel.base.annotation.SysLogs;
import wpy.personal.novel.base.result.ResponseResult;
import wpy.personal.novel.novel.novel.service.NovelFileService;
import wpy.personal.novel.pojo.bo.UploadListBo;
import wpy.personal.novel.pojo.entity.SysUser;
import wpy.personal.novel.pojo.vo.UploadListVo;
import wpy.personal.novel.utils.RequestUtils;
import wpy.personal.novel.utils.pageUtils.RequestPageUtils;

/**
 * <p>
 * 小说文件表 前端控制器
 * </p>
 *
 * @author wangpanyin
 * @since 2021-09-07
 */
@RestController
@RequestMapping("/novelFile")
public class NovelFileController {

    @Autowired
    private NovelFileService novelFileService;

    @PostMapping("/list")
    @SysLogs(fun = "查询上传记录")
    public ResponseResult list(@RequestBody RequestPageUtils<UploadListVo> dto){
        SysUser sysUser = RequestUtils.getSysUser();
        Page<UploadListBo> page = novelFileService.list(dto,sysUser);
        return ResponseResult.success(page);
    }
}
