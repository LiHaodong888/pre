package com.xd.pre.modules.security.social.qq.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.oauth2.AbstractOAuth2ApiBinding;
import org.springframework.social.oauth2.TokenStrategy;

/**
 * 个性化api接口实现类，实现QQ获取用户信息的逻辑
 * AbstractOAuth2ApiBinding类非单例，每个用户都创建对象存储自己的accessToken走自己的认证流程
 * 所以不放入ioc中
 */
@Slf4j
public class QQImpl extends AbstractOAuth2ApiBinding implements QQ {

    // 根据access_token获取openId的url
    private static final String URL_GET_OPENID = "https://graph.qq.com/oauth2.0/me?access_token=%s";
    // oauth_consumer_key应填写appId（access_token已经由父类的restTemplate帮我们带上了所以此处去掉）
    private static final String URL_GET_USERINFO = "https://graph.qq.com/user/get_user_info?oauth_consumer_key=%s&openid=%s";

    private String appId;
    private String openId;

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * 构造函数，获取openId
     */
    public QQImpl(String accessToken, String openId) {
        // 1 把accessToken存储到父类中，TokenStrategy默认是使用父类的restTemplate发请求时把accessToken放入请求头
        super(accessToken, TokenStrategy.ACCESS_TOKEN_PARAMETER); // 把accessToken放入请求参数
        this.openId = openId;
        // 2 发请求获取openId
        String jsonString = getRestTemplate()
                .getForObject(String.format(URL_GET_OPENID, accessToken), String.class);
        // 截取返回的json字符串中openId的值 TODO 待优化
        this.openId = StringUtils.substringBetween(jsonString, "\"openid\":\"", "\"}");
    }


    /**
     * 根据accessToken、appId、openid发请求，获取用户信息
     */
    @Override
    public QQUserInfo getUserInfo(){
        String jsonString = getRestTemplate()
                .getForObject(String.format(URL_GET_USERINFO, appId, openId), String.class);
        try {
            // 把openId设置到userInfo中
            QQUserInfo qqUserInfo = objectMapper.readValue(jsonString, QQUserInfo.class);
            qqUserInfo.setOpenId(openId);
            return qqUserInfo;
        } catch (Exception e) {
            log.error("QQImpl.getUserInfo()：获取QQ用户信息失败",e);
            return null;
        }
    }

}
