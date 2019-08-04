package com.xd.pre.modules.security.social.qq.connection;

import com.xd.pre.modules.security.social.qq.api.QQ;
import org.springframework.social.connect.support.OAuth2ConnectionFactory;

/**
 * 构建QQ登录的 ConnectionFactory
 * 由 ServiceProvider 和 ApiAdapter 组成
 */
public class QQConnectionFactory extends OAuth2ConnectionFactory<QQ> {

    /**
     * 创建一个 ConnectionFactory，必须提供构造函数
     * @param providerId  提供者ID，例如“facebook”
     */
    public QQConnectionFactory(String providerId, String appId, String appSecret) {
        // 使用自定义的serviceProvider和apiAdapter
        super(providerId, new QQServiceProvider(appId, appSecret), new QQAdapter());
    }

}
