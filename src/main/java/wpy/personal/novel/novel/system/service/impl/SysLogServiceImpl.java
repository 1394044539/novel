package wpy.personal.novel.novel.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import wpy.personal.novel.pojo.dto.SysLogDto;
import wpy.personal.novel.pojo.entity.SysLog;
import wpy.personal.novel.novel.system.mapper.SysLogMapper;
import wpy.personal.novel.novel.system.service.SysLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import wpy.personal.novel.pojo.entity.SysUser;

/**
 * <p>
 * 日志表 服务实现类
 * </p>
 *
 * @author wangpanyin
 * @since 2021-09-07
 */
@Service
@Transactional
public class SysLogServiceImpl extends ServiceImpl<SysLogMapper, SysLog> implements SysLogService {

    @Autowired
    private SysLogMapper sysLogMapper;

    @Override
    public Page<SysLog> getLogList(SysLogDto sysLogDto, SysUser sysUser) {
        QueryWrapper<SysLog> qw = new QueryWrapper<>();

        return this.sysLogMapper.selectPage(new Page<>(sysLogDto.getPage(),sysLogDto.getPageSize()),qw);
    }
}
