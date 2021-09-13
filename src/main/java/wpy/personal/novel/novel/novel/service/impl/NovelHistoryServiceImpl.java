package wpy.personal.novel.novel.novel.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wpy.personal.novel.base.enums.SqlEnums;
import wpy.personal.novel.base.exception.BusinessException;
import wpy.personal.novel.novel.novel.mapper.NovelHistoryMapper;
import wpy.personal.novel.novel.novel.service.NovelHistoryService;
import wpy.personal.novel.pojo.dto.HistoryDto;
import wpy.personal.novel.pojo.entity.NovelHistory;
import wpy.personal.novel.pojo.entity.SysUser;
import wpy.personal.novel.utils.ObjectUtils;
import wpy.personal.novel.utils.StringUtils;

import java.util.Date;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wangpanyin
 * @since 2021-09-12
 */
@Service
@Transactional
public class NovelHistoryServiceImpl extends ServiceImpl<NovelHistoryMapper, NovelHistory> implements NovelHistoryService {

    @Autowired
    private NovelHistoryMapper novelHistoryMapper;

    @Override
    public Page<NovelHistory> getHistoryList(HistoryDto dto, SysUser sysUser) {
        QueryWrapper<NovelHistory> qw = new QueryWrapper<>();

        return this.novelHistoryMapper.selectPage(new Page<NovelHistory>(dto.getPage(), dto.getPageSize()), qw);
    }

    @Override
    public NovelHistory saveHistory(HistoryDto historyDto, SysUser sysUser, String ip) {
        //1、判断是历史还是书签记录
        if(SqlEnums.HISTORY_RECORD.getCode().equals(historyDto.getRecordType())){
            //历史记录
           return this.handleHistory(historyDto,sysUser,ip);
        }else if(SqlEnums.BOOK_MARK.getCode().equals(historyDto.getRecordType())){
            return this.handleMark(historyDto,sysUser,ip);
        }else {
            throw BusinessException.fail("记录类型错误");
        }
    }

    @Override
    public NovelHistory getHistory(String historyId, SysUser sysUser) {

        return this.getById(historyId);
    }

    /**
     * 处理书签记录
     * @param historyDto
     * @param sysUser
     * @param ip
     * @return
     */
    private NovelHistory handleMark(HistoryDto historyDto, SysUser sysUser, String ip) {
        //书签只能修改一个名字
        NovelHistory novelHistory = new NovelHistory();
        if(StringUtils.isEmpty(historyDto.getHistoryId())){
            //新增
            novelHistory = ObjectUtils.newInstance(sysUser.getUserId(), NovelHistory.class);
            ObjectUtils.copyPropertiesIgnoreNull(historyDto,novelHistory);
            if(StringUtils.isEmpty(novelHistory.getMarkName())){
                //生成名字加四位随机数
                novelHistory.setMarkName(historyDto.getChapterName()+ RandomStringUtils.randomAlphanumeric(4));
            }
            novelHistory.setIp(ip);
            novelHistory.setHistoryId(StringUtils.getUuid32());
            this.save(novelHistory);
        }else {
            //修改
            novelHistory.setIp(ip);
            novelHistory.setUpdateBy(sysUser.getUserId());
            novelHistory.setUpdateTime(new Date());
            novelHistory.setMarkName(historyDto.getMarkName());
            this.updateById(novelHistory);
        }
        return novelHistory;
    }

    /**
     * 处理历史
     * @param historyDto
     * @param sysUser
     * @param ip
     * @return
     */
    private NovelHistory handleHistory(HistoryDto historyDto, SysUser sysUser, String ip) {
        //这里没有id，只有分卷id
        NovelHistory novelVolume = this.getOne(new QueryWrapper<NovelHistory>().eq("last_volume_id", historyDto.getLastVolumeId()));
        if(novelVolume==null){
            //说明是第一次，就插入就行
            novelVolume = ObjectUtils.newInstance(sysUser.getUserId(), NovelHistory.class);
            ObjectUtils.copyPropertiesIgnoreNull(historyDto,novelVolume);
            novelVolume.setIp(ip);
            novelVolume.setHistoryId(StringUtils.getUuid32());
            this.save(novelVolume);
        }else {
            //说明之前已经有过记录了
            novelVolume.setLastChapterId(historyDto.getLastChapterId());
            novelVolume.setIp(ip);
            novelVolume.setRecordPercentage(historyDto.getRecordPercentage());
            novelVolume.setUpdateTime(new Date());
            novelVolume.setUpdateBy(sysUser.getUserId());
            this.updateById(novelVolume);
        }
        return novelVolume;
    }
}
