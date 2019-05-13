package com.xd.pre.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.xd.pre.domain.SysDept;
import com.xd.pre.dto.DeptDto;
import com.xd.pre.mapper.SysDeptMapper;
import com.xd.pre.service.ISysDeptService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 部门管理 服务实现类
 * </p>
 *
 * @author lihaodong
 * @since 2019-04-21
 */
@Service
public class SysDeptServiceImpl extends ServiceImpl<SysDeptMapper, SysDept> implements ISysDeptService {

    @Override
    public boolean save(SysDept entity) {
        return super.save(entity);
    }

    @Override
    public List<SysDept> selectDeptList() {
        List<SysDept> sysDepts = new ArrayList<>();
        List<SysDept> depts = baseMapper.selectList(Wrappers.<SysDept>query().select("dept_id", "name", "parent_id", "sort", "create_time"));
        for (SysDept dept : depts) {
            if (dept.getParentId() == null || dept.getParentId() == 0) {
                dept.setLevel(0);
                sysDepts.add(dept);
            }
        }
        findChildren(sysDepts, depts);
        return sysDepts;
    }


    private void findChildren(List<SysDept> sysDepts, List<SysDept> depts) {
        for (SysDept sysDept : sysDepts) {
            List<SysDept> children = new ArrayList<>();
            for (SysDept dept : depts) {
                if (sysDept.getDeptId() != null && sysDept.getDeptId().equals(dept.getParentId())) {
                    dept.setParentName(sysDept.getName());
                    dept.setLevel(sysDept.getLevel() + 1);
                    children.add(dept);
                }
            }
            sysDept.setChildren(children);
            findChildren(children, depts);
        }
    }

    @Override
    public boolean updateDeptById(DeptDto entity) {
        SysDept sysDept = new SysDept();
        BeanUtils.copyProperties(entity,sysDept);
        sysDept.setUpdateTime(LocalDateTime.now());
        return this.updateById(sysDept);
    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean removeById(Serializable id) {
        // 部门层级删除
        List<Integer> idList = this.list(Wrappers.<SysDept>query().lambda().eq(SysDept::getParentId, id)).stream().map(SysDept::getDeptId).collect(Collectors.toList());
        // 删除自己
        idList.add((Integer) id);
        return super.removeByIds(idList);
    }

    @Override
    public String selectDeptNameByDeptId(int deptId) {
        return baseMapper.selectOne(Wrappers.<SysDept>query().lambda().select(SysDept::getName).eq(SysDept::getDeptId,deptId)).getName();
    }

    @Override
    public List<SysDept> selectDeptListBydeptName(String deptName) {
        return null;
    }

}
