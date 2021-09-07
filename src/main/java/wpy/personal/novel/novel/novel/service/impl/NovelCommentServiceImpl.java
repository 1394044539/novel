package wpy.personal.novel.novel.novel.service.impl;

import wpy.personal.novel.pojo.entity.NovelComment;
import wpy.personal.novel.novel.novel.mapper.NovelCommentMapper;
import wpy.personal.novel.novel.novel.service.NovelCommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 小说评论表 服务实现类
 * </p>
 *
 * @author wangpanyin
 * @since 2021-09-07
 */
@Service
public class NovelCommentServiceImpl extends ServiceImpl<NovelCommentMapper, NovelComment> implements NovelCommentService {

}
