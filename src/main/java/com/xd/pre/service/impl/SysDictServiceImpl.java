package com.xd.pre.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xd.pre.domain.SysDict;
import com.xd.pre.dto.DictDTO;
import com.xd.pre.mapper.SysDictMapper;
import com.xd.pre.service.ISysDictService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 字典表 服务实现类
 * </p>
 *
 * @author lihaodong
 * @since 2019-05-17
 */
@Service
public class SysDictServiceImpl extends ServiceImpl<SysDictMapper, SysDict> implements ISysDictService {

    @Override
    public boolean save(SysDict entity) {
        return super.save(entity);
    }

    @Override
    public IPage<SysDict> selectDictList(int page, int pageSize) {
        Page<SysDict> dictPage = new Page<>(page, pageSize);
        IPage<SysDict> sysDictIPage = baseMapper.selectPage(dictPage, Wrappers.<SysDict>lambdaQuery().select(SysDict::getId, SysDict::getName, SysDict::getDescription, SysDict::getRemark, SysDict::getCreateTime));
        sysDictIPage.setRecords(sysDictIPage.getRecords().stream().filter(sysDict -> StringUtils.isNotEmpty(sysDict.getDescription())).collect(Collectors.toList()));
        return sysDictIPage;
    }

    @Override
    public boolean updateDict(DictDTO dictDto) {
        if (ObjectUtil.isNull(dictDto.getValue())) {
            // 先查询所有的含有的主键 然后批量修改
            List<SysDict> sysDicts = baseMapper.selectList(Wrappers.<SysDict>lambdaQuery().select(SysDict::getId).eq(SysDict::getName, baseMapper.selectById(dictDto.getId()).getName()));
            List<SysDict> collect = sysDicts.stream().map(sysDict1 -> {
                SysDict sysDict = new SysDict();
                sysDict.setId(sysDict1.getId());
                sysDict.setName(dictDto.getName());
                return sysDict;
            }).collect(Collectors.toList());
            return updateBatchById(collect);
        }
        SysDict sysDict = new SysDict();
        BeanUtil.copyProperties(dictDto, sysDict);
        return baseMapper.updateById(sysDict) > 0;
    }

    @Override
    public List<SysDict> selectDictDetailList(String name) {
        return baseMapper.selectList(Wrappers.<SysDict>lambdaQuery().select(SysDict::getId, SysDict::getName, SysDict::getValue, SysDict::getLabel, SysDict::getSort).eq(SysDict::getName, name)).stream().filter(sysDict -> StringUtils.isNotEmpty(sysDict.getValue())).collect(Collectors.toList());

    }

    @Override
    public boolean deleteDictByName(String name) {

        return baseMapper.delete(Wrappers.<SysDict>lambdaQuery().isNull(SysDict::getValue).eq(SysDict::getName, name)) > 0;
    }

    @Override
    public boolean removeById(Serializable id) {
        return super.removeById(id);
    }
}
