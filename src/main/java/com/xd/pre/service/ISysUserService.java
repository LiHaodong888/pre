package com.xd.pre.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xd.pre.domain.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xd.pre.dto.UserDto;

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
     * 分页查询用户
     * @param page
     * @param pageSize
     * @return
     */
    IPage<SysUser> selectUserList(int page, int pageSize,Integer deptId);

    /**
     * 保存用户以及角色部门等信息
     * @param userDto
     * @return
     */
    boolean insertUser(UserDto userDto);

    /**
     * 更新用户以及角色部门等信息
     * @param userDto
     * @return
     */
    boolean updateUser(UserDto userDto);

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

    Set<String> findPermsByUserId(Integer userId);


    String login(String username, String password,String captcha,HttpServletRequest request);
}
