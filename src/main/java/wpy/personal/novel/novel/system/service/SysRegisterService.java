package wpy.personal.novel.novel.system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import wpy.personal.novel.pojo.dto.SysRegisterDto;
import wpy.personal.novel.pojo.entity.SysRegister;
import com.baomidou.mybatisplus.extension.service.IService;
import wpy.personal.novel.pojo.entity.SysUser;

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

    /**
     * 获取申请列表
     * @param sysUser
     * @param registerDto
     * @return
     */
    Page<SysRegister> getApplyRegisterList(SysUser sysUser, SysRegisterDto registerDto);

    /**
     * 创建注册信息
     * @param sysUser
     * @param registerDto
     * @return
     */
    SysRegister createRegInfo(SysUser sysUser, SysRegisterDto registerDto);
}
