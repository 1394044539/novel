package wpy.personal.novel.novel.novel.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import wpy.personal.novel.base.enums.BusinessEnums;
import wpy.personal.novel.base.enums.SqlEnums;
import wpy.personal.novel.base.exception.BusinessException;
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
import java.util.Map;
import java.util.stream.Collectors;

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

    @Override
    public void copyOrMove(UserCollectionDto userCollectionDto, SysUser sysUser) {
        if(BusinessEnums.COPY.getCode().equals(userCollectionDto.getOptType())){
            //复制
            this.copy(userCollectionDto,sysUser);
        }else if(BusinessEnums.MOVE.getCode().equals(userCollectionDto.getOptType())){
            //移动
            this.move(userCollectionDto,sysUser);
        }
    }

    /**
     * 复制
     * @param userCollectionDto
     * @param sysUser
     */
    private void copy(UserCollectionDto userCollectionDto, SysUser sysUser) {

    }

    /**
     * 移动
     * @param userCollectionDto
     * @param sysUser
     */
    private void move(UserCollectionDto userCollectionDto, SysUser sysUser) {
        //1、检查是否是自己的子目录
//        UserCollectionDto allCollection=new UserCollectionDto();
//        allCollection.setTypeList(Lists.newArrayList("2"));
//        List<CollectionBo> collections = this.userCollectionMapper.selectCollections(allCollection, sysUser);
//        Map<String, List<CollectionBo>> map = collections.stream().collect(Collectors.groupingBy(CollectionBo::getCollectionId));
//        boolean childCatalog = checkChildCatalog(map, userCollectionDto.getCollectionId(), userCollectionDto.getParentId());
//        if(childCatalog){
//            throw BusinessException.fail("不允许移动到自己的子目录下");
//        }
        //2、修改目录的父id即可
        UpdateWrapper<UserCollection> uw=new UpdateWrapper<>();
        uw.set("parent_id",userCollectionDto.getParentId());
        uw.eq("collection_id",userCollectionDto.getCollectionId());
        this.update(uw);
    }

    /**
     * 递归校验目标目录是否是当前目录的子集
     * @param map
     * @param collectionId
     * @param parentId
     * @return
     */
    private boolean checkChildCatalog(Map<String, List<CollectionBo>> map, String collectionId, String parentId) {
        List<CollectionBo> collectionBos = map.get(collectionId);
        if(!CollectionUtils.isEmpty(collectionBos)){
            for (CollectionBo collectionBo : collectionBos) {
                if(collectionBo.getCollectionId().equals(parentId)){
                    return true;
                }
                boolean childList = checkChildCatalog(map, collectionBo.getCollectionId(), parentId);
                if(childList){
                    return true;
                }
            }
        }
        return false;
    }
}
