package wpy.personal.novel.novel.novel.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.multipart.MultipartFile;
import wpy.personal.novel.pojo.bo.SeriesBo;
import wpy.personal.novel.pojo.bo.SeriesListBo;
import wpy.personal.novel.pojo.dto.SeriesDto;
import wpy.personal.novel.pojo.entity.Series;
import com.baomidou.mybatisplus.extension.service.IService;
import wpy.personal.novel.pojo.entity.SysUser;
import wpy.personal.novel.pojo.vo.SeriesListVo;
import wpy.personal.novel.utils.pageUtils.RequestPageUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * <p>
 * 系列表 服务类
 * </p>
 *
 * @author wangpanyin
 * @since 2021-09-07
 */
public interface SeriesService extends IService<Series> {

    /**
     * 根据条件查询系列
     * @param param
     * @param sysUser
     * @return
     */
    Page<SeriesListBo> getSeriesList(RequestPageUtils<SeriesListVo> param, SysUser sysUser);

    /**
     * 插入小说
     * @param seriesDto
     * @param sysUser
     * @return
     */
    Series addSeries(SeriesDto seriesDto, SysUser sysUser);

    /**
     * 根据id获取小说详情
     * @param seriesId
     * @param sysUser
     * @return
     */
    SeriesBo getSeriesInfo(String seriesId, SysUser sysUser);

    /**
     * 修改小说信息
     * @param seriesDto
     * @param sysUser
     * @return
     */
    Series updateSeries(SeriesDto seriesDto, SysUser sysUser);

    /**
     * 删除小说
     * @param idList
     * @param sysUser
     */
    void deleteSeries(List<String> idList, SysUser sysUser);

    /**
     * 快速上传
     * @param file
     * @param sysUser
     * @return
     */
    Series quickUpload(MultipartFile file, SysUser sysUser);

    /**
     * 更新小说总数
     * @param novelId
     * @param sysUser
     */
    void updateTotal(String novelId,SysUser sysUser);

    /**
     * 下载小说
     * @param novelId
     * @param sysUser
     * @param request
     * @param response
     */
    void download(String novelId, SysUser sysUser, HttpServletRequest request, HttpServletResponse response);
}
