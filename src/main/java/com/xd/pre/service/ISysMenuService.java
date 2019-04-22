package com.xd.pre.service;

import com.xd.pre.domain.SysMenu;
import com.baomidou.mybatisplus.extension.service.IService;
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
    @Override
    boolean updateById(SysMenu entity);

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
}
