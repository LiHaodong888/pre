package com.xd.pre.modules.security.config;

import com.xd.pre.modules.security.UserDetailsServiceImpl;
import com.xd.pre.modules.security.code.img.ImageCodeFilter;
import com.xd.pre.modules.security.code.sms.SmsCodeAuthenticationSecurityConfig;
import com.xd.pre.modules.security.code.sms.SmsCodeFilter;
import com.xd.pre.modules.security.filter.PreJwtAuthenticationTokenFilter;
import com.xd.pre.modules.security.handle.PreAuthenticationEntryPointImpl;
import com.xd.pre.modules.security.handle.PreAuthenticationFailureHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.social.security.SpringSocialConfigurer;

/**
 * @Classname WebSecurityConfig
 * @Description Security配置类
 * @Author 李号东 lihaodongmail@163.com
 * @Date 2019-05-07 09:10
 * @Version 1.0
 */
@Slf4j
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class PreWebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private PreAuthenticationEntryPointImpl unauthorizedHandler;

    @Autowired
    private PreAuthenticationFailureHandler preAuthenticationFailureHandler;

    @Autowired
    private PreJwtAuthenticationTokenFilter preJwtAuthenticationTokenFilter;

    @Autowired
    private ImageCodeFilter imageCodeFilter;

    @Autowired
    private SmsCodeFilter smsCodeFilter;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    // 注入短信登录的相关配置
    @Autowired
    private SmsCodeAuthenticationSecurityConfig smsCodeAuthenticationSecurityConfig;

    @Autowired
    private SpringSocialConfigurer springSocialConfigurer;

    /**
     * 解决 无法直接注入 AuthenticationManager
     *
     * @return
     * @throws Exception
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


    /**
     * 配置策略
     *
     * @param httpSecurity
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {

        imageCodeFilter.setAuthenticationFailureHandler(preAuthenticationFailureHandler);
        httpSecurity
                // 由于使用的是JWT，我们这里不需要csrf
                .csrf().disable()
                // 短信登录配置
                .apply(smsCodeAuthenticationSecurityConfig).and()
                .apply(springSocialConfigurer).and()
                // 认证失败处理类
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
                // 基于token，所以不需要session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                // 过滤请求
                .authorizeRequests()
                // 对于登录login 图标 要允许匿名访问
                .antMatchers("/login/**", "/mobile/login/**", "/favicon.ico","/socialSignUp","/bind","/register/**").anonymous()
                .antMatchers(HttpMethod.GET,"/*.html","/**/*.html","/**/*.css","/**/*.js")
                .permitAll()
                .antMatchers("/captcha.jpg").anonymous()
                .antMatchers("/sendCode/**").anonymous()
                .antMatchers("/tenant/list").anonymous()
                .antMatchers("/tenant/setting/**")
                .permitAll()
                // 访问/user 需要拥有admin权限
                //  .antMatchers("/user").hasAuthority("ROLE_ADMIN")
                // 除上面外的所有请求全部需要鉴权认证
                .anyRequest().authenticated()
                .and()
                .headers().frameOptions().disable();
        // 添加JWT filter 用户名登录
        httpSecurity
                // 添加图形证码校验过滤器
//                .addFilterBefore(imageCodeFilter, UsernamePasswordAuthenticationFilter.class)
                // 添加JWT验证过滤器
                .addFilterBefore(preJwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class)
                // 添加短信验证码过滤器
                .addFilterBefore(smsCodeFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                // 设置UserDetailsService
                .userDetailsService(userDetailsService)
                // 使用BCrypt进行密码的hash
                .passwordEncoder(passwordEncoder());
    }

    /**
     * 装载BCrypt密码编码器 密码加密
     *
     * @return
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

