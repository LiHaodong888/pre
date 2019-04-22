package com.xd.pre.controller;


import com.xd.pre.domain.SysDept;
import com.xd.pre.service.ISysDeptService;
import com.xd.pre.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

/**
 * <p>
 * 部门管理 前端控制器
 * </p>
 *
 * @author lihaodong
 * @since 2019-04-21
 */
@RestController
@RequestMapping("/dept")
public class SysDeptController {

    @Autowired
    private ISysDeptService deptService;

    /**
     * 保存部门信息
     *
     * @param sysDept
     * @return
     */
    @PostMapping
    public R save(SysDept sysDept) {
        return R.ok(deptService.save(sysDept));
    }

    /**
     * 获取部门信息
     *
     * @return
     */
    @GetMapping
    public R getDeptTree() {
        return R.ok(deptService.selectDeptList());
    }

    /**
     * 获取部门信息
     *
     * @return
     */
    @PutMapping
    public R update(SysDept sysDept) {
        sysDept.setUpdateTime(LocalDateTime.now());
        return R.ok(deptService.updateById(sysDept));
    }

    /**
     * 删除部门信息
     *
     * @return
     */
    @DeleteMapping("/{id}")
    public R delete(@PathVariable("id") Integer id) {
        return R.ok(deptService.removeById(id));
    }


}

