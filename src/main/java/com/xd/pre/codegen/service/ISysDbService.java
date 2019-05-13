package com.xd.pre.codegen.service;

import com.xd.pre.codegen.domain.SysDb;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xd.pre.codegen.dto.DbDto;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 数据库管理 服务类
 * </p>
 *
 * @author lihaodong
 * @since 2019-04-26
 */
public interface ISysDbService extends IService<SysDb> {

    boolean saveDb(DbDto dbDto);

    List<SysDb> selectDbList();

    @Override
    boolean removeById(Serializable id);

    @Override
    boolean updateById(SysDb entity);
}
