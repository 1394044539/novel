package wpy.personal.novel.novel.system.service.impl;

import org.springframework.transaction.annotation.Transactional;
import wpy.personal.novel.pojo.entity.SysNotice;
import wpy.personal.novel.novel.system.mapper.SysNoticeMapper;
import wpy.personal.novel.novel.system.service.SysNoticeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

}
