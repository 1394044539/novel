package wpy.personal.novel.novel.system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import wpy.personal.novel.pojo.dto.FeedbackDto;
import wpy.personal.novel.pojo.entity.Feedback;
import wpy.personal.novel.pojo.entity.SysUser;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wangpanyin
 * @since 2022-01-07
 */
public interface FeedbackService extends IService<Feedback> {

    /**
     * 提交反馈
     * @param dto
     * @param sysUser
     */
    void insert(FeedbackDto dto, SysUser sysUser);

    /**
     * 修改反馈
     * @param dto
     * @param sysUser
     */
    void update(FeedbackDto dto, SysUser sysUser);

    /**
     * 查询列表
     * @param dto
     * @param sysUser
     * @return
     */
    Page<Feedback> list(FeedbackDto dto, SysUser sysUser);
}
