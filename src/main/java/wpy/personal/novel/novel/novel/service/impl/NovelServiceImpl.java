package wpy.personal.novel.novel.novel.service.impl;

import wpy.personal.novel.pojo.entity.Novel;
import wpy.personal.novel.novel.novel.mapper.NovelMapper;
import wpy.personal.novel.novel.novel.service.NovelService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 小说表 服务实现类
 * </p>
 *
 * @author wangpanyin
 * @since 2021-09-07
 */
@Service
public class NovelServiceImpl extends ServiceImpl<NovelMapper, Novel> implements NovelService {

}
