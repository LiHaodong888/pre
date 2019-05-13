package com.xd.pre.codegen.mapper;

import com.xd.pre.codegen.domain.ColumnEntity;
import com.xd.pre.codegen.domain.TableEntity;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Classname SysCodeGenMapper
 * @Description TODO
 * @Author 李号东 lihaodongmail@163.com
 * @Date 2019-04-25 15:11
 * @Version 1.0
 */
@Repository
public interface SysCodeGenMapper {

    /**
     * 根据数据库名查询表信息
     * @return
     */
    @Select("SELECT table_schema,table_name,table_comment,engine as table_engine,create_time,update_time,table_collation FROM information_schema.tables WHERE table_schema= #{tableSchema} AND table_type='base table'")
    List<TableEntity> selectTable(String tableSchema);


    /**
     * 根据表明查询表字段信息
     * @param tableName
     * @return
     */
    @Select("SELECT table_name,data_type,column_comment FROM INFORMATION_SCHEMA.COLUMNS WHERE table_name = #{tableName} AND table_schema = 'pre'")
    List<ColumnEntity> selectColumn(String tableName);
}
