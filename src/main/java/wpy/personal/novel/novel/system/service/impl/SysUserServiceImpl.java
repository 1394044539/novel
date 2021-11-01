package wpy.personal.novel.novel.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wpy.personal.novel.base.constant.RedisConstant;
import wpy.personal.novel.base.enums.DictEnums;
import wpy.personal.novel.base.enums.ErrorCode;
import wpy.personal.novel.base.enums.SqlEnums;
import wpy.personal.novel.base.exception.BusinessException;
import wpy.personal.novel.config.jwt.JwtUtils;
import wpy.personal.novel.novel.system.mapper.SysUserMapper;
import wpy.personal.novel.novel.system.service.*;
import wpy.personal.novel.pojo.bo.UserInfoBo;
import wpy.personal.novel.pojo.bo.UserPermissionBo;
import wpy.personal.novel.pojo.bo.UserRoleBo;
import wpy.personal.novel.pojo.bo.ZhenZiResultBo;
import wpy.personal.novel.pojo.dto.SysUserDto;
import wpy.personal.novel.pojo.entity.SysRole;
import wpy.personal.novel.pojo.entity.SysUser;
import wpy.personal.novel.pojo.entity.SysUserRole;
import wpy.personal.novel.utils.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
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
@Transactional
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Value("${jwt.expireTime}")
    private Long expireTime;

    @Autowired
    private SysRoleService sysRoleService;
    @Autowired
    private SysUserRoleService sysUserRoleService;
    @Autowired
    private SysRolePermissionService sysRolePermissionService;
    @Autowired
    private SendMessageService sendMessageService;
    @Autowired
    private SysUserMapper sysUserMapper;


    @Override
    public UserInfoBo loginByAccount(SysUserDto sysUserDto) {
        //校验用户是否存在
        SysUser sysUser = this.getOne(new QueryWrapper<SysUser>().eq("account_name", sysUserDto.getAccountName()));
        if(sysUser==null){
            throw BusinessException.fail(ErrorCode.USER_PASSWORD_ERROR);
        }
        if(SqlEnums.USER_DISABLE.getCode().equals(sysUser.getUserStatus())){
            throw BusinessException.fail("用户被禁用");
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
        //判断账号或手机号或邮箱是否重复
        QueryWrapper<SysUser> qw=new QueryWrapper<>();
        qw.eq("account_name",sysUserDto.getAccountName());
        if(StringUtils.isNotEmpty(sysUserDto.getPhone())){
            qw.or();
            qw.eq("phone",sysUserDto.getPhone());
        }
        if(StringUtils.isNotEmpty(sysUserDto.getEmail())){
            qw.or();
            qw.eq("email",sysUserDto.getEmail());
        }
        int count = this.count(qw);
        if(count>0){
            throw BusinessException.fail(ErrorCode.USER_INFO_REPEAT);
        }
        SysUser insertUser = ObjectUtils.newInstance(sysUser.getUserId(), SysUser.class);
        ObjectUtils.copyPropertiesIgnoreNull(sysUserDto,insertUser);
        insertUser.setUserId(StringUtils.getUuid32());
        //默认只有100M空间
        insertUser.setFullMemory(new BigDecimal("100"));
        insertUser.setUseMemory(new BigDecimal("0"));
        //加密
        insertUser.setPassword(EncryptionUtils.md5Encryption(insertUser.getPassword(),insertUser.getUserId()));
        insertUser.setUserStatus(SqlEnums.USER_NORMAL.getCode());
        //赋予角色
        sysRoleService.userAddRole(insertUser.getUserId(),sysUserDto.getRoleCode(),sysUser.getUserId());
        this.save(insertUser);
    }

    @Override
    public UserInfoBo getUserInfo(SysUser sysUser) {
        UserInfoBo userInfoBo = new UserInfoBo();
        userInfoBo.setUserName(sysUser.getUserName());
        userInfoBo.setAccountName(sysUser.getAccountName());
        //1、通过userId去查询关联表拿到角色
        List<UserRoleBo> roleList = sysUserRoleService.getRoleListByUserId(sysUser.getUserId());
        userInfoBo.setRoleList(roleList);
        //2、通过roleCode去查询全部的权限信息
        List<String> roleCodeList = roleList.stream().map(UserRoleBo::getRoleCode).collect(Collectors.toList());
        List<UserPermissionBo> permissionList = sysRolePermissionService.getPermissionByRoleCodeList(roleCodeList);
        userInfoBo.setPermissionList(permissionList);
        return userInfoBo;
    }

    @Override
    public void logon() {
        //删除redis缓存
        String token = RequestUtils.getToken(null);
        RedisUtils.delete(RedisConstant.TOKEN+token);
    }

    @Override
    public SysUser updateUser(SysUserDto sysUserDto, SysUser sysUser) {
        //账号啥的肯定不能修改
        SysUser updateUser = new SysUser();
        BeanUtils.copyProperties(sysUserDto,updateUser);
        updateUser.setUpdateBy(sysUser.getUserId());
        updateUser.setUpdateTime(new Date());
        //权限修改没
        if(StringUtils.isNotEmpty(sysUserDto.getRoleCode())){
            //更新一下关联表
            SysRole sysRole = sysRoleService.getOne(new QueryWrapper<SysRole>().eq("role_code", sysUserDto.getRoleCode()));
            sysUserRoleService.update(new UpdateWrapper<SysUserRole>().eq("user_id",sysUserDto.getUserId()).set("role_id",sysRole.getRoleId()));
        }
        this.updateById(updateUser);
        return null;
    }

    @Override
    public UserInfoBo loginByPhone(SysUserDto sysUserDto) {
        //校验用户是否存在
        SysUser sysUser = this.checkVerifyCode(sysUserDto);
        //拿到用户信息
        UserInfoBo userInfo = this.getUserInfo(sysUser);
        //生成用户的token
        String token = JwtUtils.createToken(sysUser.getUserId(), sysUser.getAccountName(), sysUser.getPassword(), sysUser.getUserName());
        userInfo.setToken(token);
        //往redis存入一个用户
        RedisUtils.setObject(RedisConstant.TOKEN+token,userInfo,expireTime);
        RedisUtils.delete(RedisConstant.VCODE + sysUserDto.getPhone());
        return userInfo;
    }

    @Override
    public ZhenZiResultBo getVerifyCode(String phone) {
        if(RedisUtils.hasKey(RedisConstant.VCODE + phone)){
            long time = RedisUtils.getTime(RedisConstant.VCODE + phone);
            if(time>240){
                throw BusinessException.fail("请勿频繁发送验证码，还需等待"+time+"秒钟");
            }
        }
        //1、生成6位数字验证码，缓存时间300秒
        String verifyCode = RandomStringUtils.randomNumeric(6);
        RedisUtils.setObject(RedisConstant.VCODE+phone,verifyCode,300);
        //发送短信
        return sendMessageService.sendMessage(phone,verifyCode);
    }

    @Override
    public UserInfoBo checkPhone(SysUserDto sysUserDto) {
        SysUser sysUser = this.checkVerifyCode(sysUserDto);
        //只返回一个用户名信息
        UserInfoBo userInfoBo = new UserInfoBo();
        userInfoBo.setAccountName(sysUser.getAccountName());
        return userInfoBo;
    }

    @Override
    public void updatePassword(SysUserDto sysUserDto) {
        SysUser sysUser = this.getOne(new QueryWrapper<SysUser>().eq("account_name", sysUserDto.getAccountName()));
        if(StringUtils.isBlank(sysUser)){
            throw BusinessException.fail("用户不存在");
        }
        String newPassword = EncryptionUtils.md5Encryption(sysUserDto.getPassword(), sysUser.getUserId());
        sysUser.setPassword(newPassword);
        this.updateById(sysUser);
    }

    @Override
    public Page<SysUser> getUserList(SysUser sysUser, SysUserDto sysUserDto) {
        Page<SysUser> page = new Page<>(sysUserDto.getPage(), sysUserDto.getPageSize());
        List<SysUser> list = this.sysUserMapper.selectUserInfo(sysUserDto,page);
        return page.setRecords(list);
    }

    @Override
    public void disableUser(SysUser sysUser, List<String> ids) {
        UpdateWrapper<SysUser> uw = new UpdateWrapper<>();
        uw.set("update_time",new Date());
        uw.set("update_by",sysUser.getUserId());
        uw.set("user_status",SqlEnums.USER_DISABLE.getCode());
        uw.in("user_id",ids);
        this.update(uw);
    }

    /**
     * 校验验证码
     * @param sysUserDto
     * @return
     */
    private SysUser checkVerifyCode(SysUserDto sysUserDto) {
        //校验手机号是否存在
        SysUser sysUser = this.getOne(new QueryWrapper<SysUser>().eq("phone", sysUserDto.getPhone()));
        if(sysUser==null){
            throw BusinessException.fail(ErrorCode.USER_NOT_EXISTS);
        }
        if(SqlEnums.USER_DISABLE.getCode().equals(sysUser.getUserStatus())){
            throw BusinessException.fail("用户被禁用");
        }
        //校验手机号验证码是否正确
        if(StringUtils.isEmpty(sysUserDto.getVerifyCode())){
            throw BusinessException.fail("验证码不能为空");
        }
        Object object = RedisUtils.getObject(RedisConstant.VCODE + sysUserDto.getPhone());
        if(!sysUserDto.getVerifyCode().equals(object)){
            throw BusinessException.fail("验证码错误或已过期");
        }
        return sysUser;
    }
}
