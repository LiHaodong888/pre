package com.xd.pre.controller;

import com.xd.pre.constant.PreConstant;
import com.xd.pre.service.ISysUserService;
import com.xd.pre.utils.PreUtil;
import com.xd.pre.utils.R;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
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
import java.util.concurrent.TimeUnit;

/**
 * @Classname IndexController
 * @Description TODO
 * @Author 李号东 lihaodongmail@163.com
 * @Date 2019-05-07 12:38
 * @Version 1.0
 */
@Api(value="主页模块")
@RestController
public class IndexController {

    @Autowired
    private ISysUserService userService;

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    /**
     * 生成验证码
     *
     * @param response
     * @param request
     * @throws ServletException
     * @throws IOException
     */
    @GetMapping("captcha.jpg")
    public void captcha(HttpServletResponse response, HttpServletRequest request) throws IOException {
        response.setHeader("Cache-Control", "no-store, no-cache");
        response.setContentType("image/jpeg");
        // 生成图片验证码
        BufferedImage image = PreUtil.createImage();
        // 生成文字验证码
        String randomText = PreUtil.drawRandomText(image);
        // 保存到验证码到 redis 有效期两分钟
        redisTemplate.opsForValue().set(PreConstant.PRE_IMAGE_SESSION_KEY, randomText.toLowerCase(),2, TimeUnit.MINUTES);
        ServletOutputStream out = response.getOutputStream();
        ImageIO.write(image, "jpeg", out);
    }

    /**
     * 登录
     *
     * @param username
     * @param password
     * @param request
     * @return
     */
    @RequestMapping(value = "/login")
    public R login(String username, String password, String captcha, HttpServletRequest request) {
        return R.ok(userService.login(username, password, captcha, request));
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

    @RequestMapping("/logout")
    public String logout(){
        return "success";
    }
}
