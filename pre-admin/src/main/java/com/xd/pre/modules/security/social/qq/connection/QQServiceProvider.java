package com.xd.pre.modules.security.social.qq.connection;

import com.xd.pre.modules.security.social.qq.api.QQ;
import com.xd.pre.modules.security.social.qq.api.QQImpl;
import org.springframework.social.oauth2.AbstractOAuth2ServiceProvider;

/**
 *  构建QQ登录的 ServiceProvider
 *  由api（AbstractOAuth2ApiBinding实现类） 和 OAuth2Operations（OAuth2Template） 组成
 */
public class QQServiceProvider extends AbstractOAuth2ServiceProvider<QQ> {  // 个性化api接口

    private String appId; // 由构造函数赋值，并传入getApi()中

    // 获取授权码地址
    private static final String URL_AUTHORIZE = "https://graph.qq.com/oauth2.0/authorize";
    // 由授权码获取accessToken的地址
    private static final String URL_ACCESS_TOKEN = "https://graph.qq.com/oauth2.0/token";

    /**
     * 创建一个 ServiceProvider，必须提供构造函数
     * 使用security提供的 OAuth2Template 作为 OAuth2Operations 的 实现类
     * @param appId 具体应用不同
     * @param appSecret 具体应用不同d
     */
    public QQServiceProvider(String appId,String appSecret) {
        // 自定义获取授权码后RestTemplate的解析逻辑
        // appId,appSecret,authorizeUrl,accessTokenUrl
        super(new QQOAuth2Template(appId,appSecret,URL_AUTHORIZE,URL_ACCESS_TOKEN));
        this.appId = appId;
    }

    /**
     * 使用自定义的 QQImpl 作为 AbstractOAuth2ApiBinding 的实现类
     * @param accessToken
     * @return
     */
    @Override
    public QQ getApi(String accessToken) {
        return new QQImpl(accessToken,appId);
    }

}
