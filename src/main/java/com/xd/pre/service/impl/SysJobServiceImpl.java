package com.xd.pre.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xd.pre.domain.SysJob;
import com.xd.pre.domain.SysUser;
import com.xd.pre.mapper.SysJobMapper;
import com.xd.pre.service.ISysDeptService;
import com.xd.pre.service.ISysJobService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;
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
    public boolean save(SysJob entity) {
        return super.save(entity);
    }

    @Override
    public boolean removeById(Serializable id) {
        return super.removeById(id);
    }

    @Override
    public boolean updateById(SysJob entity) {
        return super.updateById(entity);
    }

    @Override
    public IPage<SysJob> selectJobList(int page, int pageSize, String jobName) {
        LambdaQueryWrapper<SysJob> jobLambdaQueryWrapper = Wrappers.<SysJob>lambdaQuery();
        if (StringUtils.isNotEmpty(jobName)) {
            jobLambdaQueryWrapper.eq(SysJob::getJobName, jobName);
        }
        IPage<SysJob> sysJobIPage = baseMapper.selectPage(new Page<>(page, pageSize), jobLambdaQueryWrapper);
        List<SysJob> sysJobList = sysJobIPage.getRecords();
        List<SysJob> collect = sysJobList.stream().peek(sysJob -> sysJob.setDeptName(deptService.selectDeptNameByDeptId(sysJob.getDeptId()))).sorted((SysJob o1, SysJob o2) -> (o1.getSort() - o2.getSort())).collect(Collectors.toList());
        return sysJobIPage.setRecords(collect);
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
