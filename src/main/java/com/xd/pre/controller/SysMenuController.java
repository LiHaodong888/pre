package com.xd.pre.controller;


import com.alibaba.fastjson.JSON;
import com.xd.pre.domain.SysMenu;
import com.xd.pre.dto.MenuDto;
import com.xd.pre.log.SysLog;
import com.xd.pre.security.SecurityUser;
import com.xd.pre.security.util.SecurityUtil;
import com.xd.pre.service.ISysMenuService;
import com.xd.pre.utils.PreUtil;
import com.xd.pre.utils.R;
import com.xd.pre.vo.MenuVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 菜单权限表 前端控制器
 * </p>
 *
 * @author lihaodong
 * @since 2019-04-21
 */
@RestController
@RequestMapping("/menu")
public class SysMenuController {

    @Autowired
    private ISysMenuService menuService;

    @Autowired
    private SecurityUtil securityUtil;

    /**
     * 添加菜单
     *
     * @param menu
     * @return
     */
    @PreAuthorize("hasAuthority('sys:menu:add')")
    @SysLog(descrption = "添加菜单")
    @PostMapping
    public R save(@RequestBody SysMenu menu) {
        return R.ok(menuService.save(menu));
    }

    /**
     * 获取菜单树
     *
     * @return
     */
    @GetMapping
    public R getMenuTree() {
        SecurityUser securityUser = securityUtil.getSecurityUser();
        return R.ok(menuService.selectMenuTree(securityUser.getUserId()));
    }


    /**
     * 获取所有菜单
     *
     * @return
     */
    @GetMapping("/getMenus")
    public R getMenus() {
        return R.ok(menuService.selectMenuTree(0));
    }

    /**
     * 修改菜单
     *
     * @param menuDto
     * @return
     */
    @PreAuthorize("hasAuthority('sys:menu:update')")
    @SysLog(descrption = "修改菜单")
    @PutMapping
    public R updateMenu(@RequestBody MenuDto menuDto) {
        return R.ok(menuService.updateMenuById(menuDto));
    }

    /**
     * 根据id删除菜单
     *
     * @param id
     * @return
     */
    @PreAuthorize("hasAuthority('sys:menu:delete')")
    @SysLog(descrption = "删除菜单")
    @DeleteMapping("/{id}")
    public R deleteMenu(@PathVariable("id") Integer id) {
        return menuService.removeMenuById(id);
    }

    /**
     * 获取路由
     *
     * @return
     */
    @GetMapping("/getRouters")
    public R getRouters() {
        SecurityUser securityUser = securityUtil.getSecurityUser();
        return R.ok(PreUtil.buildMenus(menuService.selectMenuTree(securityUser.getUserId())));
    }

}

