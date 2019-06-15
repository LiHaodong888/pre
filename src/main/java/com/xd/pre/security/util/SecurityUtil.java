package com.xd.pre.security.util;

import com.alibaba.fastjson.JSON;
import com.xd.pre.exception.BaseException;
import com.xd.pre.security.PreUser;
import com.xd.pre.utils.R;
import lombok.experimental.UtilityClass;
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
@UtilityClass
public class SecurityUtil {

    /**
     * 获取用户
     *
     * @param authentication
     * @return PigxUser
     * <p>
     * 获取当前用户的全部信息 EnablePigxResourceServer true
     * 获取当前用户的用户名 EnablePigxResourceServer false
     */
    private PreUser getUser(Authentication authentication) {
        Object principal = authentication.getPrincipal();
        if (principal instanceof PreUser) {
            return (PreUser) principal;
        }
        return null;
    }

    public void writeJavaScript(R r, HttpServletResponse response) throws IOException {
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
    public PreUser getUser(){
        try {
            return (PreUser) getAuthentication().getPrincipal();
        } catch (Exception e) {
            throw new BaseException("登录状态过期", HttpStatus.UNAUTHORIZED.value());
        }
    }
}
