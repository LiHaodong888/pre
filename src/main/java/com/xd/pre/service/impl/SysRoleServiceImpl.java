package com.xd.pre.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.xd.pre.domain.SysMenu;
import com.xd.pre.domain.SysRole;
import com.xd.pre.domain.SysRoleMenu;
import com.xd.pre.dto.RoleDto;
import com.xd.pre.mapper.SysRoleMapper;
import com.xd.pre.service.ISysRoleMenuService;
import com.xd.pre.service.ISysRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean saveRoleMenu(RoleDto roleDto) {
        SysRole sysRole = new SysRole();
        BeanUtils.copyProperties(roleDto, sysRole);
        baseMapper.insertRole(sysRole);
        Integer roleId = sysRole.getRoleId();
        List<SysRoleMenu> roleMenus = roleDto.getRoleMenus();
        List<SysRoleMenu> rms = new ArrayList<>();
        if (!CollectionUtils.isEmpty(roleMenus)) {
            roleMenus.forEach(item -> {
                SysRoleMenu sysRoleMenu = new SysRoleMenu();
                sysRoleMenu.setRoleId(roleId);
                sysRoleMenu.setMenuId(item.getMenuId());
                rms.add(sysRoleMenu);
            });
            return roleMenuService.saveBatch(rms);
        }
        return false;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateRoleMenu(RoleDto roleDto) {
        SysRole sysRole = new SysRole();
        BeanUtils.copyProperties(roleDto, sysRole);
        baseMapper.updateById(sysRole);
        List<SysRoleMenu> roleMenus = roleDto.getRoleMenus();
        roleMenuService.remove(Wrappers.<SysRoleMenu>query().lambda().eq(SysRoleMenu::getRoleId, sysRole.getRoleId()));
        return roleMenuService.saveBatch(roleMenus);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean removeById(Serializable id) {
        roleMenuService.remove(Wrappers.<SysRoleMenu>query().lambda().eq(SysRoleMenu::getRoleId, id));
        return super.removeById(id);
    }

    @Override
    public List<SysRole> selectRoleList() {
        return super.list(new QueryWrapper<>());
    }

    @Override
    public List<SysMenu> findMenuListByRoleId(int roleId) {
        return baseMapper.findMenuListByRoleId(roleId);
    }

}
