package com.xd.pre.mapper;

import com.xd.pre.domain.SysMenu;
import com.xd.pre.domain.SysRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 系统角色表 Mapper 接口
 * </p>
 *
 * @author lihaodong
 * @since 2019-04-21
 */
@Repository
public interface SysRoleMapper extends BaseMapper<SysRole> {


    @Insert("insert into sys_role (role_name,role_code, role_desc) values (#{roleName}, #{roleCode},#{roleDesc})")
    @Options(useGeneratedKeys=true, keyProperty="roleId", keyColumn="role_id")
    Boolean insertRole(SysRole sysRole);

    /**
     *
     * @param roleId
     * @return
     */
    @Select("select m.menu_id,m.name,m.type,m.parent_id,m.sort,m.perms from sys_menu m, sys_role_menu rm where rm.role_id = #{roleId} and m.menu_id = rm.menu_id")
    List<SysMenu> findMenuListByRoleId(int roleId);
}
