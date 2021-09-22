package wpy.personal.novel.novel.system.service.impl;

import wpy.personal.novel.base.enums.SqlEnums;
import wpy.personal.novel.pojo.dto.SysRegisterDto;
import wpy.personal.novel.pojo.entity.SysRegister;
import wpy.personal.novel.novel.system.mapper.SysRegisterMapper;
import wpy.personal.novel.novel.system.service.SysRegisterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import wpy.personal.novel.utils.ObjectUtils;
import wpy.personal.novel.utils.StringUtils;

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
        SysRegister sysRegister = ObjectUtils.newInstance("System", SysRegister.class);
        sysRegister.setRegisterId(StringUtils.getUuid32());
        sysRegister.setPhone(registerDto.getPhone());
        sysRegister.setRegisterMessage(registerDto.getRegisterMessage());
        sysRegister.setRegisterStatus(SqlEnums.WAIT_APPLY.getCode());
        this.save(sysRegister);
    }
}
