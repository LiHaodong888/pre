package com.xd.pre.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Classname CorsConfig
 * @Description 跨域设置
 * @Author 李号东 lihaodongmail@163.com
 * @Date 2019-04-21 11:28
 * @Version 1.0
 */
//@Configuration
//@EnableWebMvc
public class CorsConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowCredentials(true)
                        .allowedHeaders("*")
                        .allowedOrigins("*")
                        .allowedMethods("GET","POST","PUT","DELETE");
            }
        };
    }
}
