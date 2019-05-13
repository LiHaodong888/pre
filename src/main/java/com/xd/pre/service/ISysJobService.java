package com.xd.pre.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xd.pre.domain.SysJob;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xd.pre.domain.SysUser;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 岗位管理 服务类
 * </p>
 *
 * @author lihaodong
 * @since 2019-05-01
 */
public interface ISysJobService extends IService<SysJob> {


    /**
     * 保存岗位
     * @param entity
     * @return
     */
    @Override
    boolean save(SysJob entity);

    /**
     * 根据id删除岗位
     * @param id
     * @return
     */
    @Override
    boolean removeById(Serializable id);

    /**
     * 根据id更新岗位
     * @param entity
     * @return
     */
    @Override
    boolean updateById(SysJob entity);

    /**
     * 分页查询岗位列表
     * @param page
     * @param pageSize
     * @param jobName
     * @return
     */
    IPage<SysJob> selectJobList(int page, int pageSize, String jobName);


    /**
     * 根据部门id查询所属下的岗位信息
     * @param deptId
     * @return
     */
    List<SysJob> selectJobListByDeptId(Integer deptId);


    String selectJobNameByJobId(Integer jobId);

}
