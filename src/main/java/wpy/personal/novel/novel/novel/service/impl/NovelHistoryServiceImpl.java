package wpy.personal.novel.novel.novel.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wpy.personal.novel.base.enums.SqlEnums;
import wpy.personal.novel.base.exception.BusinessException;
import wpy.personal.novel.novel.novel.mapper.NovelChapterMapper;
import wpy.personal.novel.novel.novel.mapper.NovelHistoryMapper;
import wpy.personal.novel.novel.novel.service.NovelChapterService;
import wpy.personal.novel.novel.novel.service.NovelHistoryService;
import wpy.personal.novel.novel.novel.service.NovelService;
import wpy.personal.novel.pojo.bo.ChapterInfoBo;
import wpy.personal.novel.pojo.bo.NovelHistoryBo;
import wpy.personal.novel.pojo.dto.HistoryDto;
import wpy.personal.novel.pojo.entity.Novel;
import wpy.personal.novel.pojo.entity.NovelChapter;
import wpy.personal.novel.pojo.entity.NovelHistory;
import wpy.personal.novel.pojo.entity.SysUser;
import wpy.personal.novel.pojo.vo.HistoryListVo;
import wpy.personal.novel.utils.ObjectUtils;
import wpy.personal.novel.utils.StringUtils;
import wpy.personal.novel.utils.pageUtils.RequestPageUtils;

import java.util.Date;
import java.util.List;

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
    @Autowired
    private NovelChapterService novelChapterService;
    @Autowired
    private NovelChapterMapper novelChapterMapper;
    @Autowired
    private NovelService novelService;

    @Override
    public Page<NovelHistoryBo> getHistoryList(RequestPageUtils<HistoryListVo> dto, SysUser sysUser) {
        HistoryListVo param = dto.getParam();
        Page<NovelHistoryBo> page = new Page<>(dto.getPage(), dto.getPageSize());
        List<NovelHistoryBo> list = this.novelHistoryMapper.getHistoryList(param,page);
        return page.setRecords(list);
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
    public NovelHistoryBo getHistory(String chapterId, SysUser sysUser) {
        NovelHistoryBo novelHistoryBo = new NovelHistoryBo();
        //找到章节对应的小说，根据小说id，找到上次的进度
        NovelChapter novelChapter = this.novelChapterService.getById(chapterId);
        NovelHistory novelHistory = this.getOne(new QueryWrapper<NovelHistory>()
                .eq("last_novel_id", novelChapter.getNovelId())
                .eq("record_type",SqlEnums.HISTORY_RECORD.getCode()));
        if(novelHistory==null){
            return null;
        }
        BeanUtils.copyProperties(novelHistory,novelHistoryBo);
        NovelChapter chapter = this.novelChapterService.getById(novelHistory.getLastChapterId());
        novelHistoryBo.setChapterName(chapter.getChapterName());
        return novelHistoryBo;
    }

    @Override
    public void batchDelete(List<String> ids, SysUser sysUser) {
        this.removeByIds(ids);
    }

    @Override
    public void clearHistory(HistoryDto historyDto, SysUser sysUser) {
        QueryWrapper<NovelHistory> qw = new QueryWrapper<>();
        qw.eq("create_by",sysUser.getUserId());
        if(StringUtils.isNotEmpty(historyDto.getRecordType())){
            qw.eq("record_type",historyDto.getRecordType());
        }
        this.remove(qw);
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
        //历史记录是针对小说使用的，一个小说只会记录一次记录
        NovelChapter chapter = novelChapterService.getById(historyDto.getLastChapterId());
        NovelHistory novelHistory = this.getOne(new QueryWrapper<NovelHistory>()
                .eq("last_novel_id", chapter.getNovelId())
                .eq("record_type",SqlEnums.HISTORY_RECORD.getCode()));
        if(novelHistory==null){
            //说明是第一次，就插入就行
            novelHistory = new NovelHistory();
            novelHistory.setCreateBy(sysUser.getUserId());
            novelHistory.setCreateTime(new Date());
            novelHistory.setUpdateBy(sysUser.getUserId());
            novelHistory.setUpdateTime(new Date());
            ObjectUtils.copyPropertiesIgnoreNull(historyDto,novelHistory);
            novelHistory.setIp(ip);
            novelHistory.setHistoryId(StringUtils.getUuid32());
            //第一次需要插入小说id和系列id
            novelHistory.setLastNovelId(chapter.getNovelId());
            novelHistory.setLastSeriesId(chapter.getSeriesId());
            this.save(novelHistory);
        }else {
            //说明之前已经有过记录了
            novelHistory.setLastChapterId(historyDto.getLastChapterId());
            novelHistory.setIp(ip);
            novelHistory.setRecordPercentage(historyDto.getRecordPercentage());
            novelHistory.setUpdateTime(new Date());
            novelHistory.setUpdateBy(sysUser.getUserId());
            this.updateById(novelHistory);
        }
        return novelHistory;
    }
}
