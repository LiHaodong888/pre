package com.xd.pre.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xd.pre.domain.SysJob;
import com.xd.pre.domain.SysUser;
import com.xd.pre.log.SysLog;
import com.xd.pre.service.ISysJobService;
import com.xd.pre.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 岗位管理 前端控制器
 * </p>
 *
 * @author lihaodong
 * @since 2019-05-01
 */
@RestController
@RequestMapping("/job")
public class SysJobController {

    @Autowired
    private ISysJobService jobService;


    /**
     * 获取岗位列表
     * @param page
     * @param pageSize
     * @param jobName
     * @return
     */
    @GetMapping
    @PreAuthorize("hasAuthority('sys:job:view')")
    public R getList(Integer page, Integer pageSize,@RequestParam(defaultValue = "") String jobName) {
        Map<String, Object> map = new HashMap<>();
        IPage<SysJob> sysJobIPage = jobService.selectJobList(page, pageSize, jobName);
        map.put("jobList", sysJobIPage.getRecords());
        map.put("total", sysJobIPage.getTotal());
        return R.ok(map);
    }

    /**
     * 保存岗位
     *
     * @param sysJob
     * @return
     */
    @SysLog(descrption = "保存岗位")
    @PostMapping
    @PreAuthorize("hasAuthority('sys:job:add')")
    public R save(@RequestBody SysJob sysJob) {
        return R.ok(jobService.save(sysJob));
    }

    /**
     * 根据id删除岗位
     * @param id
     * @return
     */
    @SysLog(descrption = "根据id删除岗位")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('sys:job:delete')")
    public R delete(@PathVariable("id") Integer id) {
        return R.ok(jobService.removeById(id));
    }

    /**
     * 更新岗位
     * @param sysJob
     * @return
     */
    @SysLog(descrption = "更新岗位")
    @PutMapping
    @PreAuthorize("hasAuthority('sys:job:update')")
    public R update(@RequestBody SysJob sysJob) {
        return R.ok(jobService.updateById(sysJob));
    }


    @GetMapping("/{id}")
    public R selectJobListByDeptId(@PathVariable("id") Integer deptId) {
        return R.ok(jobService.selectJobListByDeptId(deptId));
    }



}

