package com.xd.pre.codegen.service;

import com.xd.pre.codegen.domain.ColumnEntity;
import com.xd.pre.codegen.domain.SysDb;
import com.xd.pre.codegen.domain.TableEntity;
import com.xd.pre.codegen.dto.DbDto;

import java.util.List;

/**
 * @Classname SysCodeGenService
 * @Description TODO
 * @Author 李号东 lihaodongmail@163.com
 * @Date 2019-04-25 17:06
 * @Version 1.0
 */
public interface SysCodeGenService {

    List<TableEntity> selectTable(String tableSchema);

    List<ColumnEntity> selectColumn(String tableName);

    boolean testConnection(DbDto dbDto);
}
