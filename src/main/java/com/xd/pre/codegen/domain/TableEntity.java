package com.xd.pre.codegen.domain;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Classname TableEntity
 * @Description 数据库字段
 * @Author 李号东 lihaodongmail@163.com
 * @Date 2019-04-25 14:39
 * @Version 1.0
 * @sql select table_schema,table_name,table_comment,engine,create_time,update_time,table_collation from information_schema.tables where table_schema='pre' and table_type='base table';
 */
@Data
public class TableEntity {

    /**
     * 数据库名称
     */
    private String tableSchema;

    /**
     * 表名称
     */
    private String tableName;

    /**
     * 表注释
     */
    private String tableComment;

    /**
     * 表引擎
     */
    private String tableEngine;

    /**
     * 表字符集
     */
    private String tableCollation;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    private LocalDateTime updateTime;




}
