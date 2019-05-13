package com.xd.pre.service;

import com.xd.pre.domain.SysDept;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xd.pre.dto.DeptDto;

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
    boolean updateDeptById(DeptDto entity);

    /**
     * 删除部门
     * @param id
     * @return
     */
    @Override
    boolean removeById(Serializable id);

    /**
     * 根据部门id查询部门名称
     * @param deptId
     * @return
     */
    String selectDeptNameByDeptId(int deptId);

    /**
     * 根据部门名称查询
     * @param deptName
     * @return
     */
    List<SysDept> selectDeptListBydeptName(String deptName);
}
