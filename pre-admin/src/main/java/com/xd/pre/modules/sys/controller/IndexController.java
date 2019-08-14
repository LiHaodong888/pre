package com.xd.pre.modules.sys.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.xd.pre.common.exception.ValidateCodeException;
import com.xd.pre.modules.security.code.img.CaptchaUtil;
import com.xd.pre.modules.security.code.sms.SmsCodeService;
import com.xd.pre.modules.security.social.PreConnectionData;
import com.xd.pre.modules.security.social.SocialRedisHelper;
import com.xd.pre.modules.security.social.SocialUserInfo;
import com.xd.pre.common.constant.PreConstant;
import com.xd.pre.modules.sys.domain.SysUser;
import com.xd.pre.modules.sys.dto.UserDTO;
import com.xd.pre.modules.sys.service.ISysUserService;
import com.xd.pre.common.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionData;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.ServletWebRequest;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @Classname IndexController
 * @Description 主页模块
 * @Author 李号东 lihaodongmail@163.com
 * @Date 2019-05-07 12:38
 * @Version 1.0
 */
@RestController
public class IndexController {

    @Autowired
    private ISysUserService userService;

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    @Autowired
    private SmsCodeService smsCodeService;

    @Autowired
    private ProviderSignInUtils providerSignInUtils;

    @Autowired
    private SocialRedisHelper socialRedisHelper;

    @Value("${pre.url.address}")
    private String url;


    /**
     * 生成验证码
     *
     * @param response
     * @param request
     * @throws ServletException
     * @throws IOException
     */
    @GetMapping("/captcha.jpg")
    public void captcha(HttpServletResponse response, HttpServletRequest request) throws IOException {
        response.setHeader("Cache-Control", "no-store, no-cache");
        response.setContentType("image/jpeg");
        // 生成图片验证码
        BufferedImage image = CaptchaUtil.createImage();
        // 生成文字验证码
        String randomText = CaptchaUtil.drawRandomText(image);
        // 保存到验证码到 redis 有效期两分钟
        String t = request.getParameter("t");
        redisTemplate.opsForValue().set(PreConstant.PRE_IMAGE_KEY + t, randomText.toLowerCase(), 2, TimeUnit.MINUTES);
        ServletOutputStream out = response.getOutputStream();
        ImageIO.write(image, "jpeg", out);
    }


    /**
     * 发送短信验证码
     *
     * @param phone
     * @return
     */
    @PostMapping("/sendCode/{phone}")
    public R sendSmsCode(@PathVariable("phone") String phone) {
        Map<String, Object> map = smsCodeService.sendCode(phone);
        // 获取状态码 00000 成功 00141 一小时内发送给单个手机次数超过限制 00142 一天内发送给单个手机次数超过限制
        String respCode = map.get("respCode").toString();
        String code = map.get("code").toString();
        if ("00141".equals(respCode) || "00142".equals(respCode)) {
            return R.error("发送次数过多,稍后再试");
        }
        // 保存到验证码到 redis 有效期两分钟
        redisTemplate.opsForValue().set(phone, code, 2, TimeUnit.MINUTES);
        return R.ok();
    }


    @PostMapping("/register")
    public R register(@RequestBody UserDTO userDTO) {
        Object redisCode = redisTemplate.opsForValue().get(userDTO.getPhone());
        if (ObjectUtil.isNull(redisCode)) {
            throw new ValidateCodeException("验证码已失效");
        }
        if (!userDTO.getSmsCode().toLowerCase().equals(redisCode)) {
            throw new ValidateCodeException("短信验证码错误");
        }
        return R.ok(userService.register(userDTO));
    }

    /**
     * 登录
     *
     * @param username
     * @param password
     * @return
     */
    @RequestMapping(value = "/login")
    public R login(String username, String password, HttpServletRequest request) {
        // 社交快速登录
        String token = request.getParameter("token");
        if (StrUtil.isNotEmpty(token)) {
            return R.ok(token);
        }
        return R.ok(userService.login(username, password));
    }

    /**
     * 保存完信息然后跳转到绑定用户信息页面
     *
     * @param request
     * @param response
     * @throws IOException
     */
    @GetMapping("/socialSignUp")
    public void socialSignUp(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String uuid = UUID.randomUUID().toString();
        SocialUserInfo userInfo = new SocialUserInfo();
        Connection<?> connectionFromSession = providerSignInUtils.getConnectionFromSession(new ServletWebRequest(request));
        userInfo.setHeadImg(connectionFromSession.getImageUrl());
        userInfo.setNickname(connectionFromSession.getDisplayName());
        userInfo.setProviderId(connectionFromSession.getKey().getProviderId());
        userInfo.setProviderUserId(connectionFromSession.getKey().getProviderUserId());
        ConnectionData data = connectionFromSession.createData();
        PreConnectionData preConnectionData = new PreConnectionData();
        BeanUtil.copyProperties(data, preConnectionData);
        socialRedisHelper.saveConnectionData(uuid, preConnectionData);
        // 跳转到用户绑定页面
        response.sendRedirect(url+"/bind?key=" + uuid);
    }

    /**
     * 社交登录绑定
     *
     * @param user
     * @return
     */
    @PostMapping("/bind")
    public R register(@RequestBody SysUser user) {
        return R.ok(userService.doPostSignUp(user));
    }


    /**
     * @Author 李号东
     * @Description 暂时这样写
     * @Date 08:12 2019-06-22
     **/
    @RequestMapping("/info")
    public R info() {
        Map<String, Object> map = new HashMap<>();
        List<String> list = new ArrayList<>();
        list.add("admin");
        map.put("roles", list);
        map.put("avatar", "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1561394014552&di=17b6c1233048e5276f48309b306c7699&imgtype=0&src=http%3A%2F%2Fb-ssl.duitang.com%2Fuploads%2Fitem%2F201804%2F29%2F20180429210111_gtsnf.jpg");
        map.put("name", "Super Admin");
        return R.ok(map);
    }

    /**
     * @Author 李号东
     * @Description 使用jwt前后分离 只需要前端清除token即可 暂时返回success
     * @Date 08:13 2019-06-22
     **/
    @RequestMapping("/logout")
    public String logout() {
        return "success";
    }
}
