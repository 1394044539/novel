package wpy.personal.novel.novel.novel.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
}
