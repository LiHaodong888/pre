package com.xd.pre.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xd.pre.domain.SysUser;
import com.xd.pre.dto.UserDto;
import com.xd.pre.log.SysLog;
import com.xd.pre.service.ISysUserService;
import com.xd.pre.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author lihaodong
 * @since 2019-04-21
 */
@RestController
@RequestMapping("/user")
public class SysUserController {

    @Autowired
    private ISysUserService userService;

    /**
     * 保存用户包括角色和部门
     *
     * @param userDto
     * @return
     */
    @SysLog(descrption = "保存用户包括角色和部门")
    @PostMapping
    @PreAuthorize("hasAuthority('sys:user:add')")
    public R insert(@RequestBody UserDto userDto) {
        return R.ok(userService.insertUser(userDto));
    }


    /**
     * 获取用户列表集合
     *
     * @param page
     * @param pageSize
     * @return
     */
    @SysLog(descrption = "查询用户集合")
    @GetMapping
    @PreAuthorize("hasAuthority('sys:user:view')")
    public R getList(Integer page, Integer pageSize,Integer deptId) {
        Map<String, Object> map = new HashMap<>();
        IPage<SysUser> usersIPage = userService.selectUserList(page, pageSize,deptId);
        map.put("userList", usersIPage.getRecords());
        map.put("total", usersIPage.getTotal());
        return R.ok(map);
    }

    /**
     * 更新用户包括角色和部门
     *
     * @param userDto
     * @return
     */
    @SysLog(descrption = "更新用户包括角色和部门")
    @PutMapping
    @PreAuthorize("hasAuthority('sys:user:update')")
    public R update(@RequestBody UserDto userDto) {
        return R.ok(userService.updateUser(userDto));
    }

    /**
     * 删除用户包括角色和部门
     *
     * @param userId
     * @return
     */
    @SysLog(descrption = "删除用户包括角色和部门")
    @DeleteMapping("/{userId}")
    @PreAuthorize("hasAuthority('sys:user:delete')")
    public R delete(@PathVariable("userId") Integer userId) {
        return R.ok(userService.removeUser(userId));
    }


    /**
     * 重置密码
     *
     * @param userId
     * @return
     */
    @SysLog(descrption = "重置密码")
    @PutMapping("/{userId}")
    @PreAuthorize("hasAuthority('sys:user:rest')")
    public R restPass(@PathVariable("userId") Integer userId) {
        return R.ok(userService.restPass(userId));
    }

}

