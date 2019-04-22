package com.xd.pre.service;

import com.xd.pre.domain.SysDept;
import com.baomidou.mybatisplus.extension.service.IService;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 部门管理 服务类
 * </p>
 *
 * @author lihaodong
 * @since 2019-04-21
 */
public interface ISysDeptService extends IService<SysDept> {

    /**
     * 保存部门信息
     * @return
     */
    @Override
    boolean save(SysDept entity);

    /**
     * 查询部门信息
     * @return
     */
    List<SysDept> selectDeptList();

    /**
     * 更新部门
     * @param entity
     * @return
     */
    @Override
    boolean updateById(SysDept entity);

    /**
     * 删除部门
     * @param id
     * @return
     */
    @Override
    boolean removeById(Serializable id);
}
