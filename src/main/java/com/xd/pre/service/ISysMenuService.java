package com.xd.pre.service;

import com.xd.pre.domain.SysMenu;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xd.pre.dto.MenuDTO;
import com.xd.pre.utils.R;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 菜单权限表 服务类
 * </p>
 *
 * @author lihaodong
 * @since 2019-04-21
 */
public interface ISysMenuService extends IService<SysMenu> {

    /**
     * 保存菜单信息
     * @param entity
     * @return
     */
    @Override
    boolean save(SysMenu entity);

    /**
     * 更新菜单信息
     * @param entity
     * @return
     */
    boolean updateMenuById(MenuDTO entity);

    /**
     * 删除菜单信息
     * @param id
     * @return
     */
    R removeMenuById(Serializable id);

    /**
     * 根据用户id查找菜单树
     * @return
     */
    List<SysMenu> selectMenuTree(Integer uid);

    /**
     * @Author 李号东
     * @Description 根据父id查询菜单
     * @Date 18:43 2019-05-12
     **/
    SysMenu getMenuById(Integer parentId);

    /**
     * @Description 根据用户id查询权限
     **/
    List<String> findPermsByUserId(Integer userId);
}
