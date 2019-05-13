package com.xd.pre.codegen.service.Impl;

import com.xd.pre.codegen.domain.ColumnEntity;
import com.xd.pre.codegen.domain.SysDb;
import com.xd.pre.codegen.domain.TableEntity;
import com.xd.pre.codegen.dto.DbDto;
import com.xd.pre.codegen.mapper.SysCodeGenMapper;
import com.xd.pre.codegen.service.SysCodeGenService;
import com.xd.pre.codegen.utils.JDBCTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Classname SysCodeGenServiceImpl
 * @Description TODO
 * @Author 李号东 lihaodongmail@163.com
 * @Date 2019-04-25 17:07
 * @Version 1.0
 */
@Service
public class SysCodeGenServiceImpl implements SysCodeGenService {

    @Autowired
    private SysCodeGenMapper codeGenMapper;

    @Override
    public List<TableEntity> selectTable(String tableSchema) {
        return codeGenMapper.selectTable(tableSchema);
    }

    @Override
    public List<ColumnEntity> selectColumn(String tableName) {
        return codeGenMapper.selectColumn(tableName);
    }

    @Override
    public boolean testConnection(DbDto dbDto) {
        return JDBCTool.testConnection(dbDto);
    }
}
