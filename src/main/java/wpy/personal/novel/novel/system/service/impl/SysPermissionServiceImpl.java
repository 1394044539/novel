package wpy.personal.novel.novel.system.service.impl;

import org.springframework.transaction.annotation.Transactional;
import wpy.personal.novel.pojo.entity.SysPermission;
import wpy.personal.novel.novel.system.mapper.SysPermissionMapper;
import wpy.personal.novel.novel.system.service.SysPermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 权限表 服务实现类
 * </p>
 *
 * @author wangpanyin
 * @since 2021-09-07
 */
@Service
@Transactional
public class SysPermissionServiceImpl extends ServiceImpl<SysPermissionMapper, SysPermission> implements SysPermissionService {

}
