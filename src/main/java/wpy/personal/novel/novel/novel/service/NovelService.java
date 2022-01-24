package wpy.personal.novel.novel.novel.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;
import wpy.personal.novel.pojo.bo.NovelBo;
import wpy.personal.novel.pojo.dto.NovelDto;
import wpy.personal.novel.pojo.dto.NovelOrderDto;
import wpy.personal.novel.pojo.entity.Novel;
import wpy.personal.novel.pojo.entity.SysUser;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * <p>
 * 小说分卷表 服务类
 * </p>
 *
 * @author wangpanyin
 * @since 2021-09-07
 */
public interface NovelService extends IService<Novel> {

    /**
     * 添加分卷信息
     * @param novelDto
     * @param sysUser
     * @return
     */
    Novel addNovel(NovelDto novelDto, SysUser sysUser);

    /**
     * 获取某一卷的信息
     * @param volumeId
     * @param sysUser
     * @return
     */
    NovelBo getNovelInfo(String volumeId, SysUser sysUser);

    /**
     * 批量上传分卷信息
     * @param files
     * @param seriesId
     * @param sysUser
     */
    void batchUploadNovel(MultipartFile[] files, String seriesId, SysUser sysUser);

    /**
     * 更新排序规则
     * @param novelOrderDto
     * @param sysUser
     */
    void updateOrder(NovelOrderDto novelOrderDto, SysUser sysUser);

    /**
     * 删除分卷
     * @param idList
     * @param sysUser
     */
    void deleteNovel(List<String> idList, SysUser sysUser);

    /**
     * 获取需要被删除的文件id集合
     * @param novelList
     * @param idList
     * @return
     */
    List<String> getDeleteFileIds(List<Novel> novelList, List<String> idList);

    /**
     * 上传正常单个上传小说
     * @param novelDto
     * @param sysUser
     * @return
     */
    Novel addUploadNovel(NovelDto novelDto, SysUser sysUser);

    /**
     * 获取分卷列表
     * @param seriesId
     * @param sysUser
     * @return
     */
    List<Novel> getNovelList(String seriesId, SysUser sysUser);

    /**
     * 更新分卷
     * @param novelDto
     * @param sysUser
     * @return
     */
    Novel updateNovel(NovelDto novelDto, SysUser sysUser);

    /**
     * 下载
     * @param volumeId
     * @param sysUser
     * @param request
     * @param response
     */
    void download(String volumeId, SysUser sysUser, HttpServletRequest request, HttpServletResponse response);
}
