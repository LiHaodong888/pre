package com.xd.pre.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xd.pre.domain.SysLog;
import com.baomidou.mybatisplus.extension.service.IService;

import java.io.Serializable;

/**
 * <p>
 * 系统日志 服务类
 * </p>
 *
 * @author lihaodong
 * @since 2019-04-27
 */
public interface ISysLogService extends IService<SysLog> {

    /**
     * 保存日志
     * @param entity
     * @return
     */
    @Override
    boolean save(SysLog entity);

    /**
     * 分页查询日志
     * @param page
     * @param pageSize
     * @param type
     * @return
     */
    IPage<SysLog> selectLogList(Integer page, Integer pageSize,Integer type);


    /**
     * 根据id删除日志
     * @param id
     * @return
     */
    @Override
    boolean removeById(Serializable id);


}
