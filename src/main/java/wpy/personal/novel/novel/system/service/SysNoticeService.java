package wpy.personal.novel.novel.system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import wpy.personal.novel.pojo.dto.SysNoticeDto;
import wpy.personal.novel.pojo.entity.SysNotice;
import com.baomidou.mybatisplus.extension.service.IService;
import wpy.personal.novel.pojo.entity.SysUser;

/**
 * <p>
 * 系统公告表 服务类
 * </p>
 *
 * @author wangpanyin
 * @since 2021-09-07
 */
public interface SysNoticeService extends IService<SysNotice> {

    /**
     * 查询公告列表
     * @param dto
     * @param sysUser
     * @return
     */
    Page<SysNotice> list(SysNoticeDto dto, SysUser sysUser);

    /**
     * 获取需要打开的公告信息
     * @return
     */
    SysNotice openNotice();
}
