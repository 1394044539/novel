package wpy.personal.novel.novel.novel.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import wpy.personal.novel.pojo.bo.UploadListBo;
import wpy.personal.novel.pojo.entity.NovelFile;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import wpy.personal.novel.pojo.vo.UploadListVo;

import java.util.List;

/**
 * <p>
 * 小说文件表 Mapper 接口
 * </p>
 *
 * @author wangpanyin
 * @since 2021-09-07
 */
public interface NovelFileMapper extends BaseMapper<NovelFile> {

    /**
     * 列表查询
     * @param param
     * @param page
     * @return
     */
    List<UploadListBo> list(@Param("param") UploadListVo param, Page<UploadListBo> page);
}
