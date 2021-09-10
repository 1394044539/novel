package wpy.personal.novel.novel.system.service;

import wpy.personal.novel.pojo.bo.UserPermissionBo;
import wpy.personal.novel.pojo.entity.SysRolePermission;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 角色权限关联表 服务类
 * </p>
 *
 * @author wangpanyin
 * @since 2021-09-07
 */
public interface SysRolePermissionService extends IService<SysRolePermission> {

    /**
     * 通过roleCode获取全部的权限信息
     * @param roleCodeList
     * @return
     */
    List<UserPermissionBo> getPermissionByRoleCodeList(List<String> roleCodeList);
}
