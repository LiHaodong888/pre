package com.xd.pre.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author lihaodong
 * @since 2019-04-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class DeptDto {

    private static final long serialVersionUID = 1L;

    private Integer deptId;

    /**
     * 部门名称
     */
    private String name;


    /**
     * 上级部门
     */
    private Integer parentId;

    /**
     * 排序
     */
    private Integer sort;


}
