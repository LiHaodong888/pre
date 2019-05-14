package com.xd.pre.controller;

import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;
import com.xd.pre.exception.BaseException;
import com.xd.pre.service.ISysUserService;
import com.xd.pre.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Classname IndexController
 * @Description TODO
 * @Author 李号东 lihaodongmail@163.com
 * @Date 2019-05-07 12:38
 * @Version 1.0
 */
@RestController
public class IndexController {

    @Autowired
    private Producer producer;

    @Autowired
    private ISysUserService userService;


    /**
     * 生成二维码
     * @param response
     * @param request
     * @throws ServletException
     * @throws IOException
     */
    @GetMapping("captcha.jpg")
    public void captcha(HttpServletResponse response, HttpServletRequest request) throws ServletException, IOException {
        response.setHeader("Cache-Control", "no-store, no-cache");
        response.setContentType("image/jpeg");
        // 生成文字验证码
        String text = producer.createText();
        // 生成图片验证码
        BufferedImage image = producer.createImage(text);
        // 保存到验证码到 session
        request.getSession().setAttribute(Constants.KAPTCHA_SESSION_KEY, text);
        ServletOutputStream out = response.getOutputStream();
        ImageIO.write(image, "jpg", out);
    }

    /**
     * 登录
     * @param username
     * @param password
     * @param request
     * @return
     */
    @RequestMapping(value = "/login")
    public R login(String username,String password,String captcha, HttpServletRequest request) {
        return R.ok(userService.login(username, password,captcha,request));
    }


    @GetMapping("/getuser")
    @PreAuthorize("hasAuthority('sys:dept:update')")
    public R getUserDetails() {
        UserDetails userDetails = null;
        try {
            userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        } catch (Exception e) {
            throw new BaseException("登录状态过期",HttpStatus.UNAUTHORIZED.value());
        }
        return R.ok(userDetails.getAuthorities());
    }

    @RequestMapping("/info")
    public R info() {
        Map<String, Object> map = new HashMap<>();
        List<String> list = new ArrayList<>();
        list.add("admin");
        map.put("roles", list);
        map.put("token", "admin");
        map.put("avatar", "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
        map.put("name", "Super Admin");
        return R.ok(map);
    }
}
