package wpy.personal.novel.novel.novel.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import wpy.personal.novel.pojo.bo.CollectionBo;
import wpy.personal.novel.pojo.bo.CollectionTableBo;
import wpy.personal.novel.pojo.dto.UserCollectionDto;
import wpy.personal.novel.pojo.entity.SysUser;
import wpy.personal.novel.pojo.entity.UserCollection;
import com.baomidou.mybatisplus.extension.service.IService;
import wpy.personal.novel.pojo.vo.UserCollectionListVo;
import wpy.personal.novel.utils.pageUtils.RequestPageUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * <p>
 * 用户收藏表 服务类
 * </p>
 *
 * @author wangpanyin
 * @since 2021-09-07
 */
public interface UserCollectionService extends IService<UserCollection> {

    /**
     * 加入收藏
     * @param userCollectionDto
     * @param sysUser
     * @return
     */
    UserCollection addCollection(UserCollectionDto userCollectionDto, SysUser sysUser);

    /**
     * 获取收藏信息
     * @param id
     * @param type
     * @param sysUser
     * @return
     */
    UserCollection getCollection(String id, String type, SysUser sysUser);

    /**
     * 取消收藏
     * @param collectionId
     * @param collectionType
     */
    void deleteCollection(String collectionId, String collectionType);

    /**
     * 获取收藏列表
     * @param parentId
     * @param sysUser
     * @return
     */
    List<CollectionBo> getCollectionList(UserCollectionDto parentId, SysUser sysUser);

    /**
     * 修改收藏
     * @param userCollectionDto
     * @param sysUser
     * @return
     */
    void updateCollection(UserCollectionDto userCollectionDto, SysUser sysUser);

    /**
     * 复制或移动
     * @param userCollectionDto
     * @param sysUser
     */
    void copyOrMove(UserCollectionDto userCollectionDto, SysUser sysUser);

    /**
     * 下载收藏
     * @param userCollectionDto
     * @param sysUser
     * @param request
     * @param response
     */
    void download(UserCollectionDto userCollectionDto, SysUser sysUser, HttpServletRequest request, HttpServletResponse response);

    /**
     * 下载全部收藏
     * @param sysUser
     * @param request
     * @param response
     */
    void downloadAll(SysUser sysUser, HttpServletRequest request, HttpServletResponse response);

    /**
     * 分页查询
     * @param param
     * @param sysUser
     * @return
     */
    Page<CollectionTableBo> list(RequestPageUtils<UserCollectionListVo> param, SysUser sysUser);

    /**
     * 批量删除收藏
     * @param list
     */
    void batchCancelCollection(List<UserCollectionDto> list);

    /**
     * 清空收藏
     * @param dto
     * @param sysUser
     */
    void removeAll(UserCollectionDto dto, SysUser sysUser);

    /**
     * 清除小说本体被删除的小说
     * @param sysUser
     */
    void clearInvalidCollection(SysUser sysUser);
}
