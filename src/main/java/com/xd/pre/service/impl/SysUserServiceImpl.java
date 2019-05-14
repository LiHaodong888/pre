package com.xd.pre.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.code.kaptcha.Constants;
import com.xd.pre.domain.SysUser;
import com.xd.pre.domain.SysUserRole;
import com.xd.pre.dto.UserDto;
import com.xd.pre.exception.BaseException;
import com.xd.pre.mapper.SysUserMapper;
import com.xd.pre.security.SecurityUser;
import com.xd.pre.security.UserDetailsServiceImpl;
import com.xd.pre.security.util.JwtUtil;
import com.xd.pre.security.util.SecurityUtil;
import com.xd.pre.service.*;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

    @Autowired
    private SecurityUtil securityUtil;

    @Override
    public IPage<SysUser> selectUserList(int page, int pageSize, Integer deptId) {
        Page<SysUser> userPage = new Page<>(page, pageSize);
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("user_id", "username", "dept_id", "job_id", "email", "phone", "avatar", "lock_flag");
        if (deptId != 0) {
            queryWrapper.lambda().eq(SysUser::getDeptId, deptId);
        }
        IPage<SysUser> sysUserIPage = baseMapper.selectPage(userPage, queryWrapper);
        List<SysUser> sysUserList = sysUserIPage.getRecords();

        sysUserList.forEach(sysUser -> {
            // 获取角色
            sysUser.setUserRoles(userRoleService.selectUserRoleListByUserId(sysUser.getUserId()));
            // 获取部门
            sysUser.setDeptName(deptService.selectDeptNameByDeptId(sysUser.getDeptId()));
            // 岗位
            sysUser.setJobName(jobService.selectJobNameByJobId(sysUser.getJobId()));
        });
        return sysUserIPage.setRecords(sysUserList);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean insertUser(UserDto userDto) {
        SysUser sysUser = new SysUser();
        BeanUtils.copyProperties(userDto, sysUser);
        // 默认密码
        sysUser.setPassword("123456");
        baseMapper.insertUser(sysUser);
        List<SysUserRole> userRoles = new ArrayList<>();
        userDto.getUserRoles().forEach(item -> {
            SysUserRole sysUserRole = new SysUserRole();
            sysUserRole.setRoleId(item);
            sysUserRole.setUserId(sysUser.getUserId());
            userRoles.add(sysUserRole);
        });
        return userRoleService.saveBatch(userRoles);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateUser(UserDto userDto) {
        SysUser sysUser = new SysUser();
        BeanUtils.copyProperties(userDto, sysUser);
        baseMapper.updateById(sysUser);
        userRoleService.remove(Wrappers.<SysUserRole>lambdaQuery().eq(SysUserRole::getUserId, sysUser.getUserId()));
        List<SysUserRole> userRoles = new ArrayList<>();
        userDto.getUserRoles().forEach(item -> {
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
        return baseMapper.selectOne(Wrappers.<SysUser>lambdaQuery().select(SysUser::getUserId,SysUser::getUsername,SysUser::getPassword).eq(SysUser::getUsername,username));
    }

    @Override
    public Set<String> findPermsByUserId(Integer userId) {
        return menuService.findPermsByUserId(userId).stream().filter(StringUtils::isNotEmpty).collect(Collectors.toSet());
    }

    @Override
    public String login(String username, String password,String captcha,HttpServletRequest request) {
        // 验证验证码
        // 从session中获取之前保存的验证码跟前台传来的验证码进行匹配
        Object kaptcha = request.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY);
        if(kaptcha == null){
            throw new BaseException("验证码已失效");
        }
        if (!captcha.toLowerCase().equals(kaptcha)){
            throw new BaseException("验证码错误");
        }
        //用户验证
        Authentication authentication = securityUtil.authenticate(username, password);
        //存储认证信息
        SecurityContextHolder.getContext().setAuthentication(authentication);
        //生成token
        SecurityUser userDetail = (SecurityUser) authentication.getPrincipal();
        return JwtUtil.generateToken(userDetail);
    }



}
