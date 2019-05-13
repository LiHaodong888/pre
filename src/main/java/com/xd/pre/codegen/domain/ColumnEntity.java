package com.xd.pre.codegen.domain;

import lombok.Data;

/**
 * @Classname ColumnEntity
 * @Description 表字段
 * @Author 李号东 lihaodongmail@163.com
 * @Date 2019-04-25 14:43
 * @Version 1.0
 * @sql SELECT table_name,data_type,column_comment FROM INFORMATION_SCHEMA.COLUMNS WHERE table_name = 'sys_dept' AND table_schema = 'pre'
 */
@Data
public class ColumnEntity {

    /**
     * 列名
     */
    private String columnName;
    /**
     * 数据类型
     */
    private String dataType;

    /**
     * 列注释
     */
    private String columnComment;

}
