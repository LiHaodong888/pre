package com.xd.pre.security;

import cn.hutool.core.util.ObjectUtil;
import com.xd.pre.domain.SysUser;
import com.xd.pre.service.ISysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Set;

/**
 * @Classname UserDetailsServiceImpl
 * @Description 身份验证
 * @Author 李号东 lihaodongmail@163.com
 * @Date 2019-05-07 20:30
 * @Version 1.0
 */
@Slf4j
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private ISysUserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        SysUser user = userService.findByUserName(username);
        if (ObjectUtil.isNull(user)) {
            log.info("登录用户：" + username + " 不存在.");
            throw new UsernameNotFoundException("登录用户：" + username + " 不存在");
        }
        // 获取用户拥有的角色
        // 用户权限列表，根据用户拥有的权限标识与如 @PreAuthorize("hasAuthority('sys:menu:view')") 标注的接口对比，决定是否可以调用接口
        // 权限集合
        Set<String> permissions = userService.findPermsByUserId(user.getUserId());
        // 角色集合
        Set<String> roleIds = userService.findRoleIdByUserId(user.getUserId());
        permissions.addAll(roleIds);
        Collection<? extends GrantedAuthority> authorities = AuthorityUtils.createAuthorityList(permissions.toArray(new String[0]));
        return new PreUser(user.getUserId(), username, user.getPassword(), authorities);
    }
}
