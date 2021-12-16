package wpy.personal.novel.novel.novel.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import wpy.personal.novel.base.enums.SqlEnums;
import wpy.personal.novel.novel.novel.mapper.NovelMapper;
import wpy.personal.novel.novel.novel.mapper.NovelVolumeMapper;
import wpy.personal.novel.novel.novel.mapper.UserCollectionMapper;
import wpy.personal.novel.novel.novel.service.UserCollectionService;
import wpy.personal.novel.pojo.bo.CollectionBo;
import wpy.personal.novel.pojo.dto.UserCollectionDto;
import wpy.personal.novel.pojo.entity.SysUser;
import wpy.personal.novel.pojo.entity.UserCollection;
import wpy.personal.novel.utils.ObjectUtils;

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
            qw.and(entity->entity.eq("volume_id","").or().isNull("volume_id"));
        }else if(SqlEnums.COLLECTION_CATALOG.getCode().equals(type)){
            qw.eq("parent_id",id);
        }
        return this.getOne(qw);
    }

    @Override
    public void deleteCollection(String collectionId, String collectionType) {
        if(!SqlEnums.COLLECTION_CATALOG.getCode().equals(collectionType)){
            this.removeById(collectionId);
        }else {
            recursionRemove(collectionId);
        }
    }

    /**
     * 递归删除
     * @param collectionId
     */
    private void recursionRemove(String collectionId) {
        //子目录
        List<UserCollection> list = userCollectionMapper.selectList(new QueryWrapper<UserCollection>().eq("parent_id", collectionId));
        if(!CollectionUtils.isEmpty(list)){
            //去递归
            for (UserCollection userCollection : list) {
                recursionRemove(userCollection.getCollectionId());
            }
        }
        this.removeById(collectionId);
    }

    @Override
    public List<CollectionBo> getCollectionList(UserCollectionDto dto, SysUser sysUser) {
        //todo 查询分卷历史记录
        //todo 查询小说看到哪一张
        List<CollectionBo> newList = this.userCollectionMapper.selectCollections(dto,sysUser);
        for (CollectionBo collectionBo : newList) {
            if(SqlEnums.COLLECTION_VOLUME.getCode().equals(collectionBo.getCollectionType())){
                collectionBo.setCatalogName(collectionBo.getVolumeName());
                collectionBo.setImgPath(collectionBo.getVolumeImg());
            }else if(SqlEnums.COLLECTION_NOVEL.getCode().equals(collectionBo.getCollectionType())){
                collectionBo.setCatalogName(collectionBo.getNovelName());
                collectionBo.setImgPath(collectionBo.getNovelImg());
            }
        }
        return newList;
    }

    @Override
    public void updateCollection(UserCollectionDto userCollectionDto, SysUser sysUser) {
        UserCollection userCollection = new UserCollection();
        BeanUtils.copyProperties(userCollectionDto,userCollection);
        this.updateById(userCollection);
    }
}
