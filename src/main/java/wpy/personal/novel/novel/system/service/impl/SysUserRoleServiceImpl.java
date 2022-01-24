package wpy.personal.novel.novel.system.service.impl;

import com.google.common.collect.Lists;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import wpy.personal.novel.base.enums.DictEnums;
import wpy.personal.novel.pojo.bo.UserRoleBo;
import wpy.personal.novel.pojo.entity.SysRole;
import wpy.personal.novel.pojo.entity.SysUser;
import wpy.personal.novel.pojo.entity.SysUserRole;
import wpy.personal.novel.novel.system.mapper.SysUserRoleMapper;
import wpy.personal.novel.novel.system.service.SysUserRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 用户角色关联表 服务实现类
 * </p>
 *
 * @author wangpanyin
 * @since 2021-09-07
 */
@Service
@Transactional
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRole> implements SysUserRoleService {

    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;

    @Override
    public List<UserRoleBo> getRoleListByUserId(String userId) {
        List<SysRole> roleList = sysUserRoleMapper.getRoleListByUserId(userId);
        List<UserRoleBo> userRoleList = Lists.newArrayList();
        for (SysRole sysRole : roleList) {
            UserRoleBo userRoleBo = new UserRoleBo();
            userRoleBo.setRoleCode(sysRole.getRoleCode());
            userRoleBo.setRoleName(sysRole.getRoleName());
            userRoleList.add(userRoleBo);
        }
        return userRoleList;
    }

    @Override
    public List<String> getRoleCodeListByUserId(String userId) {
        List<SysRole> roleList = sysUserRoleMapper.getRoleListByUserId(userId);
        if(CollectionUtils.isEmpty(roleList)){
            return new ArrayList<>();
        }
        return roleList.stream().map(SysRole::getRoleCode).collect(Collectors.toList());
    }

    @Override
    public List<SysUser> getUserListByRole(String roleId) {
        return this.sysUserRoleMapper.getUserListByRole(roleId);
    }

    @Override
    public boolean hasRole(String userId,DictEnums... dictEnums) {
        List<String> roleList = this.getRoleCodeListByUserId(userId);
        for (DictEnums dictEnum : dictEnums) {
            if(roleList.contains(dictEnum.getKey())){
                return true;
            }
        }
        return false;
    }
}
