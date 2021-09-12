package wpy.personal.novel.novel.system.service.impl;

import com.google.common.collect.Lists;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import wpy.personal.novel.base.enums.SqlEnums;
import wpy.personal.novel.base.exception.BusinessException;
import wpy.personal.novel.pojo.entity.SysDictParam;
import wpy.personal.novel.novel.system.mapper.SysDictParamMapper;
import wpy.personal.novel.novel.system.service.SysDictParamService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import wpy.personal.novel.pojo.entity.SysUser;
import wpy.personal.novel.utils.StringUtils;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 字段子类表 服务实现类
 * </p>
 *
 * @author wangpanyin
 * @since 2021-09-07
 */
@Service
@Transactional
public class SysDictParamServiceImpl extends ServiceImpl<SysDictParamMapper, SysDictParam> implements SysDictParamService {

    @Override
    public void addDictParam(List<SysDictParam> paramList, String dictId, SysUser sysUser) {
        //说明有子项
        if(CollectionUtils.isEmpty(paramList)){
            throw BusinessException.fail("请填写字典子项");
        }
        List<String> codeList = Lists.newArrayList();
        for (SysDictParam sysDictParam : paramList) {
            if(StringUtils.isEmpty(sysDictParam.getParamCode())||StringUtils.isEmpty(sysDictParam.getParamName())){
                throw BusinessException.fail("子字典编码和值不能为空");
            }
            if(codeList.contains(sysDictParam.getParamCode())){
                throw BusinessException.fail("子字典编码不能重复");
            }
            codeList.add(sysDictParam.getParamCode());
            sysDictParam.setDictParamId(StringUtils.getUuid32());
            sysDictParam.setDictId(dictId);
            sysDictParam.setCreateBy(sysUser.getUserId());
            sysDictParam.setCreateTime(new Date());
            sysDictParam.setUpdateBy(sysUser.getUserId());
            sysDictParam.setUpdateTime(new Date());
            sysDictParam.setIsDelete(SqlEnums.NOT_DELETE.getCode());
        }
        this.saveBatch(paramList);
    }
}
