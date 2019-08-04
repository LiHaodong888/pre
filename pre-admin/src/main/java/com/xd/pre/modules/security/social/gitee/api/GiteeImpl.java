package com.xd.pre.modules.security.social.gitee.api;

/**
 * @author huan.fu
 * @date 2018/11/26 - 18:12
 */

import cn.hutool.core.util.ObjectUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.social.oauth2.AbstractOAuth2ApiBinding;

import java.util.Map;

/**
 * git hub api operation
 *
 * @author huan.fu
 * @date 2018/11/26 - 18:12
 */
@Slf4j
public class GiteeImpl extends AbstractOAuth2ApiBinding implements Gitee {

    private static final String URL_GET_USRE_INFO = "https://gitee.com/api/v5/user";

    private String accessToken;

    public GiteeImpl(String accessToken) {
        this.accessToken = accessToken;
    }

    @Override
    public GiteeUserInfo getUserInfo() {
        Map<String, Object> user = getRestTemplate().getForObject(String.format(URL_GET_USRE_INFO + "?access_token=%s", accessToken), Map.class);
        log.info("当达到:{}", user);
        if (ObjectUtil.isNotNull(user)) {
            int id = (int) user.get("id");
            String username = String.valueOf(user.get("login"));
            String name = String.valueOf(user.get("name"));
            String avatarUrl = user.get("avatar_url") != null ? String.valueOf(user.get("avatar_url")) : null;

            String url = String.valueOf(user.get("url"));
            String htmlUrl = String.valueOf(user.get("html_url"));
            String followersUrl = String.valueOf(user.get("followers_url"));
            String followingUrl = String.valueOf(user.get("following_url"));
            String blog = user.get("blog") != null ? String.valueOf(user.get("blog")) : null;
            GiteeUserInfo userInfo = GiteeUserInfo.builder()
                    .id(id)
                    .login(username)
                    .name(name)
                    .avatarUrl(avatarUrl)
                    .url(url)
                    .htmlUrl(htmlUrl)
                    .followersUrl(followersUrl)
                    .followingUrl(followingUrl)
                    .blog(blog)
                    .build();
            log.info("Gitee userInfo : [{}]", userInfo);
            return userInfo;
        }
        return null;
    }
}
