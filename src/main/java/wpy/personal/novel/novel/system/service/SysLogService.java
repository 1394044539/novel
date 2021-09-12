package wpy.personal.novel.novel.system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import wpy.personal.novel.pojo.dto.SysLogDto;
import wpy.personal.novel.pojo.entity.SysLog;
import com.baomidou.mybatisplus.extension.service.IService;
import wpy.personal.novel.pojo.entity.SysUser;

/**
 * <p>
 * 日志表 服务类
 * </p>
 *
 * @author wangpanyin
 * @since 2021-09-07
 */
public interface SysLogService extends IService<SysLog> {

    /**
     * 获取日志列表
     * @param sysLogDto
     * @param sysUser
     * @return
     */
    Page<SysLog> getLogList(SysLogDto sysLogDto, SysUser sysUser);
}
