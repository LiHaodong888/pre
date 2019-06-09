package com.xd.pre.handler;

import com.xd.pre.dto.RoleDto;
import com.xd.pre.security.util.SecurityUtil;
import com.xd.pre.service.ISysDeptService;
import com.xd.pre.service.ISysUserService;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Classname ThisLevelChildenDataScope
 * @Description TODO
 * @Author Created by Lihaodong (alias:小东啊) lihaodongmail@163.com
 * @Date 2019-06-08 16:30
 * @Version 1.0
 */
@Component("3")
public class ThisLevelChildenDataScope implements AbstractDataScopeHandler {

    private final ISysUserService userService;

    private final SecurityUtil securityUtil;

    private final ISysDeptService deptService;

    public ThisLevelChildenDataScope(ISysDeptService deptService, ISysUserService userService, SecurityUtil securityUtil) {
        this.deptService = deptService;
        this.userService = userService;
        this.securityUtil = securityUtil;
    }

    @Override
    public List<Integer> getDeptIds(RoleDto roleDto, DataScopeTypeEnum dataScopeTypeEnum) {
        Integer deptId = userService.findByUserName(securityUtil.getSecurityUser().getUsername()).getDeptId();
        return deptService.selectDeptIds(deptId);
    }
}
