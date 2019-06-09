package com.xd.pre.dto;

import com.xd.pre.domain.SysRoleDept;
import com.xd.pre.domain.SysRoleMenu;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.List;

/**
 * @Classname UserDto
 * @Description 角色Dto
 * @Author 李号东 lihaodongmail@163.com
 * @Date 2019-04-23 21:26
 * @Version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class RoleDto {

    private static final long serialVersionUID = 1L;

    private Integer roleId;
    private String roleName;
    private String roleCode;
    private String roleDesc;
    private String delFlag;
    private int dataScope;
    List<SysRoleMenu> roleMenus;
    List<SysRoleDept> roleDepts;



}
