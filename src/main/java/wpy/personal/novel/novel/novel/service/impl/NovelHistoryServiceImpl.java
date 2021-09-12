package wpy.personal.novel.novel.novel.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wpy.personal.novel.novel.novel.mapper.NovelHistoryMapper;
import wpy.personal.novel.novel.novel.service.NovelHistoryService;
import wpy.personal.novel.pojo.entity.NovelHistory;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wangpanyin
 * @since 2021-09-12
 */
@Service
@Transactional
public class NovelHistoryServiceImpl extends ServiceImpl<NovelHistoryMapper, NovelHistory> implements NovelHistoryService {

}
