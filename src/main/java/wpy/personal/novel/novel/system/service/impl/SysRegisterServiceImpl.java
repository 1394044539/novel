package wpy.personal.novel.novel.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import wpy.personal.novel.base.enums.SqlEnums;
import wpy.personal.novel.base.exception.BusinessException;
import wpy.personal.novel.pojo.dto.SysRegisterDto;
import wpy.personal.novel.pojo.entity.SysRegister;
import wpy.personal.novel.novel.system.mapper.SysRegisterMapper;
import wpy.personal.novel.novel.system.service.SysRegisterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import wpy.personal.novel.utils.ObjectUtils;
import wpy.personal.novel.utils.StringUtils;

import java.util.Date;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wangpanyin
 * @since 2021-09-22
 */
@Service
public class SysRegisterServiceImpl extends ServiceImpl<SysRegisterMapper, SysRegister> implements SysRegisterService {

    @Override
    public void applyRegister(SysRegisterDto registerDto) {
        SysRegister register = this.getOne(new QueryWrapper<SysRegister>().eq("phone", registerDto.getPhone()));
        if(register!=null){
            if(SqlEnums.WAIT_APPLY.getCode().equals(register.getRegisterStatus())){
                throw BusinessException.fail("该手机号已经提交过申请，请等待");
            }else if(SqlEnums.ALREADY_SEND.getCode().equals(register.getRegisterStatus())){
                //todo 待编写
            }else if(SqlEnums.ALREADY_REGISTER.getCode().equals(register.getRegisterStatus())){
                throw BusinessException.fail("该手机号已注册，无需申请");
            }else if(SqlEnums.ALREADY_CANCEL.getCode().equals(register.getRegisterStatus())){
                //这里更新
                register.setRegisterStatus(SqlEnums.WAIT_APPLY.getCode());
                register.setRegisterMessage(registerDto.getRegisterMessage());
                register.setUpdateTime(new Date());
                this.updateById(register);
            }
        }else {
            SysRegister sysRegister = ObjectUtils.newInstance("System", SysRegister.class);
            sysRegister.setRegisterId(StringUtils.getUuid32());
            sysRegister.setPhone(registerDto.getPhone());
            sysRegister.setRegisterMessage(registerDto.getRegisterMessage());
            sysRegister.setRegisterStatus(SqlEnums.WAIT_APPLY.getCode());
            this.save(sysRegister);
        }
    }
}
