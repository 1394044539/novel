package wpy.personal.novel.novel.system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import wpy.personal.novel.pojo.dto.SysDictDto;
import wpy.personal.novel.pojo.entity.SysDict;
import com.baomidou.mybatisplus.extension.service.IService;
import wpy.personal.novel.pojo.entity.SysUser;

import java.util.List;

/**
 * <p>
 * 字典表 服务类
 * </p>
 *
 * @author wangpanyin
 * @since 2021-09-07
 */
public interface SysDictService extends IService<SysDict> {

    /**
     * 添加字典表
     * @param sysDictDto
     * @param sysUser
     * @return
     */
    SysDict addDict(SysDictDto sysDictDto, SysUser sysUser);

    /**
     * 查询字典列表
     * @param sysDictDto
     * @param sysUser
     * @return
     */
    Page<SysDict> getDictList(SysDictDto sysDictDto, SysUser sysUser);

    /**
     * 获得字典数据详情
     * @param dictId
     * @param sysUser
     * @return
     */
    SysDictDto getDictInfo(String dictId, SysUser sysUser);

    /**
     * 修改字典
     * @param sysDictDto
     * @param sysUser
     * @return
     */
    SysDict updateDict(SysDictDto sysDictDto, SysUser sysUser);

    /**
     * 删除字典
     * @param ids
     * @param sysUser
     */
    void deleteDict(List<String> ids, SysUser sysUser);
}
