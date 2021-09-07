package wpy.personal.novel.novel.novel.service.impl;

import wpy.personal.novel.pojo.entity.UserCollection;
import wpy.personal.novel.novel.novel.mapper.UserCollectionMapper;
import wpy.personal.novel.novel.novel.service.UserCollectionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户收藏表 服务实现类
 * </p>
 *
 * @author wangpanyin
 * @since 2021-09-07
 */
@Service
public class UserCollectionServiceImpl extends ServiceImpl<UserCollectionMapper, UserCollection> implements UserCollectionService {

}
