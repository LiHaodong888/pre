package com.xd.pre.modules.security.social.qq.config;

import com.xd.pre.modules.security.properties.PreSecurityProperties;
import com.xd.pre.modules.security.properties.QQProperties;
import com.xd.pre.modules.security.social.SocialAutoConfigurerAdapter;
import com.xd.pre.modules.security.social.qq.connection.QQConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
//import org.springframework.social.autoconfigure.SocialAutoConfigurerAdapter;
import org.springframework.social.connect.ConnectionFactory;

/**
 * 把QQ登录的自定义配置传给ConnectionFactory
 */
// 当且仅当配置文件中配置了security.social.qq.app-id时候此配置类才生效
@ConditionalOnProperty(prefix = "pre.security.social.qq",name = "app-id")
@Configuration
public class QQAutoConfig extends SocialAutoConfigurerAdapter {

    @Autowired
    private PreSecurityProperties preSecurityProperties;

    @Override
    protected ConnectionFactory<?> createConnectionFactory() {
        QQProperties qqProperties = preSecurityProperties.getSocial().getQq();
        String providerId = qqProperties.getProviderId();
        String appId = qqProperties.getAppId();
        String appSecret = qqProperties.getAppSecret();
        return new QQConnectionFactory(providerId, appId, appSecret);
    }

}
