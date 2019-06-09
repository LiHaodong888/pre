package com.xd.pre.handler;

import com.xd.pre.domain.SysDept;
import com.xd.pre.dto.RoleDto;
import com.xd.pre.service.ISysDeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Classname AllDataScope
 * @Description TODO
 * @Author Created by Lihaodong (alias:小东啊) lihaodongmail@163.com
 * @Date 2019-06-08 16:27
 * @Version 1.0
 */
@Component("1")
public class AllDataScope implements AbstractDataScopeHandler {

    @Autowired
    private ISysDeptService deptService;

    @Override
    public List<Integer> getDeptIds(RoleDto roleDto, DataScopeTypeEnum dataScopeTypeEnum) {
        List<SysDept> sysDepts = deptService.list();
        return sysDepts.stream().map(SysDept::getDeptId).collect(Collectors.toList());
    }
}
