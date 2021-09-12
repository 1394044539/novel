package wpy.personal.novel.novel.novel.service.impl;

import org.springframework.transaction.annotation.Transactional;
import wpy.personal.novel.pojo.entity.NovelData;
import wpy.personal.novel.novel.novel.mapper.NovelDataMapper;
import wpy.personal.novel.novel.novel.service.NovelDataService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 小说数据表 服务实现类
 * </p>
 *
 * @author wangpanyin
 * @since 2021-09-07
 */
@Service
@Transactional
public class NovelDataServiceImpl extends ServiceImpl<NovelDataMapper, NovelData> implements NovelDataService {

}
