package com.xd.pre.modules.sys.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xd.pre.modules.sys.domain.SysSocial;

import java.util.List;

/**
 * <p>
 * 社交登录 服务类
 * </p>
 *
 * @author lihaodong
 * @since 2019-04-27
 */
public interface ISysSocialService extends IService<SysSocial> {

    /**
     * 分页查询社区账号绑定集合
     * @param page
     * @return
     */
    IPage<SysSocial> selectSocialList(Page page, SysSocial sysSocial);

}
