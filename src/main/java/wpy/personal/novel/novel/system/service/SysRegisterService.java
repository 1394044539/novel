package wpy.personal.novel.novel.system.service;

import wpy.personal.novel.pojo.dto.SysRegisterDto;
import wpy.personal.novel.pojo.entity.SysRegister;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wangpanyin
 * @since 2021-09-22
 */
public interface SysRegisterService extends IService<SysRegister> {

    /**
     * 申请注册权限
     * @param registerDto
     */
    void applyRegister(SysRegisterDto registerDto);
}
