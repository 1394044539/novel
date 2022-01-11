package wpy.personal.novel.novel.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import wpy.personal.novel.base.enums.DictEnums;
import wpy.personal.novel.novel.system.service.SysDictService;
import wpy.personal.novel.pojo.dto.SysNoticeDto;
import wpy.personal.novel.pojo.entity.SysDict;
import wpy.personal.novel.pojo.entity.SysNotice;
import wpy.personal.novel.novel.system.mapper.SysNoticeMapper;
import wpy.personal.novel.novel.system.service.SysNoticeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import wpy.personal.novel.pojo.entity.SysUser;

/**
 * <p>
 * 系统公告表 服务实现类
 * </p>
 *
 * @author wangpanyin
 * @since 2021-09-07
 */
@Service
@Transactional
public class SysNoticeServiceImpl extends ServiceImpl<SysNoticeMapper, SysNotice> implements SysNoticeService {

    @Autowired
    private SysNoticeMapper sysNoticeMapper;
    @Autowired
    private SysDictService sysDictService;

    @Override
    public Page<SysNotice> list(SysNoticeDto dto, SysUser sysUser) {
        QueryWrapper<SysNotice> qw = new QueryWrapper<SysNotice>();
        return this.sysNoticeMapper.selectPage(new Page<SysNotice>(dto.getPage(),dto.getPageSize()),qw);
    }

    @Override
    public SysNotice openNotice() {
        //判断是否有需要打开的公告
        SysDict one = sysDictService.getOne(new QueryWrapper<SysDict>()
                .eq("dict_code", DictEnums.OPEN_NOTICE.getKey()));
        if(one == null || DictEnums.CLOSE_NOTICE.getKey().equals(one.getDictValue())){
            return null;
        }
        //取出需要打开的公告
        return this.getOne(new QueryWrapper<SysNotice>().eq("notice_id",one.getDictValue()));
    }
}
