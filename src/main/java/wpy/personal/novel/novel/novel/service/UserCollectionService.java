package wpy.personal.novel.novel.novel.service;

import wpy.personal.novel.pojo.bo.CollectionBo;
import wpy.personal.novel.pojo.dto.UserCollectionDto;
import wpy.personal.novel.pojo.entity.SysUser;
import wpy.personal.novel.pojo.entity.UserCollection;
import com.baomidou.mybatisplus.extension.service.IService;

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
}
