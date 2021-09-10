package wpy.personal.novel.novel.system.mapper;

import org.apache.ibatis.annotations.Param;
import wpy.personal.novel.pojo.entity.SysPermission;
import wpy.personal.novel.pojo.entity.SysRolePermission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 角色权限关联表 Mapper 接口
 * </p>
 *
 * @author wangpanyin
 * @since 2021-09-07
 */
public interface SysRolePermissionMapper extends BaseMapper<SysRolePermission> {

    /**
     * 通过角色code集合查询权限
     * @param roleCodeList
     * @return
     */
    List<SysPermission> getPermissionByRoleCodeList(@Param("list")List<String> roleCodeList);
}
