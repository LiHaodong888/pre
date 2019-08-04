package com.xd.pre.modules.security.social.qq.connection;

import com.xd.pre.modules.security.social.qq.api.QQ;
import com.xd.pre.modules.security.social.qq.api.QQUserInfo;
import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.ConnectionValues;
import org.springframework.social.connect.UserProfile;

/**
 * 个性化服务提供商的数据（此处是从QQ获取的用户信息） 与 OAuth2协议的标准数据 之间的适配器
 */
public class QQAdapter implements ApiAdapter<QQ> {  //所要适配的个性化api接口

    // 测试当前api是否可用
    @Override
    public boolean test(QQ api) {
        // 此处总是认为可用
        return true;
    }

    /**
     * 把QQ的用户信息设置到ConnectionValues中，完成数据适配
     * ConnectionValues中的数据会被UsersConnectionRepository保存到数据库
      */
    @Override
    public void setConnectionValues(QQ api, ConnectionValues values) {
        QQUserInfo qqUserInfo = api.getUserInfo();
        // 昵称
        values.setDisplayName(qqUserInfo.getNickname());
        // 头像
        values.setImageUrl(qqUserInfo.getFigureurl_qq_1());
        // 个人主页url
        values.setProfileUrl(null); //qq没有
        // 服务提供商的openId
        values.setProviderUserId(qqUserInfo.getOpenId());
    }

    @Override
    public UserProfile fetchUserProfile(QQ api) {
        return null;
    }

    @Override
    public void updateStatus(QQ api, String message) {
        //QQ没有这种功能
    }



}
