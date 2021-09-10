package wpy.personal.novel.novel.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import wpy.personal.novel.base.enums.ErrorCode;
import wpy.personal.novel.base.exception.BusinessException;
import wpy.personal.novel.pojo.dto.SysUserDto;
import wpy.personal.novel.pojo.entity.SysUser;
import wpy.personal.novel.novel.system.mapper.SysUserMapper;
import wpy.personal.novel.novel.system.service.SysUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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


    @Override
    public SysUser loginByAccount(SysUserDto sysUserDto) {
        SysUser sysUser = this.getOne(new QueryWrapper<SysUser>().eq("account_name", sysUserDto.getAccountName()));
        if(sysUser==null){
            throw BusinessException.fail(ErrorCode.USER_PASSWORD_ERROR);
        }

        return new SysUser();
    }
}
