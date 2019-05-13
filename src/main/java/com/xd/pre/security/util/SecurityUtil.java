package com.xd.pre.security.util;

import com.alibaba.fastjson.JSON;
import com.xd.pre.exception.BaseException;
import com.xd.pre.security.SecurityUser;
import com.xd.pre.utils.R;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @Classname SecurityUtil
 * @Description 安全服务工具类
 * @Author 李号东 lihaodongmail@163.com
 * @Date 2019-05-08 10:12
 * @Version 1.0
 */
@Component
public class SecurityUtil {


    @Resource
    private AuthenticationManager authenticationManager;

    /**
     * 验证用户
     * @param username
     * @param password
     * @return
     */
    public Authentication authenticate(String username, String password) {
        try {
            // 该方法会去调用UserDetailsServiceImpl.loadUserByUsername()去验证用户名和密码，
            // 如果正确，则存储该用户名密码到security 的 context中
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (Exception e ) {
            if (e instanceof BadCredentialsException){
                throw new BaseException("用户名或密码错误",402);
            } else if (e instanceof DisabledException){
                throw new BaseException("账户被禁用",402);
            } else if (e instanceof AccountExpiredException){
                throw new BaseException("账户过期无法验证",402);
            } else {
                throw new BaseException("账户被锁定,无法登录",402);
            }
        }
    }


    public static void writeJavaScript(R r, HttpServletResponse response) throws IOException {
        response.setStatus(200);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        PrintWriter printWriter = response.getWriter();
        printWriter.write(JSON.toJSONString(r));
        printWriter.flush();
    }

    /**
     * 获取Authentication
     */
    private Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    /**
     * @Author 李号东
     * @Description 获取用户
     * @Date 11:29 2019-05-10
     **/
    public SecurityUser getSecurityUser(){
        try {
            return (SecurityUser) getAuthentication().getPrincipal();
        } catch (Exception e) {
            throw new BaseException("登录状态过期", HttpStatus.UNAUTHORIZED.value());
        }
    }
}
