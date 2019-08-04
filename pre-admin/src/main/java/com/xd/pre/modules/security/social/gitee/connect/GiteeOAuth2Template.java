package com.xd.pre.modules.security.social.gitee.connect;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2Template;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;

/**
 * @Classname GiteeAdapter
 * @Description
 * @Author Created by Lihaodong (alias:小东啊) lihaodongmail@163.com
 * @Date 2019-07-08 21:49
 * @Version 1.0
 */
public class GiteeOAuth2Template extends OAuth2Template {

    private Logger logger = LoggerFactory.getLogger(getClass());

    public GiteeOAuth2Template(String clientId, String clientSecret, String authorizeUrl, String accessTokenUrl) {
        super(clientId, clientSecret, authorizeUrl, accessTokenUrl);
        setUseParametersForClientAuthentication(true);
    }

    @Override
    protected AccessGrant postForAccessGrant(String accessTokenUrl, MultiValueMap<String, String> parameters) {

        RestTemplate restTemplate = new RestTemplate();

        String responseStr = restTemplate.postForObject("https://gitee.com/oauth/token", parameters, String.class);
        logger.info("获取accessToke的响应：" + responseStr);
        JSONObject object = JSONObject.parseObject(responseStr);
        String accessToken = (String) object.get("access_token");
        String scope = (String) object.get("scope");
        String refreshToken = (String) object.get("refresh_token");
        int expiresIn = (Integer) object.get("expires_in");

        logger.info("获取Toke的响应:{},scope响应:{},refreshToken响应:{},expiresIn响应:{}", accessToken, scope, refreshToken, expiresIn);
        return new AccessGrant(accessToken, scope, refreshToken, (long) expiresIn);
    }

    @Override
    protected RestTemplate createRestTemplate() {
        RestTemplate restTemplate = super.createRestTemplate();
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter(Charset.forName("UTF-8")));
        return restTemplate;
    }

}
