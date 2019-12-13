package com.xd.pre.modules.sys.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xd.pre.modules.sys.domain.SysJob;
import com.xd.pre.modules.sys.mapper.SysJobMapper;
import com.xd.pre.modules.sys.service.ISysDeptService;
import com.xd.pre.modules.sys.service.ISysJobService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 岗位管理 服务实现类
 * </p>
 *
 * @author lihaodong
 * @since 2019-05-01
 */
@Service
public class SysJobServiceImpl extends ServiceImpl<SysJobMapper, SysJob> implements ISysJobService {

    @Autowired
    private ISysDeptService deptService;

    @Override
    public IPage<SysJob> selectJobList(int page, int pageSize, String jobName) {
        LambdaQueryWrapper<SysJob> jobLambdaQueryWrapper = Wrappers.<SysJob>lambdaQuery();
        if (StringUtils.isNotEmpty(jobName)) {
            jobLambdaQueryWrapper.eq(SysJob::getJobName, jobName);
        }
        IPage<SysJob> sysJobIPage = baseMapper.selectPage(new Page<>(page, pageSize), jobLambdaQueryWrapper);
        List<SysJob> sysJobList = sysJobIPage.getRecords();
        if (CollectionUtil.isNotEmpty(sysJobList)){
            List<SysJob> collect = sysJobList.stream().peek(sysJob -> sysJob.setDeptName(deptService.selectDeptNameByDeptId(sysJob.getDeptId()))).sorted((SysJob o1, SysJob o2) -> (o1.getSort() - o2.getSort())).collect(Collectors.toList());
            return sysJobIPage.setRecords(collect);
        }
        return sysJobIPage;
    }

    @Override
    public List<SysJob> selectJobListByDeptId(Integer deptId) {
        return baseMapper.selectList(Wrappers.<SysJob>lambdaQuery().select(SysJob::getId,SysJob::getJobName).eq(SysJob::getDeptId,deptId));
    }

    @Override
    public String selectJobNameByJobId(Integer jobId) {
        return baseMapper.selectOne(Wrappers.<SysJob>lambdaQuery().select(SysJob::getJobName).eq(SysJob::getId,jobId)).getJobName();
    }

}
