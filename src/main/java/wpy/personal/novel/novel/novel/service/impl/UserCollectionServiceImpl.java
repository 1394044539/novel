package wpy.personal.novel.novel.novel.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import wpy.personal.novel.base.enums.SqlEnums;
import wpy.personal.novel.novel.novel.mapper.NovelMapper;
import wpy.personal.novel.novel.novel.mapper.NovelVolumeMapper;
import wpy.personal.novel.novel.novel.mapper.UserCollectionMapper;
import wpy.personal.novel.novel.novel.service.UserCollectionService;
import wpy.personal.novel.pojo.dto.UserCollectionDto;
import wpy.personal.novel.pojo.entity.Novel;
import wpy.personal.novel.pojo.entity.NovelVolume;
import wpy.personal.novel.pojo.entity.SysUser;
import wpy.personal.novel.pojo.entity.UserCollection;
import wpy.personal.novel.utils.ObjectUtils;
import wpy.personal.novel.utils.StringUtils;

import java.util.List;

/**
 * <p>
 * 用户收藏表 服务实现类
 * </p>
 *
 * @author wangpanyin
 * @since 2021-09-07
 */
@Service
@Transactional
public class UserCollectionServiceImpl extends ServiceImpl<UserCollectionMapper, UserCollection> implements UserCollectionService {

    @Autowired
    private UserCollectionMapper userCollectionMapper;
    @Autowired
    private NovelMapper novelMapper;
    @Autowired
    private NovelVolumeMapper novelVolumeMapper;

    @Override
    public UserCollection addCollection(UserCollectionDto userCollectionDto, SysUser sysUser) {
        UserCollection userCollection = ObjectUtils.newInstance(sysUser.getUserId(), UserCollection.class);
        ObjectUtils.copyPropertiesIgnoreNull(userCollectionDto,userCollection);
        this.save(userCollection);
        return userCollection;
    }

    @Override
    public UserCollection getCollection(String id, String type, SysUser sysUser) {
        QueryWrapper<UserCollection> qw=new QueryWrapper<>();
        qw.eq("create_by",sysUser.getUserId());
        if(SqlEnums.COLLECTION_VOLUME.getCode().equals(type)){
            qw.eq("volume_id",id);
        }else if(SqlEnums.COLLECTION_NOVEL.getCode().equals(type)){
            qw.eq("novel_id",id);
        }else if(SqlEnums.COLLECTION_CATALOG.getCode().equals(type)){
            qw.eq("parent_id",id);
        }
        return this.getOne(qw);
    }

    @Override
    public void deleteCollection(String collectionId) {
        this.removeById(collectionId);
    }

    @Override
    public List<UserCollection> getCollectionList(UserCollectionDto dto, SysUser sysUser) {
        QueryWrapper<UserCollection> qw = new QueryWrapper<>();
        qw.eq("create_by",sysUser.getUserId());
        if(!CollectionUtils.isEmpty(dto.getTypeList())){
            qw.in("collection_type",dto.getTypeList());
        }
        if(StringUtils.isNotEmpty(dto.getParentId())){
            qw.eq("parent_id",dto.getParentId());
        }else {
            qw.and(wrapper->wrapper.isNull("parent_id").or().eq("parent_id",""));
        }
        List<UserCollection> list = this.userCollectionMapper.selectList(qw);
        List<UserCollection> newList = Lists.newArrayList();
        for (UserCollection userCollection : list) {
            // 需要改成连表查询
            if(SqlEnums.COLLECTION_VOLUME.getCode().equals(userCollection.getCollectionType())){
                NovelVolume novelVolume = novelVolumeMapper.selectById(userCollection.getVolumeId());
                userCollection.setCatalogName(novelVolume.getVolumeName());
                userCollection.setImgPath(novelVolume.getVolumeImg());
                //todo 查询分卷历史记录
            }else if(SqlEnums.COLLECTION_NOVEL.getCode().equals(userCollection.getCollectionType())){
                Novel novel = novelMapper.selectById(userCollection.getNovelId());
                userCollection.setCatalogName(novel.getNovelName());
                userCollection.setImgPath(novel.getNovelImg());
                //todo 查询小说看到哪一张
            }
            if(StringUtils.isEmpty(dto.getCatalogName()) || userCollection.getCatalogName().contains(dto.getCatalogName())){
                newList.add(userCollection);
            }
        }
        return newList;
    }
}
