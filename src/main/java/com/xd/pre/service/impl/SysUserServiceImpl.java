package com.xd.pre.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xd.pre.config.DataScope;
import com.xd.pre.constant.PreConstant;
import com.xd.pre.domain.SysUser;
import com.xd.pre.domain.SysUserRole;
import com.xd.pre.dto.UserDTO;
import com.xd.pre.exception.BaseException;
import com.xd.pre.mapper.SysUserMapper;
import com.xd.pre.security.PreUser;
import com.xd.pre.security.util.JwtUtil;
import com.xd.pre.service.*;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xd.pre.utils.PreUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author lihaodong
 * @since 2019-04-21
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {

    @Autowired
    private ISysUserRoleService userRoleService;

    @Autowired
    private ISysDeptService deptService;
    @Autowired
    private ISysJobService jobService;

    @Autowired
    private ISysMenuService menuService;

    @Resource
    private AuthenticationManager authenticationManager;

    @Autowired
    RedisTemplate redisTemplate;

    @Override
    public IPage<SysUser> getUsersWithRolePage(Page page, UserDTO userDTO) {

        if (ObjectUtil.isNotNull(userDTO) && userDTO.getDeptId() != 0){
            userDTO.setDeptList(deptService.selectDeptIds(userDTO.getDeptId()));
        }
        return baseMapper.getUserVosPage(page, userDTO, new DataScope());
    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean insertUser(UserDTO userDto) {
        SysUser sysUser = new SysUser();
        BeanUtils.copyProperties(userDto, sysUser);
        // 默认密码
        sysUser.setPassword(PreUtil.encode("123456"));
        baseMapper.insertUser(sysUser);
        List<SysUserRole> userRoles = userDto.getRoleList().stream().map(item -> {
            SysUserRole sysUserRole = new SysUserRole();
            sysUserRole.setRoleId(item);
            sysUserRole.setUserId(sysUser.getUserId());
            return sysUserRole;
        }).collect(Collectors.toList());
        return userRoleService.saveBatch(userRoles);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateUser(UserDTO userDto) {
        SysUser sysUser = new SysUser();
        BeanUtils.copyProperties(userDto, sysUser);
        baseMapper.updateById(sysUser);
        userRoleService.remove(Wrappers.<SysUserRole>lambdaQuery().eq(SysUserRole::getUserId, sysUser.getUserId()));
        List<SysUserRole> userRoles = new ArrayList<>();
        userDto.getRoleList().forEach(item -> {
            SysUserRole sysUserRole = new SysUserRole();
            sysUserRole.setRoleId(item);
            sysUserRole.setUserId(sysUser.getUserId());
            userRoles.add(sysUserRole);
        });
        return userRoleService.saveBatch(userRoles);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean removeUser(Integer userId) {
        baseMapper.deleteById(userId);
        return userRoleService.remove(Wrappers.<SysUserRole>lambdaQuery().eq(SysUserRole::getUserId, userId));
    }

    @Override
    public boolean restPass(Integer userId) {
        return baseMapper.updateById(new SysUser().setPassword("12345678").setUserId(userId)) > 0;
    }

    @Override
    public SysUser findByUserName(String username) {
        return baseMapper.selectOne(Wrappers.<SysUser>lambdaQuery()
                .select(SysUser::getUserId, SysUser::getUsername, SysUser::getPassword)
                .eq(SysUser::getUsername, username));
    }

    @Override
    public SysUser findByUserInfoName(String username) {
        SysUser sysUser = baseMapper.selectOne(Wrappers.<SysUser>lambdaQuery()
                .select(SysUser::getUserId, SysUser::getUsername, SysUser::getPhone, SysUser::getEmail, SysUser::getPassword, SysUser::getDeptId, SysUser::getJobId, SysUser::getAvatar)
                .eq(SysUser::getUsername, username));
        // 获取部门
        sysUser.setDeptName(deptService.selectDeptNameByDeptId(sysUser.getDeptId()));
        // 获取岗位
        sysUser.setJobName(jobService.selectJobNameByJobId(sysUser.getJobId()));
        return sysUser;
    }

    @Override
    public Set<String> findPermsByUserId(Integer userId) {
        return menuService.findPermsByUserId(userId).stream().filter(StringUtils::isNotEmpty).collect(Collectors.toSet());
    }

    @Override
    public Set<String> findRoleIdByUserId(Integer userId) {
        return userRoleService
                .selectUserRoleListByUserId(userId)
                .stream()
                .map(sysUserRole -> "ROLE_" + sysUserRole.getRoleId())
                .collect(Collectors.toSet());
    }

    @Override
    public String login(String username, String password, String captcha, HttpServletRequest request) {
        // 验证验证码
        // 从session中获取之前保存的验证码跟前台传来的验证码进行匹配
        // 线上可以存放在redis中
//        Object kaptcha = request.getSession().getAttribute(PreConstant.PRE_IMAGE_SESSION_KEY);
        Object kaptcha = redisTemplate.opsForValue().get(PreConstant.PRE_IMAGE_SESSION_KEY);
        if (kaptcha == null) {
            throw new BaseException("验证码已失效");
        }
        if (!captcha.toLowerCase().equals(kaptcha)) {
            throw new BaseException("验证码错误");
        }
        //用户验证
        Authentication authentication = null;
        try {
            // 该方法会去调用UserDetailsServiceImpl.loadUserByUsername()去验证用户名和密码，
            // 如果正确，则存储该用户名密码到security 的 context中
            authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (Exception e) {
            if (e instanceof BadCredentialsException) {
                throw new BaseException("用户名或密码错误", 402);
            } else if (e instanceof DisabledException) {
                throw new BaseException("账户被禁用", 402);
            } else if (e instanceof AccountExpiredException) {
                throw new BaseException("账户过期无法验证", 402);
            } else {
                throw new BaseException("账户被锁定,无法登录", 402);
            }
        }
        //存储认证信息
        SecurityContextHolder.getContext().setAuthentication(authentication);
        //生成token
        PreUser userDetail = (PreUser) authentication.getPrincipal();
        return JwtUtil.generateToken(userDetail);
    }

    @Override
    public boolean updateUserInfo(SysUser sysUser) {
        return baseMapper.updateById(sysUser) > 0;
    }
}
