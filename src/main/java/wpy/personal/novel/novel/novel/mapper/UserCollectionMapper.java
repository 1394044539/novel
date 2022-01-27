package wpy.personal.novel.novel.novel.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import wpy.personal.novel.pojo.bo.CollectionBo;
import wpy.personal.novel.pojo.bo.CollectionTableBo;
import wpy.personal.novel.pojo.dto.UserCollectionDto;
import wpy.personal.novel.pojo.entity.SysUser;
import wpy.personal.novel.pojo.entity.UserCollection;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import wpy.personal.novel.pojo.vo.UserCollectionListVo;

import java.util.List;

/**
 * <p>
 * 用户收藏表 Mapper 接口
 * </p>
 *
 * @author wangpanyin
 * @since 2021-09-07
 */
public interface UserCollectionMapper extends BaseMapper<UserCollection> {

    /**
     * 查询收藏
     * @param dto
     * @param sysUser
     * @return
     */
    List<CollectionBo> selectCollections(@Param("dto") UserCollectionDto dto,@Param("user") SysUser sysUser);

    /**
     * 查询全部的目录
     * @param parentId
     * @param userId
     * @return
     */
    List<UserCollection> getAllCollectionCatalog(@Param("parentId") String parentId,@Param("userId") String userId);

    /**
     * 分页查询
     * @param dto
     * @param page
     * @return
     */
    List<CollectionTableBo> list(@Param("dto") UserCollectionListVo dto, Page<CollectionTableBo> page);
}
