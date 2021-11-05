package wpy.personal.novel.novel.novel.service;

import org.springframework.web.multipart.MultipartFile;
import wpy.personal.novel.pojo.bo.NovelVolumeBo;
import wpy.personal.novel.pojo.dto.VolumeDto;
import wpy.personal.novel.pojo.dto.VolumeOrderDto;
import wpy.personal.novel.pojo.entity.NovelVolume;
import com.baomidou.mybatisplus.extension.service.IService;
import wpy.personal.novel.pojo.entity.SysUser;

import java.util.List;

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

    /**
     * 更新排序规则
     * @param volumeOrderDto
     * @param sysUser
     */
    void updateOrder(VolumeOrderDto volumeOrderDto, SysUser sysUser);

    /**
     * 删除分卷
     * @param idList
     * @param sysUser
     */
    void deleteVolume(List<String> idList, SysUser sysUser);

    /**
     * 获取需要被删除的文件id集合
     * @param novelVolumeList
     * @param idList
     * @return
     */
    List<String> getDeleteFileIds(List<NovelVolume> novelVolumeList, List<String> idList);
}
