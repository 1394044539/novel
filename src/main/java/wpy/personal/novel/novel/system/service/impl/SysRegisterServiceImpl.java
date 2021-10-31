package wpy.personal.novel.novel.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import wpy.personal.novel.base.enums.BusinessEnums;
import wpy.personal.novel.base.enums.SqlEnums;
import wpy.personal.novel.base.exception.BusinessException;
import wpy.personal.novel.pojo.dto.SysRegisterDto;
import wpy.personal.novel.pojo.entity.SysRegister;
import wpy.personal.novel.novel.system.mapper.SysRegisterMapper;
import wpy.personal.novel.novel.system.service.SysRegisterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import wpy.personal.novel.pojo.entity.SysUser;
import wpy.personal.novel.utils.ObjectUtils;
import wpy.personal.novel.utils.StringUtils;
import wpy.personal.novel.utils.dateUtils.DateUtils;

import java.util.Date;
import java.util.List;

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

    @Autowired
    private SysRegisterMapper sysRegisterMapper;

    @Override
    public void applyRegister(SysRegisterDto registerDto) {
        SysRegister register = this.getOne(new QueryWrapper<SysRegister>().eq("phone", registerDto.getPhone()));
        if(register!=null){
            if(SqlEnums.WAIT_APPLY.getCode().equals(register.getRegisterStatus())){
                throw BusinessException.fail("该手机号已经提交过申请，请等待");
            }else if(SqlEnums.ALREADY_SEND.getCode().equals(register.getRegisterStatus())){
                if(DateUtils.beforeDateNow(register.getExpireTime(),false)){
                    // 若失效，重发
                    register.setRegisterStatus(SqlEnums.WAIT_APPLY.getCode());
                    register.setRegisterMessage(registerDto.getRegisterMessage());
                    register.setUpdateTime(new Date());
                    this.updateById(register);
                }else {
                    throw BusinessException.fail("已通过审核，请查看短信，或三日失效后重发");
                }
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

    @Override
    public Page<SysRegister> getApplyRegisterList(SysUser sysUser, SysRegisterDto registerDto) {
        Page<SysRegister> page = new Page<>(registerDto.getPage(), registerDto.getPageSize());
        List<SysRegister> applyRegisterList = this.sysRegisterMapper.getApplyRegisterList(registerDto, page);
        for (SysRegister register : applyRegisterList) {
            // 如果失效时间小于当前时间，则失效了
            if(SqlEnums.ALREADY_SEND.getCode().equals(register.getRegisterStatus())
                    && DateUtils.beforeDateNow(register.getExpireTime(),false)){
                register.setRegisterStatus(SqlEnums.ALREADY_CANCEL.getCode());
            }
        }
        return page.setRecords(applyRegisterList);
    }

    @Override
    public SysRegister createRegInfo(SysUser sysUser, SysRegisterDto registerDto) {
        SysRegister sysRegister = this.getById(registerDto.getRegisterId());
        sysRegister.setUpdateTime(new Date());
        sysRegister.setUpdateBy(sysUser.getUserId());
        sysRegister.setRegisterStatus(SqlEnums.ALREADY_SEND.getCode());
        sysRegister.setExpireTime(DateUtils.getDateAfter(new Date(),3));
        sysRegister.setRemark("您的注册申请已通过，请在三日内点击以下链接进行短信注册：http://192.168.1.12:8080/novel/reg?registerId="+sysRegister.getRegisterId());
        this.updateById(sysRegister);
        if(BusinessEnums.SEND_MESSAGE.getCode().equals(registerDto.getSendMessage())){
            //todo 发短信
        }
        return sysRegister;
    }
}
