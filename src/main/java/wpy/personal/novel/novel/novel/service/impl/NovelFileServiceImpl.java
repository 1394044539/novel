package wpy.personal.novel.novel.novel.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;
import wpy.personal.novel.base.constant.CharConstant;
import wpy.personal.novel.base.enums.DictEnums;
import wpy.personal.novel.novel.novel.mapper.NovelFileMapper;
import wpy.personal.novel.novel.novel.service.NovelFileService;
import wpy.personal.novel.novel.system.service.SysUserRoleService;
import wpy.personal.novel.pojo.bo.UploadListBo;
import wpy.personal.novel.pojo.entity.NovelFile;
import wpy.personal.novel.pojo.entity.SysUser;
import wpy.personal.novel.pojo.vo.UploadListVo;
import wpy.personal.novel.utils.FileUtils;
import wpy.personal.novel.utils.ObjectUtils;
import wpy.personal.novel.utils.StringUtils;
import wpy.personal.novel.utils.pageUtils.RequestPageUtils;

import java.util.List;

/**
 * <p>
 * 小说文件表 服务实现类
 * </p>
 *
 * @author wangpanyin
 * @since 2021-09-07
 */
@Service
@Transactional
public class NovelFileServiceImpl extends ServiceImpl<NovelFileMapper, NovelFile> implements NovelFileService {

    @Autowired
    private NovelFileMapper novelFileMapper;
    @Autowired
    private SysUserRoleService sysUserRoleService;

    @Value("${novel.filePath.rootPath}")
    private String rootPath;
    @Value("${novel.filePath.novelFile}")
    private String novelFilePath;

    @Override
    public NovelFile saveFile(MultipartFile volumeFile, String novelId, String userId) {

        //首先需要判断该文件是否已存在
        String fileNamMd5 = FileUtils.getMd5(volumeFile);
        NovelFile fileMd5 = this.getOne(new QueryWrapper<NovelFile>().eq("file_md5", fileNamMd5));
        if(fileMd5!=null){
            return fileMd5;
        }
        //不存在则上传
        NovelFile novelFile = ObjectUtils.newInstance(userId, NovelFile.class);
        novelFile.setFileId(StringUtils.getUuid32());
        novelFile.setFileSize(volumeFile.getSize());
        novelFile.setFileType(FileUtils.getFileType(volumeFile.getOriginalFilename()));
        novelFile.setFileName(volumeFile.getOriginalFilename());
        //上传文件,用md5作为文件名，路径用配置+小说id表示
        String uploadFilePath = rootPath + CharConstant.FILE_SEPARATOR+novelFilePath+CharConstant.FILE_SEPARATOR+novelId;
        FileUtils.upload(volumeFile,uploadFilePath,fileNamMd5);
        novelFile.setFileMd5(fileNamMd5);
        novelFile.setFilePath(uploadFilePath);
        this.save(novelFile);
        return novelFile;
    }

    @Override
    public void deleteFiles(List<String> deleteFileIdList) {
        if(CollectionUtils.isEmpty(deleteFileIdList)){
            return;
        }
        //1、删除文件信息
        List<NovelFile> novelFiles = this.novelFileMapper.selectBatchIds(deleteFileIdList);
        for (NovelFile novelFile : novelFiles) {
            FileUtils.deleteFile(novelFile.getFilePath()+CharConstant.FILE_SEPARATOR+novelFile.getFileMd5());
        }
        //2、删除数据库
        this.removeByIds(deleteFileIdList);
    }

    @Override
    public Page<UploadListBo> list(RequestPageUtils<UploadListVo> dto, SysUser sysUser) {
        UploadListVo param = dto.getParam();
        if(sysUserRoleService.hasRole(sysUser.getUserId(), DictEnums.ORDINARY_USER)){
            //非管理员只能看自己的
            param.setCreateBy(sysUser.getUserId());
        }
        Page<UploadListBo> page = new Page<>(dto.getPage(),dto.getPageSize());
        List<UploadListBo> list = this.novelFileMapper.list(param,page);

        return page.setRecords(list);
    }

}
