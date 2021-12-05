package wpy.personal.novel.novel.novel.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import wpy.personal.novel.pojo.entity.NovelTypeRel;
import wpy.personal.novel.novel.novel.mapper.NovelTypeRelMapper;
import wpy.personal.novel.novel.novel.service.NovelTypeRelService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import wpy.personal.novel.pojo.entity.SysUser;
import wpy.personal.novel.utils.ObjectUtils;
import wpy.personal.novel.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 小说类型关联表 服务实现类
 * </p>
 *
 * @author wangpanyin
 * @since 2021-09-07
 */
@Service
@Transactional
public class NovelTypeRelServiceImpl extends ServiceImpl<NovelTypeRelMapper, NovelTypeRel> implements NovelTypeRelService {

    @Override
    public void updateNovelType(String novelId, List<String> typeCodeList, SysUser sysUser) {
        if(!CollectionUtils.isEmpty(typeCodeList)){
            //删除原来的
            this.remove(new QueryWrapper<NovelTypeRel>().eq("novel_id",novelId));
            //添加小说类型
            List<NovelTypeRel> typeList=new ArrayList<>();
            for (String typeCode : typeCodeList) {
                NovelTypeRel novelTypeRel = ObjectUtils.newInstance(sysUser.getUserId(), NovelTypeRel.class);
                novelTypeRel.setNovelTypeRelId(StringUtils.getUuid32());
                novelTypeRel.setNovelId(novelId);
                novelTypeRel.setTypeCode(typeCode);
                typeList.add(novelTypeRel);
            }
            this.saveBatch(typeList);
        }
    }
}
