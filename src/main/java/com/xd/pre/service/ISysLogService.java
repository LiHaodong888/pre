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

    @Override
    boolean save(SysLog entity);

    IPage<SysLog> selectLogList(Integer page, Integer pageSize,Integer type);


    @Override
    boolean removeById(Serializable id);
}
