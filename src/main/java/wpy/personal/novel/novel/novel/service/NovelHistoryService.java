package wpy.personal.novel.novel.novel.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import wpy.personal.novel.pojo.bo.NovelHistoryBo;
import wpy.personal.novel.pojo.dto.HistoryDto;
import wpy.personal.novel.pojo.entity.NovelHistory;
import wpy.personal.novel.pojo.entity.SysUser;
import wpy.personal.novel.pojo.vo.HistoryListVo;
import wpy.personal.novel.utils.pageUtils.RequestPageUtils;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wangpanyin
 * @since 2021-09-12
 */
public interface NovelHistoryService extends IService<NovelHistory> {

    /**
     * 获取历史或书签列表
     * @param dto
     * @param sysUser
     * @return
     */
    Page<NovelHistoryBo> getHistoryList(RequestPageUtils<HistoryListVo> dto, SysUser sysUser);

    /**
     * 保存历史记录
     * @param historyDto
     * @param sysUser
     * @param ip
     * @return
     */
    NovelHistory saveHistory(HistoryDto historyDto, SysUser sysUser, String ip);

    /**
     * 获取历史记录
     * @param historyId
     * @param sysUser
     * @return
     */
    NovelHistory getHistory(String historyId, SysUser sysUser);
}
