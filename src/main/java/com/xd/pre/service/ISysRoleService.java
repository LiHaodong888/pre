package com.xd.pre.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.xd.pre.domain.SysRole;
import com.baomidou.mybatisplus.extension.service.IService;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 系统角色表 服务类
 * </p>
 *
 * @author lihaodong
 * @since 2019-04-21
 */
public interface ISysRoleService extends IService<SysRole> {

    /**
     * 保存角色
     * @param entity
     * @return
     */
    @Override
    boolean save(SysRole entity);

    /**
     * 更新角色
     * @param entity
     * @return
     */
    @Override
    boolean updateById(SysRole entity);

    /**
     * 根据主键删除角色
     * @param id
     * @return
     */
    @Override
    boolean removeById(Serializable id);

    /**
     * 获取角色列表
     * @return
     */
    List<SysRole> selectRoleList();
}
