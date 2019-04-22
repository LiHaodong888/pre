package com.xd.pre.controller;


import com.xd.pre.service.ISysRoleService;
import com.xd.pre.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 系统角色表 前端控制器
 * </p>
 *
 * @author lihaodong
 * @since 2019-04-21
 */
@RestController
@RequestMapping("/role")
public class SysRoleController {

    @Autowired
    private ISysRoleService roleService;

    /**
     *
     * 获取角色列表
     *
     * @return
     */
    @GetMapping
    public R getRoleList() {
        return R.ok(roleService.selectRoleList());
    }
}

