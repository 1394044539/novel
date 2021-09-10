package wpy.personal.novel.novel.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import wpy.personal.novel.base.constant.RedisConstant;
import wpy.personal.novel.base.enums.DictEnums;
import wpy.personal.novel.base.enums.ErrorCode;
import wpy.personal.novel.base.exception.BusinessException;
import wpy.personal.novel.config.jwt.JwtUtils;
import wpy.personal.novel.novel.system.service.SysRolePermissionService;
import wpy.personal.novel.novel.system.service.SysRoleService;
import wpy.personal.novel.novel.system.service.SysUserRoleService;
import wpy.personal.novel.pojo.bo.UserInfoBo;
import wpy.personal.novel.pojo.bo.UserPermissionBo;
import wpy.personal.novel.pojo.bo.UserRoleBo;
import wpy.personal.novel.pojo.dto.SysUserDto;
import wpy.personal.novel.pojo.entity.SysRole;
import wpy.personal.novel.pojo.entity.SysUser;
import wpy.personal.novel.novel.system.mapper.SysUserMapper;
import wpy.personal.novel.novel.system.service.SysUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import wpy.personal.novel.utils.EncryptionUtils;
import wpy.personal.novel.utils.RedisUtils;
import wpy.personal.novel.utils.StringUtils;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author wangpanyin
 * @since 2021-09-07
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Value("${jwt.expireTime}")
    private static long expireTime;

    @Autowired
    private SysRoleService sysRoleService;
    @Autowired
    private SysUserRoleService sysUserRoleService;
    @Autowired
    private SysRolePermissionService sysRolePermissionService;

    @Override
    public UserInfoBo loginByAccount(SysUserDto sysUserDto) {
        //校验用户是否存在
        SysUser sysUser = this.getOne(new QueryWrapper<SysUser>().eq("account_name", sysUserDto.getAccountName()));
        if(sysUser==null){
            throw BusinessException.fail(ErrorCode.USER_PASSWORD_ERROR);
        }
        //校验用户密码是否正确
        String encryptionPassword = EncryptionUtils.md5Encryption(sysUserDto.getPassword(), sysUser.getUserId());
        if(!encryptionPassword.equals(sysUser.getPassword())){
            throw BusinessException.fail(ErrorCode.USER_PASSWORD_ERROR);
        }
        //拿到用户信息
        UserInfoBo userInfo = this.getUserInfo(sysUser);
        //生成用户的token
        String token = JwtUtils.createToken(sysUser.getUserId(), sysUser.getAccountName(), encryptionPassword, sysUser.getUserName());
        userInfo.setToken(token);
        //往redis存入一个用户
        RedisUtils.setObject(RedisConstant.TOKEN+token,userInfo,expireTime);
        return userInfo;
    }

    @Override
    public void addUser(SysUserDto sysUserDto, SysUser sysUser) {
        //1、判断权限是否正确
        List<String> roleList = sysUserRoleService.getRoleCodeListByUserId(sysUser.getUserId());
        if(!roleList.contains(DictEnums.SUPER_ADMIN.getKey())){
            throw BusinessException.fail(ErrorCode.USER_NOT_AUTH);
        }
        //2、添加用户
        SysUser insertUser = new SysUser();
        BeanUtils.copyProperties(sysUserDto,insertUser);
        insertUser.setUserId(StringUtils.getUuid());
        insertUser.setCreateBy(sysUser.getUserId());
        insertUser.setCreateTime(new Date());
        insertUser.setUpdateBy(sysUser.getUserId());
        insertUser.setUpdateTime(new Date());
        insertUser.setPassword(EncryptionUtils.md5Encryption(insertUser.getPassword(),insertUser.getUserId()));
        this.save(insertUser);
    }

    @Override
    public UserInfoBo getUserInfo(SysUser sysUser) {
        UserInfoBo userInfoBo = new UserInfoBo();
        //1、通过userId去查询关联表拿到角色
        List<UserRoleBo> roleList = sysUserRoleService.getRoleListByUserId(sysUser.getUserId());
        userInfoBo.setRoleList(roleList);
        //2、通过roleCode去查询全部的权限信息
        List<String> roleCodeList = roleList.stream().map(UserRoleBo::getRoleCode).collect(Collectors.toList());
        List<UserPermissionBo> permissionList = sysRolePermissionService.getPermissionByRoleCodeList(roleCodeList);
        userInfoBo.setPermissionList(permissionList);
        return userInfoBo;
    }
}
