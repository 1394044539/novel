package wpy.personal.novel.novel.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import wpy.personal.novel.base.constant.NumConstant;
import wpy.personal.novel.base.enums.ErrorCode;
import wpy.personal.novel.base.exception.BusinessException;
import wpy.personal.novel.novel.system.service.SysPermissionService;
import wpy.personal.novel.novel.system.service.SysRolePermissionService;
import wpy.personal.novel.novel.system.service.SysUserRoleService;
import wpy.personal.novel.pojo.bo.RoleInfoBo;
import wpy.personal.novel.pojo.bo.UserPermissionBo;
import wpy.personal.novel.pojo.dto.SysRoleDto;
import wpy.personal.novel.pojo.entity.SysRole;
import wpy.personal.novel.novel.system.mapper.SysRoleMapper;
import wpy.personal.novel.novel.system.service.SysRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import wpy.personal.novel.pojo.entity.SysRolePermission;
import wpy.personal.novel.pojo.entity.SysUser;
import wpy.personal.novel.pojo.entity.SysUserRole;
import wpy.personal.novel.utils.ObjectUtils;
import wpy.personal.novel.utils.StringUtils;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 角色表 服务实现类
 * </p>
 *
 * @author wangpanyin
 * @since 2021-09-07
 */
@Service
@Transactional
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

    @Autowired
    private SysRoleMapper sysRoleMapper;
    @Autowired
    private SysUserRoleService sysUserRoleService;
    @Autowired
    private SysRolePermissionService sysRolePermissionService;
    @Autowired
    private SysPermissionService sysPermissionService;

    @Override
    public Page<SysRole> getRoleList(SysRoleDto sysRoleDto, SysUser sysUser) {
        QueryWrapper<SysRole> qw =new QueryWrapper<>();
        return sysRoleMapper.selectPage(new Page<SysRole>(sysRoleDto.getPage(),sysRoleDto.getPageSize()),qw);
    }

    @Override
    public SysRole addRole(SysRoleDto sysRoleDto, SysUser sysUser) {
        //首先校验重复
        int roleCount = this.count(new QueryWrapper<SysRole>().eq("role_code", sysRoleDto.getRoleCode()));
        if(roleCount>0){
            throw BusinessException.fail(ErrorCode.ROLE_CODE_REPEAT);
        }

        SysRole sysRole = ObjectUtils.newInstance(sysUser.getUserId(), SysRole.class);
        ObjectUtils.copyPropertiesIgnoreNull(sysRoleDto,sysRole);
        sysRole.setRoleId(StringUtils.getUuid32());
        this.save(sysRole);
        return sysRole;
    }

    @Override
    public void updateRole(SysRoleDto sysRoleDto, SysUser sysUser) {
        //首先校验重复
        int roleCount = this.count(new QueryWrapper<SysRole>()
                .eq("role_code", sysRoleDto.getRoleCode())
                .ne("role_id",sysRoleDto.getRoleId()));
        if(roleCount>0){
            throw BusinessException.fail(ErrorCode.ROLE_CODE_REPEAT);
        }

        UpdateWrapper<SysRole> uw = new UpdateWrapper<>();
        uw.set("role_name",sysRoleDto.getRoleName());
        uw.set("role_desc",sysRoleDto.getRoleDesc());
        uw.set("update_time",new Date());
        uw.set("update_by",sysUser.getUserId());
        uw.eq("role_id",sysRoleDto.getRoleId());
        this.update(uw);
    }

    @Override
    public RoleInfoBo getRoleInfo(String roleId, SysUser sysUser) {
        RoleInfoBo roleInfoBo = new RoleInfoBo();
        //查询
        SysRole sysRole = this.getById(roleId);
        BeanUtils.copyProperties(sysRole,roleInfoBo);
        //查询出有多少用户正在使用该角色
        List<SysUser> userList = sysUserRoleService.getUserListByRole(sysRole.getRoleId());
        roleInfoBo.setUserList(userList);
        //查询角色的权限
        List<UserPermissionBo> permissionList = sysRolePermissionService.getPermissionByRoleCodeList(Lists.newArrayList(sysRole.getRoleCode()));
        roleInfoBo.setPermissionList(permissionList);
        return roleInfoBo;
    }

    @Override
    public void deleteRole(String roleId, SysUser sysUser) {
        //校验该角色是否无用户在使用
        Integer count = sysUserRoleService.getBaseMapper().selectCount(new QueryWrapper<SysUserRole>().eq("role_id", roleId));
        if(count > NumConstant.ZERO){
            throw BusinessException.fail(ErrorCode.ROLE_HAS_USER);
        }
        //先要查询出来拿到权限id去删除权限表
        List<SysRolePermission> permissionList = sysRolePermissionService.getBaseMapper().selectList(
                new QueryWrapper<SysRolePermission>().eq("role_id", roleId));
        if(!CollectionUtils.isEmpty(permissionList)){
            List<String> permissionIdList = permissionList.stream().map(SysRolePermission::getPermissionId).collect(Collectors.toList());
            List<String> rolePermissionIdList = permissionList.stream().map(SysRolePermission::getRolePermissionId).collect(Collectors.toList());
            //删除权限表
            sysPermissionService.removeByIds(permissionIdList);
            //删除关联表
            sysRolePermissionService.removeByIds(rolePermissionIdList);
        }
        //删除权限
        this.removeById(roleId);
    }

    @Override
    public void userAddRole(String userId, String roleCode, String operationUserId) {
        if(StringUtils.isEmpty(roleCode)){
            throw BusinessException.fail("角色编码不能为空");
        }
        SysRole sysRole = this.getOne(new QueryWrapper<SysRole>().eq("role_code", roleCode));

        SysUserRole sysUserRole = ObjectUtils.newInstance(operationUserId, SysUserRole.class);
        sysUserRole.setRoleId(sysRole.getRoleId());
        sysUserRole.setUserId(userId);
        sysUserRole.setUserRoleId(StringUtils.getUuid32());
        sysUserRoleService.save(sysUserRole);

    }

    @Override
    public List<SysRole> getAllRoleList(SysRoleDto sysRoleDto, SysUser sysUser) {
        QueryWrapper<SysRole> qw = new QueryWrapper<>();
        return this.sysRoleMapper.selectList(qw);
    }
}
