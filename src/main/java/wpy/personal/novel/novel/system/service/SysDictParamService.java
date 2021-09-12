package wpy.personal.novel.novel.system.service;

import wpy.personal.novel.pojo.entity.SysDictParam;
import com.baomidou.mybatisplus.extension.service.IService;
import wpy.personal.novel.pojo.entity.SysUser;

import java.util.List;

/**
 * <p>
 * 字段子类表 服务类
 * </p>
 *
 * @author wangpanyin
 * @since 2021-09-07
 */
public interface SysDictParamService extends IService<SysDictParam> {

    /**
     * 插入子项字典表
     * @param paramList
     * @param dictId
     * @param sysUser
     */
    void addDictParam(List<SysDictParam> paramList, String dictId, SysUser sysUser);
}
