package com.xd.pre.codegen.dto;

import lombok.Data;

/**
 * @Classname DbDto
 * @Description TODO
 * @Author 李号东 lihaodongmail@163.com
 * @Date 2019-04-27 08:35
 * @Version 1.0
 */
@Data
public class DbDto {

    /**
     * 数据库类型
     */
    private String dbType;

    /**
     * 数据库地址
     */
    private String host;

    /**
     * 数据库端口
     */
    private Integer port;

    /**
     * 数据库名称
     */
    private String dbName;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;
}
