package com.xd.pre.config;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.db.Db;
import cn.hutool.db.Entity;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.PluginUtils;
import com.baomidou.mybatisplus.extension.handlers.AbstractSqlParserHandler;
import com.xd.pre.domain.SysDept;
import com.xd.pre.exception.CheckedException;
import com.xd.pre.handler.DataScopeTypeEnum;
import com.xd.pre.security.PreUser;
import com.xd.pre.security.util.SecurityUtil;
import com.xd.pre.service.ISysRoleDeptService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import sun.security.util.SecurityConstants;

import javax.sql.DataSource;
import java.sql.Connection;
import java.text.DateFormat;
import java.util.*;
import java.util.stream.Collectors;

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


    private DataSource dataSource;

//    @Autowired
//    private ISysRoleDeptService sysRoleDeptService;

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
        log.info("执行的SQL参数[{}]", parameterObject);


        if (parameterObject instanceof Map) {
            for (Object val : ((Map<?, ?>) parameterObject).values()) {
                System.out.println(val.toString());
                if (val instanceof LambdaQueryWrapper){

                    System.out.println(((LambdaQueryWrapper) val).isEmptyOfWhere());
                    System.out.println(((LambdaQueryWrapper) val).getEntity());
                }
            }
        }

        String parameterValue = getParameterValue(parameterObject);

        log.info("解析后的参数:{}",parameterValue);
        //查找参数中包含DataScope类型的参数
        DataScope dataScope = findDataScopeObject(parameterObject);

        if (ObjectUtil.isNull(dataScope)) {
            return invocation.proceed();
        }

        String scopeName = dataScope.getScopeName();
        System.out.println(scopeName);
        // 优先获取赋值数据
//        if (CollUtil.isEmpty(scopeName)) {
//            PreUser user = SecurityUtil.getUser();
//            if (user == null) {
//                throw new CheckedException("auto datascope, set up security details true");
//            }
//
////            List<String> roleIdList = user.getAuthorities()
////                    .stream().map(GrantedAuthority::getAuthority)
////                    .filter(authority -> authority.startsWith("ROLE_"))
////                    .map(authority -> authority.split("_")[1])
////                    .collect(Collectors.toList());
//            List<String> roleIdList = new ArrayList<>();
//            roleIdList.add("5");
//            roleIdList.add("7");
//
//
//
//
//            // 通过角色Id查询范围权限
//            Entity query = Db.use(dataSource)
//                    .query("SELECT * FROM sys_role where role_id IN (" + CollUtil.join(roleIdList, ",") + ")")
//                    .stream().min(Comparator.comparingInt(o -> o.getInt("data_scope"))).get();
//
//            // 数据库权限范围字段
//            Integer dsType = query.getInt("data_scope");
//
//            System.out.println(dsType);
//            // 查询全部
////            if (DataScopeTypeEnum.ALL.getType() == dsType) {
////                return invocation.proceed();
////            }
////            // 自定义
////            if (DataScopeTypeEnum.CUSTOMIZE.getType() == dsType) {
////                String dsScope = query.getStr("data_scope");
////                deptIds.addAll(sysRoleDeptService.getRoleDeptIds());
////            }
////            // 查询本级及其下级
////            if (DataScopeTypeEnum.THIS_LEVEL_CHILDREN.getType() == dsType) {
////                List<Integer> deptIdList = Db.use(dataSource)
////                        .findBy("sys_dept_relation", "ancestor", user.getDeptId())
////                        .stream().map(entity -> entity.getInt("descendant"))
////                        .collect(Collectors.toList());
////                deptIds.addAll(deptIdList);
////            }
////            // 只查询本级
////            if (DataScopeTypeEnum.THIS_LEVEL.getType() == dsType) {
////                deptIds.add(user.getDeptId());
////            }
//            return invocation.proceed();
//        }



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

    /**
     * mybatis配置的属性
     *
     * @param properties mybatis配置的属性
     */
    @Override
    public void setProperties(Properties properties) {

    }

    /**
     * 查找参数是否包括DataScope对象
     *
     * @param parameterObj 参数列表
     * @return DataScope
     */
    private DataScope findDataScopeObject(Object parameterObj) {

        if (parameterObj instanceof DataScope) {
            return (DataScope) parameterObj;
        } else if (parameterObj instanceof Map) {
            for (Object val : ((Map<?, ?>) parameterObj).values()) {
                if (val instanceof DataScope) {
                    return (DataScope) val;
                }
            }
        }
        return null;
    }

    private String getParameterValue(Object obj) {
        String value = null;
        if (obj instanceof String) {
            value = "'" + obj.toString() + "'";
        } else if (obj instanceof Date) {
            DateFormat formatter = DateFormat.getDateTimeInstance(DateFormat.DEFAULT, DateFormat.DEFAULT, Locale.CHINA);
            value = "'" + formatter.format(obj) + "'";
        } else {
            if (obj != null) {
                value = obj.toString();
            } else {
                value = "";
            }
        }
        return value;
    }
}
