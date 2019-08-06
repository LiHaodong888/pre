package com.xd.pre.modules.security.properties;


import lombok.Getter;
import lombok.Setter;

/**
 * @Classname QQProperties
 * @Description QQ第三方登录配置
 * @Author Created by Lihaodong (alias:小东啊) lihaodongmail@163.com
 * @Date 2019-07-08 20:16
 * @Version 1.0
 */
@Setter
@Getter
public class QQProperties extends SocialProperties{

    private String providerId = "qq";
}
