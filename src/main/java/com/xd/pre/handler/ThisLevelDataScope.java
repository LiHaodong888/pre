package com.xd.pre.handler;

import cn.hutool.core.util.ObjectUtil;
import com.xd.pre.dto.RoleDto;
import com.xd.pre.exception.BaseException;
import com.xd.pre.security.util.SecurityUtil;
import com.xd.pre.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Classname ThisLevelHandler
 * @Description 本级
 * @Author Created by Lihaodong (alias:小东啊) lihaodongmail@163.com
 * @Date 2019-06-08 15:44
 * @Version 1.0
 */
@Component("2")
public class ThisLevelDataScope implements AbstractDataScopeHandler {

    @Autowired
    private ISysUserService userService;

    @Autowired
    private SecurityUtil securityUtil;

    @Override
    public List<Integer> getDeptIds(RoleDto roleDto, DataScopeTypeEnum dataScopeTypeEnum) {
        // 用于存储部门id
        List<Integer> deptIds = new ArrayList<>();
        deptIds.add(userService.findByUserName(securityUtil.getSecurityUser().getUsername()).getDeptId());
        return deptIds;
    }
}
