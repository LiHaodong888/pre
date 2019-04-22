package com.xd.pre.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName MyBatisPlusConfig
 * @Author lihaodong
 * @Date 2019/1/10 18:02
 * @Mail lihaodongmail@163.com
 * @Description Mybatis-Plus配置
 * @Version 1.0
 **/

@Configuration
public class MyBatisPlusConfig {

    /**
     * mybatis-plus分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        return paginationInterceptor;
    }
}
