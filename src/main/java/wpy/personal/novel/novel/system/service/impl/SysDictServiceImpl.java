package wpy.personal.novel.novel.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wpy.personal.novel.base.enums.SqlEnums;
import wpy.personal.novel.base.exception.BusinessException;
import wpy.personal.novel.novel.system.mapper.SysDictMapper;
import wpy.personal.novel.novel.system.service.SysDictParamService;
import wpy.personal.novel.novel.system.service.SysDictService;
import wpy.personal.novel.pojo.dto.SysDictDto;
import wpy.personal.novel.pojo.entity.SysDict;
import wpy.personal.novel.pojo.entity.SysDictParam;
import wpy.personal.novel.pojo.entity.SysUser;
import wpy.personal.novel.utils.ObjectUtils;
import wpy.personal.novel.utils.StringUtils;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 字典表 服务实现类
 * </p>
 *
 * @author wangpanyin
 * @since 2021-09-07
 */
@Service
@Transactional
public class SysDictServiceImpl extends ServiceImpl<SysDictMapper, SysDict> implements SysDictService {

    @Autowired
    private SysDictParamService sysDictParamService;
    @Autowired
    private SysDictMapper sysDictMapper;

    @Override
    public SysDict addDict(SysDictDto sysDictDto, SysUser sysUser) {
        //检查重复项
        int count = this.count(new QueryWrapper<SysDict>().eq("dict_code", sysDictDto.getDictCode()));
        if(count>0){
            throw BusinessException.fail("字典code禁止重复");
        }

        SysDict sysDict = ObjectUtils.newInstance(sysUser.getUserId(),SysDict.class);
        ObjectUtils.copyPropertiesIgnoreNull(sysDictDto,sysDict);
        sysDict.setDictId(StringUtils.getUuid32());
        this.save(sysDict);
        //处理子项
        if(SqlEnums.DICT_LIST.getCode().equals(sysDictDto.getDictType())){
            sysDictParamService.addDictParam(sysDictDto.getParamList(),sysDict.getDictId(),sysUser);
        }
        return sysDict;
    }

    @Override
    public Page<SysDict> getDictList(SysDictDto sysDictDto, SysUser sysUser) {
        QueryWrapper<SysDict> qw = new QueryWrapper<>();
        return sysDictMapper.selectPage(new Page<>(sysDictDto.getPage(), sysDictDto.getPageSize()), qw);
    }

    @Override
    public SysDictDto getDictInfo(String dictId, SysUser sysUser) {
        SysDict sysDict = this.getById(dictId);
        SysDictDto sysDictDto =new SysDictDto();
        BeanUtils.copyProperties(sysDict,sysDictDto);
        if(SqlEnums.DICT_LIST.getCode().equals(sysDictDto.getDictType())){
            sysDictDto.setParamList(sysDictParamService.getBaseMapper().selectList(new QueryWrapper<SysDictParam>()
                    .eq("dict_id",dictId)
                    .orderByAsc("param_order")));
        }
        return sysDictDto;
    }

    @Override
    public SysDict updateDict(SysDictDto sysDictDto, SysUser sysUser) {
        SysDict sysDict = new SysDict();
        BeanUtils.copyProperties(sysDictDto,sysDict);
        sysDict.setUpdateBy(sysUser.getUserId());
        sysDict.setUpdateTime(new Date());
        // 先保存
        this.updateById(sysDict);
        //先删再增
        sysDictParamService.remove(new QueryWrapper<SysDictParam>().eq("dict_id",sysDictDto.getDictId()));
        if(SqlEnums.DICT_LIST.getCode().equals(sysDictDto.getDictType())){
            sysDictParamService.addDictParam(sysDictDto.getParamList(),sysDict.getDictId(),sysUser);
        }
        return sysDict;
    }

    @Override
    public void deleteDict(List<String> ids, SysUser sysUser) {
        this.removeByIds(ids);
        this.sysDictParamService.remove(new QueryWrapper<SysDictParam>().in("dict_id",ids));
    }
}
