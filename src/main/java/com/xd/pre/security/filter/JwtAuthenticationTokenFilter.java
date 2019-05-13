package com.xd.pre.security.filter;

import cn.hutool.core.util.ObjectUtil;
import com.xd.pre.security.SecurityUser;
import com.xd.pre.security.util.JwtUtil;
import com.xd.pre.service.ISysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * @Author 李号东
 * @Description token过滤器来验证token有效性 引用的stackoverflow一个答案里的处理方式
 * @Date 00:32 2019-03-17
 * @Param
 * @return
 **/
@Slf4j
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private ISysUserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {

//        String[] allowDomains = {"http://localhost:9528", "http://localhost:8080","*"};
//        Set<String> allowOrigins = new HashSet<>(Arrays.asList(allowDomains));
//        String originHeads = request.getHeader("Origin");
//        if (allowOrigins.contains(originHeads)) {
//            //设置允许跨域的配置
//            // 这里填写你允许进行跨域的主机ip（正式上线时可以动态配置具体允许的域名和IP）
//            response.setHeader("Access-Control-Allow-Origin", originHeads);
//        }
//        // 设置服务器允许浏览器发送请求都携带cookie
//        response.setHeader("Access-Control-Allow-Credentials", "true");
//        // 允许的访问方法
//        response.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE, PATCH");
//        // Access-Control-Max-Age 用于 CORS 相关配置的缓存
//        response.setHeader("Access-Control-Max-Age", "3600");
//        response.setHeader("Access-Control-Allow-Headers", "token,Origin, X-Requested-With, Content-Type, Accept,mid,Authorization");
//        response.setCharacterEncoding("UTF-8");

        SecurityUser securityUser = jwtUtil.getUserFromToken(request);
        if (ObjectUtil.isNotNull(securityUser)){
            Set<String> permissions = userService.findPermsByUserId(securityUser.getUserId());
            Collection<? extends GrantedAuthority> authorities = AuthorityUtils.createAuthorityList(permissions.toArray(new String[0]));
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(securityUser, null, authorities);
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
        chain.doFilter(request, response);
    }
}
