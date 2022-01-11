package wpy.personal.novel.novel.novel.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
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
import wpy.personal.novel.base.enums.SqlEnums;
import wpy.personal.novel.novel.novel.mapper.NovelMapper;
import wpy.personal.novel.novel.novel.mapper.NovelVolumeMapper;
import wpy.personal.novel.novel.novel.mapper.UserCollectionMapper;
import wpy.personal.novel.novel.novel.service.NovelFileService;
import wpy.personal.novel.novel.novel.service.NovelService;
import wpy.personal.novel.novel.novel.service.NovelVolumeService;
import wpy.personal.novel.novel.novel.service.UserCollectionService;
import wpy.personal.novel.pojo.bo.CollectionBo;
import wpy.personal.novel.pojo.dto.UserCollectionDto;
import wpy.personal.novel.pojo.entity.*;
import wpy.personal.novel.utils.FileUtils;
import wpy.personal.novel.utils.ObjectUtils;
import wpy.personal.novel.utils.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

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
    private NovelService novelService;
    @Autowired
    private NovelMapper novelMapper;
    @Autowired
    private NovelVolumeService novelVolumeService;
    @Autowired
    private NovelVolumeMapper novelVolumeMapper;
    @Autowired
    private NovelFileService novelFileService;

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
        List<UserCollection> userCollections = this.userCollectionMapper.selectList(qw);
        if(CollectionUtils.isEmpty(userCollections)){
            return null;
        }
        return userCollections.get(0);
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

    @Override
    public void download(UserCollectionDto userCollectionDto, SysUser sysUser, HttpServletRequest request, HttpServletResponse response) {
        if(SqlEnums.COLLECTION_CATALOG.getCode().equals(userCollectionDto.getCollectionType())){
           //文件夹下载
            this.downloadCatalog(userCollectionDto.getCollectionId(),sysUser,request,response);
        }else if(SqlEnums.COLLECTION_VOLUME.getCode().equals(userCollectionDto.getCollectionType())){
            //分卷下载
            novelVolumeService.download(userCollectionDto.getVolumeId(),sysUser,request,response);
        }else if(SqlEnums.COLLECTION_NOVEL.getCode().equals(userCollectionDto.getCollectionType())){
            //小说下载
            novelService.download(userCollectionDto.getNovelId(),sysUser,request,response);
        }
    }

    @Override
    public void downloadAll(SysUser sysUser, HttpServletRequest request, HttpServletResponse response) {
        //先吧自己打成一个压缩包
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

    /**
     * 下载文件夹
     * @param collectionId
     * @param sysUser
     * @param request
     * @param response
     */
    private void downloadCatalog(String collectionId, SysUser sysUser, HttpServletRequest request, HttpServletResponse response) {
        //先吧自己打成一个压缩包
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
     * 递归下载
     * @param collectionId
     * @param zipos
     * @param os
     * @param catalogName
     * @param sysUser
     * @throws Exception
     */
    private void recursionDownloadCatalog(String collectionId, ZipOutputStream zipos, DataOutputStream os, String catalogName, SysUser sysUser) throws Exception{
        //1、取出收藏的目录下的所有信息
        UserCollectionDto dto = new UserCollectionDto();
        dto.setParentId(collectionId);
        List<CollectionBo> list = this.userCollectionMapper.selectCollections(dto, sysUser);
        List<String> renameList = Lists.newArrayList();
        for (CollectionBo collectionBo : list) {
            if(SqlEnums.COLLECTION_VOLUME.getCode().equals(collectionBo.getCollectionType())){
                //分卷
                NovelVolume volume = this.novelVolumeService.getById(collectionBo.getVolumeId());
                NovelFile file = this.novelFileService.getById(volume.getFileId());
                String pathName=catalogName+CharConstant.FILE_SEPARATOR+
                        volume.getVolumeName()+CharConstant.SEPARATOR+file.getFileType();
                pathName = this.checkRename(renameList,pathName);
                renameList.add(pathName);
                ZipEntry zipEntry=new ZipEntry(pathName);
                zipos.putNextEntry(zipEntry);
                InputStream inputStream=new FileInputStream(new File(file.getFilePath()));
                int b = 0;
                byte[] buffer = new byte[1024];
                while ((b = inputStream.read(buffer)) != -1) {
                    os.write(buffer, 0, b);
                }
            }else if(SqlEnums.COLLECTION_NOVEL.getCode().equals(collectionBo.getCollectionType())){
                //小说
                Novel novel = this.novelService.getById(collectionBo.getNovelId());
                //获取当前小说的全部分卷
                List<NovelVolume> volumeList = this.novelVolumeService.getVolumeList(collectionBo.getNovelId(), sysUser);
                for (NovelVolume novelVolume : volumeList) {
                    NovelFile file = this.novelFileService.getById(novelVolume.getFileId());
                    String pathName=catalogName+CharConstant.FILE_SEPARATOR+"(系列)"+novel.getNovelName()+CharConstant.FILE_SEPARATOR+
                            novelVolume.getVolumeName()+CharConstant.SEPARATOR+file.getFileType();
                    pathName = this.checkRename(renameList,pathName);
                    renameList.add(pathName);
                    ZipEntry zipEntry=new ZipEntry(pathName);
                    zipos.putNextEntry(zipEntry);
                    InputStream inputStream=new FileInputStream(new File(file.getFilePath()));
                    int b = 0;
                    byte[] buffer = new byte[1024];
                    while ((b = inputStream.read(buffer)) != -1) {
                        os.write(buffer, 0, b);
                    }
                }
            }else if(SqlEnums.COLLECTION_CATALOG.getCode().equals(collectionBo.getCollectionType())){
                //文件夹
                String catalogPath=catalogName+CharConstant.FILE_SEPARATOR+"(文件夹)"+collectionBo.getCatalogName();
                catalogPath = this.checkRename(renameList,catalogPath);
                renameList.add(catalogPath);
                recursionDownloadCatalog(collectionBo.getCollectionId(),zipos,os,catalogPath,sysUser);
            }
        }
    }

    /**
     * 解决重命名问题
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
     * 复制
     * @param userCollectionDto
     * @param sysUser
     */
    private void copy(UserCollectionDto userCollectionDto, SysUser sysUser) {
        List<UserCollection> collections = this.userCollectionMapper.getAllCollectionCatalog(null,sysUser.getUserId());
        // 按照父目录id进行分组
        Map<String, List<UserCollection>> map = collections.stream().peek(e->{
            if(StringUtils.isEmpty(e.getParentId())){
                e.setParentId("");
            }
        }).collect(Collectors.groupingBy(UserCollection::getParentId));
        //目标目录Id
        String parentId = userCollectionDto.getParentId();
        //被复制的目录Id
        UserCollection collection = this.getById(userCollectionDto.getCollectionId());
        // 第一级目录需要单独创建出来
        UserCollection userCollection = ObjectUtils.newInstance(sysUser.getUserId(),UserCollection.class);
        userCollection.setCollectionId(StringUtils.getUuid32());
        userCollection.setCatalogName(collection.getCatalogName());
        userCollection.setImgPath(collection.getImgPath());
        userCollection.setCollectionType(collection.getCollectionType());
        userCollection.setNovelId(collection.getNovelId());
        userCollection.setVolumeId(collection.getVolumeId());
        userCollection.setParentId(parentId);
        List<UserCollection> insertList = Lists.newLinkedList();
        insertList.add(userCollection);
        //递归创建新的层级
        List<UserCollection> list = getNewChildCatalog(collection.getCollectionId(),userCollection.getCollectionId(),map,sysUser);
        insertList.addAll(list);
        //插入数据库
        this.saveBatch(insertList);
    }

    /**
     * 获取复制时的子集目录
     * @param srcId 原目录的父级id
     * @param parentId 新目录的父级id
     * @param map 总目录集合
     * @param sysUser 操作人
     * @return
     */
    private List<UserCollection> getNewChildCatalog(String srcId, String parentId, Map<String, List<UserCollection>> map, SysUser sysUser) {
        List<UserCollection> reList = Lists.newLinkedList();
        //被复制的集合
        List<UserCollection> list = map.get(srcId);
        if(!CollectionUtils.isEmpty(list)){
            for (UserCollection collection : list) {
                UserCollection userCollection = ObjectUtils.newInstance(sysUser.getUserId(),UserCollection.class);
                userCollection.setCollectionId(StringUtils.getUuid32());
                userCollection.setCatalogName(collection.getCatalogName());
                userCollection.setImgPath(collection.getImgPath());
                userCollection.setCollectionType(collection.getCollectionType());
                userCollection.setNovelId(collection.getNovelId());
                userCollection.setVolumeId(collection.getVolumeId());
                userCollection.setParentId(parentId);
                reList.add(userCollection);
                List<UserCollection> newChildCatalog = getNewChildCatalog(collection.getCollectionId(), userCollection.getCollectionId(), map, sysUser);
                reList.addAll(newChildCatalog);
            }
        }
        return reList;
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
