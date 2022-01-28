package wpy.personal.novel.novel.novel.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;
import wpy.personal.novel.pojo.bo.UploadListBo;
import wpy.personal.novel.pojo.entity.NovelFile;
import wpy.personal.novel.pojo.entity.SysUser;
import wpy.personal.novel.pojo.vo.UploadListVo;
import wpy.personal.novel.utils.pageUtils.RequestPageUtils;

import java.util.List;

/**
 * <p>
 * 小说文件表 服务类
 * </p>
 *
 * @author wangpanyin
 * @since 2021-09-07
 */
public interface NovelFileService extends IService<NovelFile> {

    /**
     * 保存文件
     * @param volumeFile
     * @param novelId
     * @param userId
     * @return
     */
    NovelFile saveFile(MultipartFile volumeFile, String novelId, String userId);

    void deleteFiles(List<String> deleteFileIdList);

    /**
     * 查询上传记录
     * @param dto
     * @param sysUser
     * @return
     */
    Page<UploadListBo> list(RequestPageUtils<UploadListVo> dto, SysUser sysUser);
}
