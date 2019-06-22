package com.xd.pre.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xd.pre.domain.SysLog;
import com.xd.pre.domain.SysUser;
import com.xd.pre.mapper.SysLogMapper;
import com.xd.pre.service.ISysLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.io.Serializable;

/**
 * <p>
 * 系统日志 服务实现类
 * </p>
 *
 * @author lihaodong
 * @since 2019-04-27
 */
@Service
public class SysLogServiceImpl extends ServiceImpl<SysLogMapper, SysLog> implements ISysLogService {

    @Override
    public boolean save(SysLog entity) {
        return super.save(entity);
    }

    @Override
    public IPage<SysLog> selectLogList(Integer page, Integer pageSize,Integer type,String userName) {
        Page<SysLog> logPage = new Page<>(page, pageSize);
        LambdaQueryWrapper<SysLog> sysLogLambdaQueryWrapper = Wrappers.<SysLog>lambdaQuery().eq(SysLog::getType, type).orderByDesc(SysLog::getStartTime);
        if (StrUtil.isNotEmpty(userName)){
            sysLogLambdaQueryWrapper.like(SysLog::getUserName,userName);
        }
        return baseMapper.selectPage(logPage, sysLogLambdaQueryWrapper);
    }

    @Override
    public boolean removeById(Serializable id) {
        return super.removeById(id);
    }
}
