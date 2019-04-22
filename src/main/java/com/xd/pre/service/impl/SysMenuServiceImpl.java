package com.xd.pre.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.xd.pre.domain.SysDept;
import com.xd.pre.domain.SysMenu;
import com.xd.pre.mapper.SysMenuMapper;
import com.xd.pre.service.ISysMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xd.pre.utils.R;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 菜单权限表 服务实现类
 * </p>
 *
 * @author lihaodong
 * @since 2019-04-21
 */
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements ISysMenuService {

    @Override
    public boolean save(SysMenu entity) {
        return super.save(entity);
    }

    @Override
    public List<SysMenu> selectMenuTree(Integer uid) {
        List<SysMenu> sysMenus = new ArrayList<>();
        List<SysMenu> menus = baseMapper.selectList(Wrappers.<SysMenu>query()
                .select("menu_id","name","perms","url","parent_id","icon","sort","type","del_flag"));
        for (SysMenu menu : menus) {
            if (menu.getParentId() == null || menu.getParentId() == 0) {
                menu.setLevel(0);
                if(exists(sysMenus, menu)) {
                    sysMenus.add(menu);
                }
            }
        }
        sysMenus.sort((o1, o2) -> o1.getSort().compareTo(o2.getSort()));
        findChildren(sysMenus, menus, 0);
        return sysMenus;
    }

    private void findChildren(List<SysMenu> menuList, List<SysMenu> menus, int menuType) {
        for (SysMenu sysMenu : menuList) {
            List<SysMenu> children = new ArrayList<>();
            for (SysMenu menu : menus) {
                if(menuType == 1 && menu.getType() == 2) {
                    // 如果是获取类型不需要按钮，且菜单类型是按钮的，直接过滤掉
                    continue ;
                }
                if (sysMenu.getMenuId() != null && sysMenu.getMenuId().equals(menu.getParentId())) {
                    menu.setParentName(sysMenu.getName());
                    menu.setLevel(sysMenu.getLevel() + 1);
                    if(exists(children, menu)) {
                        children.add(menu);
                    }
                }
            }
            sysMenu.setChildren(children);
            children.sort((o1, o2) -> o1.getSort().compareTo(o2.getSort()));
            findChildren(children, menus, menuType);
        }
    }
    private boolean exists(List<SysMenu> sysMenus, SysMenu sysMenu) {
        boolean exist = false;
        for(SysMenu menu:sysMenus) {
            if(menu.getMenuId().equals(sysMenu.getMenuId())) {
                exist = true;
            }
        }
        return !exist;
    }

    @Override
    public boolean updateById(SysMenu entity) {
        return super.updateById(entity);
    }

    @Override
    public R removeMenuById(Serializable id) {
        List<Integer> idList = this.list(Wrappers.<SysMenu>query().lambda().eq(SysMenu::getParentId, id)).stream().map(SysMenu::getMenuId).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(idList)){
            return R.error("菜单含有下级不能删除");
        }
        return R.ok(this.removeMenuById(id));
    }
}
