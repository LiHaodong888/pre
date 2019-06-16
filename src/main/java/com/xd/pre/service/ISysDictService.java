package com.xd.pre.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xd.pre.domain.SysDict;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xd.pre.dto.DictDTO;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 字典表 服务类
 * </p>
 *
 * @author lihaodong
 * @since 2019-05-17
 */
public interface ISysDictService extends IService<SysDict> {

    @Override
    boolean save(SysDict entity);

    /**
     * 分页查询字典列表
     * @param page
     * @param pageSize
     * @return
     */
    IPage<SysDict> selectDictList(int page, int pageSize);

    /**
     * 修改字典
     * @param dictDto
     * @return
     */
    boolean updateDict(DictDTO dictDto);


    /**
     *
     * @param name
     * @return
     */
    List<SysDict> selectDictDetailList(String name);

    /**
     * 根据字典名称删除
     * @param name
     * @return
     */
    boolean deleteDictByName(String name);


    /**
     * 根据主键Id删除字典
     * @param id
     * @return
     */
    @Override
    boolean removeById(Serializable id);
}
