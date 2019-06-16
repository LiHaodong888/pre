package com.xd.pre.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xd.pre.config.DataScope;
import com.xd.pre.domain.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xd.pre.dto.UserDTO;
import org.apache.ibatis.annotations.Param;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Set;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author lihaodong
 * @since 2019-04-21
 */
public interface ISysUserService extends IService<SysUser> {

    /**
     * 分页查询用户信息（含有角色信息）
     *
     * @param page    分页对象
     * @param userDTO 参数列表
     * @return
     */
    IPage<SysUser> getUsersWithRolePage(Page page, UserDTO userDTO);

    /**
     * 保存用户以及角色部门等信息
     * @param userDto
     * @return
     */
    boolean insertUser(UserDTO userDto);

    /**
     * 更新用户以及角色部门等信息
     * @param userDto
     * @return
     */
    boolean updateUser(UserDTO userDto);

    /**
     * 删除用户信息
     * @param userId
     * @return
     */
    boolean removeUser(Integer userId);

    /**
     * 重置密码
     * @param userId
     * @return
     */
    boolean restPass(Integer userId);

    /**
     * 通过用户名查找用户
     *
     * @param username 用户名
     * @return 用户信息
     */
    SysUser findByUserName(String username);

    /**
     * 通过用户名查找用户个人信息
     *
     * @param username 用户名
     * @return 用户信息
     */
    SysUser findByUserInfoName(String username);

    /**
     * 根据用户id查询权限
     * @param userId
     * @return
     */
    Set<String> findPermsByUserId(Integer userId);

    /**
     * 通过用户id查询角色集合
     * @param userId
     * @return
     */
    Set<String> findRoleIdByUserId(Integer userId);


    /**
     * 登录
     * @param username
     * @param password
     * @param captcha
     * @param request
     * @return
     */
    String login(String username, String password,String captcha,HttpServletRequest request);


    /**
     * 修改用户信息
     * @param sysUser
     * @return
     */
    boolean updateUserInfo(SysUser sysUser);


}
