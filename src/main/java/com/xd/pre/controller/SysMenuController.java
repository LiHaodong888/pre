package com.xd.pre.controller;


import com.xd.pre.domain.SysMenu;
import com.xd.pre.service.ISysMenuService;
import com.xd.pre.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    /**
     * 添加菜单
     *
     * @param menu
     * @return
     */
    @PostMapping
    public R save(SysMenu menu) {
        return R.ok(menuService.save(menu));
    }

    /**
     * 获取菜单树
     *
     * @return
     */
    @GetMapping
    public R getMenuTree() {
        return R.ok(menuService.selectMenuTree(0));
    }

    /**
     * 修改菜单
     *
     * @param menu
     * @return
     */
    @PutMapping
    public R updateMenu(SysMenu menu) {
        return R.ok(menuService.updateById(menu));
    }
}

