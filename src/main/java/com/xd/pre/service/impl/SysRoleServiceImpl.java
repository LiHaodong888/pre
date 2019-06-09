package com.xd.pre.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.xd.pre.domain.SysMenu;
import com.xd.pre.domain.SysRole;
import com.xd.pre.domain.SysRoleDept;
import com.xd.pre.domain.SysRoleMenu;
import com.xd.pre.dto.RoleDto;
import com.xd.pre.handler.DataScopeContext;
import com.xd.pre.mapper.SysRoleMapper;
import com.xd.pre.service.ISysRoleDeptService;
import com.xd.pre.service.ISysRoleMenuService;
import com.xd.pre.service.ISysRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 系统角色表 服务实现类
 * </p>
 *
 * @author lihaodong
 * @since 2019-04-21
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements ISysRoleService {

    @Resource
    private ISysRoleMenuService roleMenuService;

    @Resource
    private ISysRoleDeptService roleDeptService;

    @Autowired
    private DataScopeContext dataScopeContext;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean saveRoleMenu(RoleDto roleDto) {
        SysRole sysRole = new SysRole();
        BeanUtils.copyProperties(roleDto, sysRole);
        baseMapper.insertRole(sysRole);
        Integer roleId = sysRole.getRoleId();
        //维护角色菜单
        List<SysRoleMenu> roleMenus = roleDto.getRoleMenus();
        if (CollectionUtil.isNotEmpty(roleMenus)) {
            List<SysRoleMenu> rms = roleMenus.stream().map(sysRoleMenu -> {
                SysRoleMenu roleMenu = new SysRoleMenu();
                roleMenu.setRoleId(roleId);
                roleMenu.setMenuId(sysRoleMenu.getMenuId());
                return roleMenu;
            }).collect(Collectors.toList());
            roleMenuService.saveBatch(rms);
        }
        // 维护角色部门权限
//        DataScopeContext dataScopeContext = new DataScopeContext();
        // 根据数据权限范围查询部门ids
        List<Integer> ids = dataScopeContext.getDeptIdsForDataScope(roleDto, roleDto.getDataScope());
        if (CollectionUtil.isNotEmpty(ids)) {
            List<SysRoleDept> roleDepts = ids.stream().map(integer -> {
                SysRoleDept sysRoleDept = new SysRoleDept();
                sysRoleDept.setDeptId(integer);
                sysRoleDept.setRoleId(roleId);
                return sysRoleDept;
            }).collect(Collectors.toList());

            roleDeptService.saveBatch(roleDepts);
        }
        return true;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateRoleMenu(RoleDto roleDto) {
        SysRole sysRole = new SysRole();
        BeanUtils.copyProperties(roleDto, sysRole);
        baseMapper.updateById(sysRole);
        List<SysRoleMenu> roleMenus = roleDto.getRoleMenus();
        roleMenuService.remove(Wrappers.<SysRoleMenu>query().lambda().eq(SysRoleMenu::getRoleId, sysRole.getRoleId()));
        roleDeptService.remove(Wrappers.<SysRoleDept>query().lambda().eq(SysRoleDept::getRoleId, sysRole.getRoleId()));

        if (CollectionUtil.isNotEmpty(roleMenus)) {
            roleMenuService.saveBatch(roleMenus);
        }
        // 根据数据权限范围查询部门ids
        List<Integer> ids = dataScopeContext.getDeptIdsForDataScope(roleDto, roleDto.getDataScope());

        if (CollectionUtil.isNotEmpty(ids)) {
            List<SysRoleDept> roleDepts = ids.stream().map(integer -> {
                SysRoleDept sysRoleDept = new SysRoleDept();
                sysRoleDept.setDeptId(integer);
                sysRoleDept.setRoleId(roleDto.getRoleId());
                return sysRoleDept;
            }).collect(Collectors.toList());
            roleDeptService.saveBatch(roleDepts);
        }
        return true;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean removeById(Serializable id) {
        roleMenuService.remove(Wrappers.<SysRoleMenu>query().lambda().eq(SysRoleMenu::getRoleId, id));
        roleDeptService.remove(Wrappers.<SysRoleDept>query().lambda().eq(SysRoleDept::getRoleId, id));
        return super.removeById(id);
    }

    @Override
    public List<SysRole> selectRoleList() {
        return list().stream().peek(sysRole ->
                sysRole.setRoleDepts(roleDeptService.getRoleDeptIds(sysRole.getRoleId()).stream().map(SysRoleDept::getDeptId).collect(Collectors.toList()))
        ).collect(Collectors.toList());
    }


    @Override
    public List<SysMenu> findMenuListByRoleId(int roleId) {
        return baseMapper.findMenuListByRoleId(roleId);
    }

}
