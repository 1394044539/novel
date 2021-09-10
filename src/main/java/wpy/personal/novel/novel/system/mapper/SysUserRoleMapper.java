package wpy.personal.novel.novel.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import wpy.personal.novel.pojo.entity.SysRole;
import wpy.personal.novel.pojo.entity.SysUserRole;

import java.util.List;

/**
 * <p>
 * 用户角色关联表 Mapper 接口
 * </p>
 *
 * @author wangpanyin
 * @since 2021-09-07
 */
public interface SysUserRoleMapper extends BaseMapper<SysUserRole> {

    /**
     * 通过userId拿到用户角色
     * @param userId
     * @return
     */
    List<SysRole> getRoleListByUserId(String userId);
}
