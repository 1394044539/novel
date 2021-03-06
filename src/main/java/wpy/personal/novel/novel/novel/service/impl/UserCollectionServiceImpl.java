package wpy.personal.novel.novel.novel.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import wpy.personal.novel.base.constant.CharConstant;
import wpy.personal.novel.base.constant.StrConstant;
import wpy.personal.novel.base.enums.BusinessEnums;
import wpy.personal.novel.base.enums.DictEnums;
import wpy.personal.novel.base.enums.SqlEnums;
import wpy.personal.novel.novel.novel.mapper.NovelMapper;
import wpy.personal.novel.novel.novel.mapper.SeriesMapper;
import wpy.personal.novel.novel.novel.mapper.UserCollectionMapper;
import wpy.personal.novel.novel.novel.service.NovelFileService;
import wpy.personal.novel.novel.novel.service.NovelService;
import wpy.personal.novel.novel.novel.service.SeriesService;
import wpy.personal.novel.novel.novel.service.UserCollectionService;
import wpy.personal.novel.novel.system.service.SysUserRoleService;
import wpy.personal.novel.pojo.bo.CollectionBo;
import wpy.personal.novel.pojo.bo.CollectionTableBo;
import wpy.personal.novel.pojo.dto.UserCollectionDto;
import wpy.personal.novel.pojo.entity.*;
import wpy.personal.novel.pojo.vo.UserCollectionListVo;
import wpy.personal.novel.utils.FileUtils;
import wpy.personal.novel.utils.ObjectUtils;
import wpy.personal.novel.utils.StringUtils;
import wpy.personal.novel.utils.pageUtils.RequestPageUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * <p>
 * ??????????????? ???????????????
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
    private SeriesService seriesService;
    @Autowired
    private SeriesMapper seriesMapper;
    @Autowired
    private NovelService novelService;
    @Autowired
    private NovelMapper novelMapper;
    @Autowired
    private NovelFileService novelFileService;
    @Autowired
    private SysUserRoleService sysUserRoleService;

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
        if(SqlEnums.COLLECTION_NOVEL.getCode().equals(type)){
            qw.eq("novel_id",id);
        }else if(SqlEnums.COLLECTION_SERIES.getCode().equals(type)){
            qw.eq("series_id",id);
            qw.and(entity->entity.eq("novel_id","").or().isNull("novel_id"));
        }else if(SqlEnums.COLLECTION_CATALOG.getCode().equals(type)){
            qw.eq("parent_id",id);
        }
//        List<UserCollection> userCollections = this.userCollectionMapper.selectList(qw);
//        if(CollectionUtils.isEmpty(userCollections)){
//            return null;
//        }
//        return userCollections.get(0);
        return this.userCollectionMapper.selectOne(qw);
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
     * ????????????
     * @param collectionId
     */
    private void recursionRemove(String collectionId) {
        //?????????
        List<UserCollection> list = userCollectionMapper.selectList(new QueryWrapper<UserCollection>().eq("parent_id", collectionId));
        if(!CollectionUtils.isEmpty(list)){
            //?????????
            for (UserCollection userCollection : list) {
                recursionRemove(userCollection.getCollectionId());
            }
        }
        this.removeById(collectionId);
    }

    @Override
    public List<CollectionBo> getCollectionList(UserCollectionDto dto, SysUser sysUser) {
        //todo ????????????????????????
        //todo ???????????????????????????
        List<CollectionBo> newList = this.userCollectionMapper.selectCollections(dto,sysUser);
        for (CollectionBo collectionBo : newList) {
            if(SqlEnums.COLLECTION_NOVEL.getCode().equals(collectionBo.getCollectionType())){
                collectionBo.setCatalogName(collectionBo.getNovelName());
                collectionBo.setImgPath(collectionBo.getNovelImg());
            }else if(SqlEnums.COLLECTION_SERIES.getCode().equals(collectionBo.getCollectionType())){
                collectionBo.setCatalogName(collectionBo.getSeriesName());
                collectionBo.setImgPath(collectionBo.getSeriesImg());
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
            //??????
            this.copy(userCollectionDto,sysUser);
        }else if(BusinessEnums.MOVE.getCode().equals(userCollectionDto.getOptType())){
            //??????
            this.move(userCollectionDto,sysUser);
        }
    }

    @Override
    public void download(UserCollectionDto userCollectionDto, SysUser sysUser, HttpServletRequest request, HttpServletResponse response) {
        if(SqlEnums.COLLECTION_CATALOG.getCode().equals(userCollectionDto.getCollectionType())){
           //???????????????
            this.downloadCatalog(userCollectionDto.getCollectionId(),sysUser,request,response);
        }else if(SqlEnums.COLLECTION_NOVEL.getCode().equals(userCollectionDto.getCollectionType())){
            //????????????
            novelService.download(userCollectionDto.getNovelId(),sysUser,request,response);
        }else if(SqlEnums.COLLECTION_SERIES.getCode().equals(userCollectionDto.getCollectionType())){
            //????????????
            seriesService.download(userCollectionDto.getSeriesId(),sysUser,request,response);
        }
    }

    @Override
    public void downloadAll(SysUser sysUser, HttpServletRequest request, HttpServletResponse response) {
        //?????????????????????????????????
        String zipName = StrConstant.ALL_COLLECTION+ CharConstant.SEPARATOR+ StrConstant.ZIP;
        response.setContentType("application/octet-stream;charset=UTF-8");
        response.setContentType("multipart/form-data");
        response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
//        response.setHeader("Accept-Ranges", "bytes");
//        response.addHeader("Content-Length", "" + fileSize);
        try {
            request.setCharacterEncoding("utf-8");
            response.setHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode(zipName,StrConstant.DEFAULT_CHARSET));
        }catch (Exception e){
            log.error(e.getMessage(),e);
        }
        try (
                ZipOutputStream zipos = new ZipOutputStream(new BufferedOutputStream(response.getOutputStream()));
                DataOutputStream os = new DataOutputStream(zipos)
        ){
            zipos.setMethod(ZipOutputStream.DEFLATED);
            this.recursionDownloadCatalog("",zipos,os,StrConstant.ALL_COLLECTION,sysUser);
        }catch (Exception e){
            log.error(e.getMessage());
        }
    }

    @Override
    public Page<CollectionTableBo> list(RequestPageUtils<UserCollectionListVo> pageParam, SysUser sysUser) {
        Page<CollectionTableBo> page = new Page<>(pageParam.getPage(), pageParam.getPageSize());
        UserCollectionListVo param = pageParam.getParam();
        if(sysUserRoleService.hasRole(sysUser.getUserId(), DictEnums.ORDINARY_USER)){
            //??????????????????????????????
            param.setCreateBy(sysUser.getUserId());
        }
        List<CollectionTableBo> list = this.userCollectionMapper.list(param,page);
        for (CollectionTableBo collectionTableBo : list) {
            if(SqlEnums.COLLECTION_NOVEL.getCode().equals(collectionTableBo.getCollectionType())){
                collectionTableBo.setName(collectionTableBo.getNovelName());
            }else if(SqlEnums.COLLECTION_SERIES.getCode().equals(collectionTableBo.getCollectionType())){
                collectionTableBo.setName(collectionTableBo.getSeriesName());
            }
        }
        page.setRecords(list);
        return page;
    }

    @Override
    public void batchCancelCollection(List<UserCollectionDto> list) {
        for (UserCollectionDto dto : list) {
            this.deleteCollection(dto.getCollectionId(),dto.getCollectionType());
        }
    }

    @Override
    public void removeAll(UserCollectionDto dto, SysUser sysUser) {
        //????????????????????????????????????????????????????????????????????????????????????
        if(BusinessEnums.INVALID.getCode().equals(dto.getIsDelete())){
            this.clearInvalidCollection(sysUser);
        }else {
            this.remove(new QueryWrapper<UserCollection>()
                    .eq("create_by",sysUser.getUserId()));
        }
    }

    @Override
    public void clearInvalidCollection(SysUser sysUser) {
        List<UserCollection> userCollections = userCollectionMapper.selectList(new QueryWrapper<UserCollection>()
                .eq("create_by", sysUser.getUserId())
                .ne("collection_type", SqlEnums.COLLECTION_CATALOG.getCode()));
        for (UserCollection userCollection : userCollections) {
            if(SqlEnums.COLLECTION_SERIES.getCode().equals(userCollection.getCollectionType())){
                Series byId = seriesService.getById(userCollection.getNovelId());
            }else if(SqlEnums.COLLECTION_NOVEL.getCode().equals(userCollection.getCollectionType())){

            }
        }
    }

    /**
     * ???????????????
     * @param collectionId
     * @param sysUser
     * @param request
     * @param response
     */
    private void downloadCatalog(String collectionId, SysUser sysUser, HttpServletRequest request, HttpServletResponse response) {
        //?????????????????????????????????
        UserCollection collection = this.getById(collectionId);
        String zipName = collection.getCatalogName()+ CharConstant.SEPARATOR+ StrConstant.ZIP;
        response.setContentType("application/octet-stream;charset=UTF-8");
        response.setContentType("multipart/form-data");
        response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
//        response.setHeader("Accept-Ranges", "bytes");
//        response.addHeader("Content-Length", "" + fileSize);
        try {
            request.setCharacterEncoding("utf-8");
            response.setHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode(zipName,StrConstant.DEFAULT_CHARSET));
        }catch (Exception e){
            log.error(e.getMessage(),e);
        }
        try (
                ZipOutputStream zipos = new ZipOutputStream(new BufferedOutputStream(response.getOutputStream()));
                DataOutputStream os = new DataOutputStream(zipos)
        ){
            zipos.setMethod(ZipOutputStream.DEFLATED);
            this.recursionDownloadCatalog(collectionId,zipos,os,collection.getCatalogName(),sysUser);
        }catch (Exception e){
            log.error(e.getMessage());
        }
    }

    /**
     * ????????????
     * @param collectionId
     * @param zipos
     * @param os
     * @param catalogName
     * @param sysUser
     * @throws Exception
     */
    private void recursionDownloadCatalog(String collectionId, ZipOutputStream zipos, DataOutputStream os, String catalogName, SysUser sysUser) throws Exception{
        //1??????????????????????????????????????????
        UserCollectionDto dto = new UserCollectionDto();
        dto.setParentId(collectionId);
        List<CollectionBo> list = this.userCollectionMapper.selectCollections(dto, sysUser);
        List<String> renameList = Lists.newArrayList();
        for (CollectionBo collectionBo : list) {
            if(SqlEnums.COLLECTION_NOVEL.getCode().equals(collectionBo.getCollectionType())){
                //??????
                Novel novel = this.novelService.getById(collectionBo.getNovelId());
                NovelFile file = this.novelFileService.getById(novel.getFileId());
                String pathName=catalogName+CharConstant.FILE_SEPARATOR+
                        novel.getNovelName()+CharConstant.SEPARATOR+file.getFileType();
                pathName = this.checkRename(renameList,pathName);
                renameList.add(pathName);
                ZipEntry zipEntry=new ZipEntry(pathName);
                zipos.putNextEntry(zipEntry);
                InputStream inputStream=new FileInputStream(new File(file.getFilePath()+CharConstant.FILE_SEPARATOR+file.getFileMd5()));
                int b = 0;
                byte[] buffer = new byte[1024];
                while ((b = inputStream.read(buffer)) != -1) {
                    os.write(buffer, 0, b);
                }
            }else if(SqlEnums.COLLECTION_SERIES.getCode().equals(collectionBo.getCollectionType())){
                //??????
                Series novel = this.seriesService.getById(collectionBo.getNovelId());
                //?????????????????????????????????
                List<Novel> volumeList = this.novelService.getNovelList(collectionBo.getNovelId(), sysUser);
                for (Novel novelVolume : volumeList) {
                    NovelFile file = this.novelFileService.getById(novelVolume.getFileId());
                    String pathName=catalogName+CharConstant.FILE_SEPARATOR+"(??????)"+novel.getSeriesName()+CharConstant.FILE_SEPARATOR+
                            novelVolume.getNovelName()+CharConstant.SEPARATOR+file.getFileType();
                    pathName = this.checkRename(renameList,pathName);
                    renameList.add(pathName);
                    ZipEntry zipEntry=new ZipEntry(pathName);
                    zipos.putNextEntry(zipEntry);
                    InputStream inputStream=new FileInputStream(new File(file.getFilePath()+CharConstant.FILE_SEPARATOR+file.getFileMd5()));
                    int b = 0;
                    byte[] buffer = new byte[1024];
                    while ((b = inputStream.read(buffer)) != -1) {
                        os.write(buffer, 0, b);
                    }
                }
            }else if(SqlEnums.COLLECTION_CATALOG.getCode().equals(collectionBo.getCollectionType())){
                //?????????
                String catalogPath=catalogName+CharConstant.FILE_SEPARATOR+"(?????????)"+collectionBo.getCatalogName();
                catalogPath = this.checkRename(renameList,catalogPath);
                renameList.add(catalogPath);
                recursionDownloadCatalog(collectionBo.getCollectionId(),zipos,os,catalogPath,sysUser);
            }
        }
    }

    /**
     * ?????????????????????
     * @param list
     * @param pathName
     * @return
     */
    private String checkRename(List<String> list, String pathName) {
        if(list.contains(pathName)){
            String fileType = FileUtils.getFileType(pathName);
            String fileName = FileUtils.getFileName(pathName);
            pathName=fileName+ CharConstant.UNDERLINE+RandomStringUtils.randomAlphanumeric(4)+
                    CharConstant.SEPARATOR+fileType;
        }
        return pathName;
    }

    /**
     * ??????
     * @param userCollectionDto
     * @param sysUser
     */
    private void copy(UserCollectionDto userCollectionDto, SysUser sysUser) {
        List<UserCollection> collections = this.userCollectionMapper.getAllCollectionCatalog(null,sysUser.getUserId());
        // ???????????????id????????????
        Map<String, List<UserCollection>> map = collections.stream().peek(e->{
            if(StringUtils.isEmpty(e.getParentId())){
                e.setParentId("");
            }
        }).collect(Collectors.groupingBy(UserCollection::getParentId));
        //????????????Id
        String parentId = userCollectionDto.getParentId();
        //??????????????????Id
        UserCollection collection = this.getById(userCollectionDto.getCollectionId());
        // ???????????????????????????????????????
        UserCollection userCollection = ObjectUtils.newInstance(sysUser.getUserId(),UserCollection.class);
        userCollection.setCollectionId(StringUtils.getUuid32());
        userCollection.setCatalogName(collection.getCatalogName());
        userCollection.setImgPath(collection.getImgPath());
        userCollection.setCollectionType(collection.getCollectionType());
        userCollection.setNovelId(collection.getNovelId());
        userCollection.setSeriesId(collection.getSeriesId());
        userCollection.setParentId(parentId);
        List<UserCollection> insertList = Lists.newLinkedList();
        insertList.add(userCollection);
        //????????????????????????
        List<UserCollection> list = getNewChildCatalog(collection.getCollectionId(),userCollection.getCollectionId(),map,sysUser);
        insertList.addAll(list);
        //???????????????
        this.saveBatch(insertList);
    }

    /**
     * ??????????????????????????????
     * @param srcId ??????????????????id
     * @param parentId ??????????????????id
     * @param map ???????????????
     * @param sysUser ?????????
     * @return
     */
    private List<UserCollection> getNewChildCatalog(String srcId, String parentId, Map<String, List<UserCollection>> map, SysUser sysUser) {
        List<UserCollection> reList = Lists.newLinkedList();
        //??????????????????
        List<UserCollection> list = map.get(srcId);
        if(!CollectionUtils.isEmpty(list)){
            for (UserCollection collection : list) {
                UserCollection userCollection = ObjectUtils.newInstance(sysUser.getUserId(),UserCollection.class);
                userCollection.setCollectionId(StringUtils.getUuid32());
                userCollection.setCatalogName(collection.getCatalogName());
                userCollection.setImgPath(collection.getImgPath());
                userCollection.setCollectionType(collection.getCollectionType());
                userCollection.setNovelId(collection.getNovelId());
                userCollection.setSeriesId(collection.getSeriesId());
                userCollection.setParentId(parentId);
                reList.add(userCollection);
                List<UserCollection> newChildCatalog = getNewChildCatalog(collection.getCollectionId(), userCollection.getCollectionId(), map, sysUser);
                reList.addAll(newChildCatalog);
            }
        }
        return reList;
    }

    /**
     * ??????
     * @param userCollectionDto
     * @param sysUser
     */
    private void move(UserCollectionDto userCollectionDto, SysUser sysUser) {
        //1????????????????????????????????????
//        UserCollectionDto allCollection=new UserCollectionDto();
//        allCollection.setTypeList(Lists.newArrayList("2"));
//        List<CollectionBo> collections = this.userCollectionMapper.selectCollections(allCollection, sysUser);
//        Map<String, List<CollectionBo>> map = collections.stream().collect(Collectors.groupingBy(CollectionBo::getCollectionId));
//        boolean childCatalog = checkChildCatalog(map, userCollectionDto.getCollectionId(), userCollectionDto.getParentId());
//        if(childCatalog){
//            throw BusinessException.fail("???????????????????????????????????????");
//        }
        //2?????????????????????id??????
        UpdateWrapper<UserCollection> uw=new UpdateWrapper<>();
        uw.set("parent_id",userCollectionDto.getParentId());
        uw.eq("collection_id",userCollectionDto.getCollectionId());
        this.update(uw);
    }

    /**
     * ??????????????????????????????????????????????????????
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
