package wpy.personal.novel.novel.novel.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import wpy.personal.novel.novel.novel.mapper.SeriesTypeRelMapper;
import wpy.personal.novel.novel.novel.service.SeriesTypeRelService;
import wpy.personal.novel.pojo.entity.SeriesTypeRel;
import wpy.personal.novel.pojo.entity.SysUser;
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
public class SeriesTypeRelServiceImpl extends ServiceImpl<SeriesTypeRelMapper, SeriesTypeRel> implements SeriesTypeRelService {

    @Override
    public void updateSeriesType(String seriesId, List<String> typeCodeList, SysUser sysUser) {
        if(!CollectionUtils.isEmpty(typeCodeList)){
            //删除原来的
            this.remove(new QueryWrapper<SeriesTypeRel>().eq("series_id",seriesId));
            //添加小说类型
            List<SeriesTypeRel> typeList=new ArrayList<>();
            for (String typeCode : typeCodeList) {
                SeriesTypeRel seriesTypeRel = new SeriesTypeRel();
                seriesTypeRel.setSeriesTypeRelId(StringUtils.getUuid32());
                seriesTypeRel.setSeriesId(seriesId);
                seriesTypeRel.setTypeCode(typeCode);
                typeList.add(seriesTypeRel);
            }
            this.saveBatch(typeList);
        }
    }
}
