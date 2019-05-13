package com.xd.pre.utils;

import com.xd.pre.domain.SysMenu;
import com.xd.pre.vo.MenuMetaVo;
import com.xd.pre.vo.MenuVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

/**
 * @Classname PreUtil
 * @Description TODO
 * @Author 李号东 lihaodongmail@163.com
 * @Date 2019-04-27 16:52
 * @Version 1.0
 */
public class PreUtil {


    public static List<MenuVo> buildMenus(List<SysMenu> menuDTOS) {
        List<MenuVo> list = new LinkedList<>();

        menuDTOS.forEach(menuDTO -> {

                    if (menuDTO != null) {
                        List<SysMenu> menuDTOList = menuDTO.getChildren();

                        MenuVo menuVo = new MenuVo();
                        menuVo.setName(menuDTO.getName());
                        menuVo.setPath(menuDTO.getPath());

                        // 如果不是外链
                        if (menuDTO.getIsFrame()) {
                            if (menuDTO.getParentId().equals(0)) {
                                //一级目录需要加斜杠，不然访问 会跳转404页面
                                menuVo.setPath("/" + menuDTO.getPath());
                                menuVo.setComponent(StringUtils.isEmpty(menuDTO.getComponent()) ? "Layout" : menuDTO.getComponent());
                            } else if (!StringUtils.isEmpty(menuDTO.getComponent())) {
                                menuVo.setComponent(menuDTO.getComponent());
                            }
                        }

                        menuVo.setMeta(new MenuMetaVo(menuDTO.getName(), menuDTO.getIcon()));

                        if (menuDTOList != null && menuDTOList.size() != 0 && menuDTO.getType() == 0) {
                            menuVo.setChildren(buildMenus(menuDTOList));
                            // 处理是一级菜单并且没有子菜单的情况
                        } else if (menuDTO.getParentId().equals(0)) {
                            menuVo.setAlwaysShow(true);
                            menuVo.setRedirect("noredirect");
                            MenuVo menuVo1 = new MenuVo();
                            menuVo1.setMeta(menuVo.getMeta());
                            // 非外链
                            if (menuDTO.getIsFrame()) {
                                menuVo1.setPath("index");
                                menuVo1.setName(menuVo.getName());
                                menuVo1.setComponent(menuVo.getComponent());
                            } else {
                                menuVo1.setPath(menuDTO.getPath());
                            }
                            menuVo.setName(null);
                            menuVo.setMeta(null);
                            menuVo.setComponent("Layout");
                            List<MenuVo> list1 = new ArrayList<MenuVo>();
                            list1.add(menuVo1);
                            menuVo.setChildren(list1);
                        } else {
                            menuVo.setChildren(new ArrayList<>());
                        }
                        list.add(menuVo);
                    }
                }
        );
        return list;
    }

    public static List<MenuVo> s(List<SysMenu> menuDTOs) {
        List<MenuVo> list = new LinkedList<>();

        menuDTOs.forEach(sysMenu -> {
            MenuVo menuVo = new MenuVo();
            if (!sysMenu.getIsFrame()){
                // 父级菜单
                if (sysMenu.getParentId() == 0) {
                    menuVo.setPath("/" + sysMenu.getPath());
                    menuVo.setAlwaysShow(true);
                    menuVo.setRedirect("noredirect");
                } else {
                    menuVo.setPath(sysMenu.getPath());
                }
            } else {
                if (sysMenu.getParentId() == 0) {
                    menuVo.setPath("/" + sysMenu.getPath());
                } else {
                    menuVo.setPath(sysMenu.getPath());
                }
            }
            menuVo.setName(sysMenu.getName());
            menuVo.setMeta(new MenuMetaVo(sysMenu.getName(), sysMenu.getIcon()));
            menuVo.setComponent(StringUtils.isEmpty(sysMenu.getComponent()) ? "Layout" : sysMenu.getComponent());
            if (sysMenu.getType() == 0 && sysMenu.getChildren() != null && sysMenu.getChildren().size() != 0){
                menuVo.setChildren(s(sysMenu.getChildren()));
            } else {
                menuVo.setChildren(new ArrayList<>());
            }
            list.add(menuVo);
        });
        return list;
    }


    /**
     * 遍历菜单
     * @param menuList
     * @param menus
     * @param menuType
     */
    public static void findChildren(List<SysMenu> menuList, List<SysMenu> menus, int menuType) {
        for (SysMenu sysMenu : menuList) {
            List<SysMenu> children = new ArrayList<>();
            for (SysMenu menu : menus) {
                if (menuType == 1 && menu.getType() == 2) {
                    // 如果是获取类型不需要按钮，且菜单类型是按钮的，直接过滤掉
                    continue;
                }
                if (sysMenu.getMenuId() != null && sysMenu.getMenuId().equals(menu.getParentId())) {
                    menu.setParentName(sysMenu.getName());
                    menu.setLevel(sysMenu.getLevel() + 1);
                    if (exists(children, menu)) {
                        children.add(menu);
                    }
                }
            }
            sysMenu.setChildren(children);
            children.sort((o1, o2) -> o1.getSort().compareTo(o2.getSort()));
            findChildren(children, menus, menuType);
        }
    }

    /**
     * 判断菜单是否存在
     * @param sysMenus
     * @param sysMenu
     * @return
     */
    public static boolean exists(List<SysMenu> sysMenus, SysMenu sysMenu) {
        boolean exist = false;
        for (SysMenu menu : sysMenus) {
            if (menu.getMenuId().equals(sysMenu.getMenuId())) {
                exist = true;
            }
        }
        return !exist;
    }

    /**
     * 获取加密盐
     * @return
     */
    public static String getSalt() {
        return UUID.randomUUID().toString().replaceAll("-", "").substring(0, 5);
    }

    public static String encode(String rawPass) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(rawPass);
    }

    /**
     * 获取用户名
     * @return
     */
    public static String getUsername(Authentication authentication) {
        String username = null;
        if(authentication != null) {
            Object principal = authentication.getPrincipal();
            if(principal != null && principal instanceof UserDetails) {
                username = ((UserDetails) principal).getUsername();
            }
        }
        return username;
    }

    /**
     * 获取当前登录信息
     * @return
     */
    public static Authentication getAuthentication() {
        if(SecurityContextHolder.getContext() == null) {
            return null;
        }
        return SecurityContextHolder.getContext().getAuthentication();
    }

}
