package wpy.personal.novel.novel.novel.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import wpy.personal.novel.pojo.bo.NovelBo;
import wpy.personal.novel.pojo.dto.NovelDto;
import wpy.personal.novel.pojo.entity.Novel;
import com.baomidou.mybatisplus.extension.service.IService;
import wpy.personal.novel.pojo.entity.SysUser;

import java.util.List;

/**
 * <p>
 * 小说表 服务类
 * </p>
 *
 * @author wangpanyin
 * @since 2021-09-07
 */
public interface NovelService extends IService<Novel> {

    /**
     * 根据条件查询小说
     * @param novelData
     * @param sysUser
     * @return
     */
    Page<Novel> getNovelList(NovelDto novelData, SysUser sysUser);

    /**
     * 插入小说
     * @param novelDto
     * @param sysUser
     * @return
     */
    Novel addNovel(NovelDto novelDto, SysUser sysUser);

    /**
     * 根据id获取小说详情
     * @param novelId
     * @param sysUser
     * @return
     */
    NovelBo getNovelInfo(String novelId, SysUser sysUser);

    /**
     * 修改小说信息
     * @param novelDto
     * @param sysUser
     * @return
     */
    Novel updateNovel(NovelDto novelDto, SysUser sysUser);

    /**
     * 删除小说
     * @param idList
     * @param sysUser
     */
    void deleteNovel(List<String> idList, SysUser sysUser);
}
