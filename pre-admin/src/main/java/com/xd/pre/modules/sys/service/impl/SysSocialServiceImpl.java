package com.xd.pre.modules.sys.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xd.pre.modules.sys.domain.SysSocial;
import com.xd.pre.modules.sys.domain.SysUser;
import com.xd.pre.modules.sys.mapper.SysSocialMapper;
import com.xd.pre.modules.sys.service.ISysSocialService;
import com.xd.pre.modules.sys.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;


/**
 * <p>
 * 社交登录 服务实现类
 * </p>
 *
 * @author lihaodong
 * @since 2019-04-27
 */
@Service
public class SysSocialServiceImpl extends ServiceImpl<SysSocialMapper, SysSocial> implements ISysSocialService {

    @Autowired
    private ISysUserService sysUserService;

    @Override
    public IPage<SysSocial> selectSocialList(Integer page, Integer pageSize) {
        Page<SysSocial> socialPage = new Page<>(page, pageSize);
        IPage<SysSocial> socialIPage = baseMapper.selectPage(socialPage, Wrappers.<SysSocial>lambdaQuery());
        socialIPage.setRecords(socialIPage.getRecords().stream().peek(sysSocial -> sysSocial.setUserName(sysUserService.findSecurityUserByUser(new SysUser().setUserId(Integer.valueOf(sysSocial.getUserId()))).getUsername())).collect(Collectors.toList()));
        return socialIPage;
    }

}
