package com.xd.pre.config;

import com.baomidou.mybatisplus.core.toolkit.PluginUtils;
import com.baomidou.mybatisplus.extension.handlers.AbstractSqlParserHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.util.Map;
import java.util.Properties;

/**
 * @Classname DataScopeInterceptor
 * @Description Mybatis 拦截器 主要用于数据权限拦截
 * @Author Created by LiHaodong (alias:小东啊) lihaodongmail@163.com
 * @Date 2019-06-08 10:29
 * @Version 1.0
 */
@Slf4j
@Intercepts({@Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class, Integer.class})})
@Component
public class DataScopeInterceptor extends AbstractSqlParserHandler implements Interceptor {
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        if (log.isInfoEnabled()) {
            log.info("进入 DataScopeInterceptor 拦截器...");
        }
        StatementHandler statementHandler = PluginUtils.realTarget(invocation.getTarget());
        MetaObject metaObject = SystemMetaObject.forObject(statementHandler);
        this.sqlParser(metaObject);
        // 先判断是不是SELECT操作 不是直接过滤
        MappedStatement mappedStatement = (MappedStatement) metaObject.getValue("delegate.mappedStatement");
        if (!SqlCommandType.SELECT.equals(mappedStatement.getSqlCommandType())) {
            log.info("不是SELECT操作");
            return invocation.proceed();
        }
        log.info("是SELECT操作,走判断流程");
        BoundSql boundSql = (BoundSql) metaObject.getValue("delegate.boundSql");
        // 执行的SQL语句
        String originalSql = boundSql.getSql();
        // SQL语句的参数
        Object parameterObject = boundSql.getParameterObject();

        log.info("执行的SQL语句[{}]", originalSql);
        log.info("执行的SQL语句[{}]", parameterObject.toString());
        if (parameterObject instanceof Map) {
            for (Object val : ((Map<?, ?>) parameterObject).values()) {
                System.out.println(val.toString());
            }
        }

        //查找参数中包含DataScope类型的参数


        return invocation.proceed();
    }

    /**
     * 生成拦截对象的代理
     *
     * @param target 目标对象
     * @return 代理对象
     */
    @Override
    public Object plugin(Object target) {
        if (target instanceof StatementHandler) {
            return Plugin.wrap(target, this);
        }
        return target;
    }

    @Override
    public void setProperties(Properties properties) {

    }

    /**
     * 查找参数是否包括DataScope对象
     *
     * @param parameterObj 参数列表
     * @return DataScope
     */
//    private DataScope findDataScopeObject(Object parameterObj) {
//        if (parameterObj instanceof DataScope) {
//            return (DataScope) parameterObj;
//        } else if (parameterObj instanceof Map) {
//            for (Object val : ((Map<?, ?>) parameterObj).values()) {
//                if (val instanceof DataScope) {
//                    return (DataScope) val;
//                }
//            }
//        }
//        return null;
//    }
}
