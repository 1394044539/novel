package wpy.personal.novel.novel.system.service.impl;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import wpy.personal.novel.pojo.bo.UserPermissionBo;
import wpy.personal.novel.pojo.entity.SysPermission;
import wpy.personal.novel.pojo.entity.SysRolePermission;
import wpy.personal.novel.novel.system.mapper.SysRolePermissionMapper;
import wpy.personal.novel.novel.system.service.SysRolePermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import wpy.personal.novel.utils.ListUtils;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * <p>
 * 角色权限关联表 服务实现类
 * </p>
 *
 * @author wangpanyin
 * @since 2021-09-07
 */
@Service
@Transactional
public class SysRolePermissionServiceImpl extends ServiceImpl<SysRolePermissionMapper, SysRolePermission> implements SysRolePermissionService {

    @Autowired
    private SysRolePermissionMapper sysRolePermissionMapper;

    @Override
    public List<UserPermissionBo> getPermissionByRoleCodeList(List<String> roleCodeList) {
        List<SysPermission> sysPermissionList = sysRolePermissionMapper.getPermissionByRoleCodeList(roleCodeList);
        List<UserPermissionBo> userPermissionList = Lists.newArrayList();
        for (SysPermission sysPermission : sysPermissionList) {
            UserPermissionBo userPermissionBo=new UserPermissionBo();
            userPermissionBo.setPermissionCode(sysPermission.getPermissionCode());
            userPermissionBo.setPermissionName(sysPermission.getPermissionName());
            userPermissionList.add(userPermissionBo);
        }
        //这里是做了对象去重
        return userPermissionList.stream().filter(ListUtils.distinctByKey(o ->
                Stream.of(o.getPermissionCode(), o.getPermissionName()).toArray())).collect(Collectors.toList());
    }
}
