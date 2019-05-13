package com.xd.pre.codegen.service.Impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.xd.pre.codegen.domain.SysDb;
import com.xd.pre.codegen.dto.DbDto;
import com.xd.pre.codegen.mapper.SysDbMapper;
import com.xd.pre.codegen.service.ISysDbService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 数据库管理 服务实现类
 * </p>
 *
 * @author lihaodong
 * @since 2019-04-26
 */
@Service
public class SysDbServiceImpl extends ServiceImpl<SysDbMapper, SysDb> implements ISysDbService {

    @Override
    public boolean saveDb(DbDto dbDto) {
        SysDb sysDb = new SysDb();
        BeanUtil.copyProperties(dbDto,sysDb);
        return baseMapper.insert(sysDb) > 0;
    }

    @Override
    public List<SysDb> selectDbList() {
        return baseMapper.selectList(Wrappers.<SysDb>query());
    }

    @Override
    public boolean removeById(Serializable id) {
        return super.removeById(id);
    }

    @Override
    public boolean updateById(SysDb entity) {
        return super.updateById(entity);
    }
}

