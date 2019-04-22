package com.xd.pre.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.xd.pre.domain.SysDept;
import com.xd.pre.mapper.SysDeptMapper;
import com.xd.pre.service.ISysDeptService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.io.Serializable;
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
        List<SysDept> depts = baseMapper.selectList(new QueryWrapper<SysDept>().select("dept_id", "name", "parent_id", "sort", "create_time"));
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
    public boolean updateById(SysDept entity) {
        return super.updateById(entity);
    }

    @Override
    public boolean removeById(Serializable id) {
        // 部门层级删除
        List<Integer> idList = this.list(Wrappers.<SysDept>query().lambda().eq(SysDept::getParentId, id)).stream().map(SysDept::getDeptId).collect(Collectors.toList());
        // 删除自己
        idList.add((Integer) id);
        return super.removeByIds(idList);
    }

}
