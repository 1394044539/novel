package wpy.personal.novel.novel.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wpy.personal.novel.novel.system.mapper.FeedbackMapper;
import wpy.personal.novel.novel.system.service.FeedbackService;
import wpy.personal.novel.pojo.dto.FeedbackDto;
import wpy.personal.novel.pojo.entity.Feedback;
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
 * @since 2022-01-07
 */
@Service
public class FeedbackServiceImpl extends ServiceImpl<FeedbackMapper, Feedback> implements FeedbackService {

    @Autowired
    private FeedbackMapper feedbackMapper;

    @Override
    public void insert(FeedbackDto dto, SysUser sysUser) {
        Feedback feedback = ObjectUtils.newInstance(sysUser.getUserId(), Feedback.class);
        feedback.setFeedbackId(StringUtils.getUuid32());
        ObjectUtils.copyProperties(dto,feedback);
        this.save(feedback);
    }

    @Override
    public void update(FeedbackDto dto, SysUser sysUser) {
        Feedback feedback = new Feedback();
        BeanUtils.copyProperties(dto,feedback);
        feedback.setUpdateTime(new Date());
        feedback.setUpdateBy(sysUser.getUserId());
        this.updateById(feedback);
    }

    @Override
    public Page<Feedback> list(FeedbackDto dto, SysUser sysUser) {
        QueryWrapper<Feedback> qw = new QueryWrapper<Feedback>();
        Page<Feedback> page = this.feedbackMapper.selectPage(new Page<Feedback>(dto.getPage(), dto.getPageSize()), qw);
        return page;
    }
}
