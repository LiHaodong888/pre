package com.xd.pre.service.impl;

import com.xd.pre.domain.SysUserRole;
import com.xd.pre.mapper.SysUserRoleMapper;
import com.xd.pre.service.ISysUserRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 用户角色表 服务实现类
 * </p>
 *
 * @author lihaodong
 * @since 2019-04-21
 */
@Service
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRole> implements ISysUserRoleService {


    @Override
    public boolean save(SysUserRole entity) {
        return super.save(entity);
    }


    @Override
    public List<SysUserRole> selectUserRoleListByUserId(Integer userId) {
        return baseMapper.selectUserRoleListByUserId(userId);
    }
}
