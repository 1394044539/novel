package wpy.personal.novel.novel.novel.service;

import org.springframework.web.multipart.MultipartFile;
import wpy.personal.novel.pojo.bo.NovelVolumeBo;
import wpy.personal.novel.pojo.dto.VolumeDto;
import wpy.personal.novel.pojo.entity.NovelVolume;
import com.baomidou.mybatisplus.extension.service.IService;
import wpy.personal.novel.pojo.entity.SysUser;

/**
 * <p>
 * 小说分卷表 服务类
 * </p>
 *
 * @author wangpanyin
 * @since 2021-09-07
 */
public interface NovelVolumeService extends IService<NovelVolume> {

    /**
     * 添加分卷信息
     * @param volumeDto
     * @param sysUser
     * @return
     */
    NovelVolume addVolume(VolumeDto volumeDto, SysUser sysUser);

    /**
     * 获取某一卷的信息
     * @param volumeId
     * @param sysUser
     * @return
     */
    NovelVolumeBo getVolumeInfo(String volumeId, SysUser sysUser);

    /**
     * 批量上传分卷信息
     * @param files
     * @param novelId
     * @param sysUser
     */
    void batchUploadVolume(MultipartFile[] files, String novelId, SysUser sysUser);
}
