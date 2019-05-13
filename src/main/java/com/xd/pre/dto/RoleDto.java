package com.xd.pre.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.xd.pre.domain.SysRoleDept;
import com.xd.pre.domain.SysRoleMenu;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 系统角色表
 * </p>
 *
 * @author lihaodong
 * @since 2019-04-21
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
    List<SysRoleMenu> roleMenus;


}
