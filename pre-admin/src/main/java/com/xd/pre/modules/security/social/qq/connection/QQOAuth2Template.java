package com.xd.pre.modules.security.social.qq.connection;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2Template;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;

/**
 * 自定义获取授权码后RestTemplate的解析逻辑
 * 因为QQ授权登录后返回的是字符串，而security的解析的RestTemplate默认使用json解析成Map再获取对应字段的值
 */
@Slf4j
public class QQOAuth2Template extends OAuth2Template {

    public QQOAuth2Template(String clientId, String clientSecret, String authorizeUrl, String accessTokenUrl) {
        super(clientId, clientSecret, authorizeUrl, accessTokenUrl);
    }

    /**
     * 往RestTemplate中加入String类型的解析器
     */
    @Override
    protected RestTemplate createRestTemplate() {
        RestTemplate restTemplate = super.createRestTemplate();
        restTemplate.getMessageConverters()
                .add(new StringHttpMessageConverter(Charset.forName("UTF-8")));
        return restTemplate;
    }

    /**
     * 自定义授权成功返回结果的解析逻辑
     */
    @Override
    protected AccessGrant postForAccessGrant(String accessTokenUrl, MultiValueMap<String, String> parameters) {
        String responseStr = getRestTemplate().postForObject(accessTokenUrl, parameters, String.class);
        log.info("从QQ获取accessToken的响应：" + responseStr);
        // 相邻的分隔符被视为空标记的分隔符
        String[] items = StringUtils.splitByWholeSeparatorPreserveAllTokens(responseStr, "&");
        // 获取返回结果的各个字段 并传给security
        String accessToken = StringUtils.substringAfterLast(items[0], "=");
        String refreshToken = StringUtils.substringAfterLast(items[1], "=");
        Long expiresIn = Long.parseLong(StringUtils.substringAfterLast(items[2], "="));

        return new AccessGrant(accessToken, null,refreshToken,expiresIn);
    }
}
