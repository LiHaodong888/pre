package com.xd.pre.codegen.utils;


import cn.hutool.core.util.ObjectUtil;
import com.xd.pre.codegen.domain.SysDb;
import com.xd.pre.codegen.dto.DbDto;
import com.xd.pre.exception.BaseException;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


/**
 * @Classname JDBCTool
 * @Description TODO
 * @Author 李号东 lihaodongmail@163.com
 * @Date 2019-04-26 09:48
 * @Version 1.0
 */
@Slf4j
public class JDBCTool {


    public static void main(String[] args) throws SQLException {
        SysDb connParam = new SysDb("Mysql", Long.getLong("localhost"), 3306, "pre", "root", "roo");
//        boolean b = testConnection(connParam);
//        System.out.println(b);

    }

    public static boolean testConnection(DbDto connParam) {
        System.out.println(connParam);
        //获取mysql数据库的驱动类
        String driver = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://" + connParam.getHost() + ":" + connParam.getPort() + "/" + connParam.getDbName();
        Connection connection = null;
        try {
            Class.forName(driver);
            //获取连接对象
            connection = DriverManager.getConnection(url, connParam.getUsername(), connParam.getPassword());
            return true;
        } catch (ClassNotFoundException | SQLException e) {
            log.error("测试连接数据库异常:{}",e.getMessage());
            throw  new BaseException(e.getMessage()) ;
        } finally {
            try {
                if (ObjectUtil.isNotNull(connection)) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}