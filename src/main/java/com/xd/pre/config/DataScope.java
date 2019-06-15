package com.xd.pre.config;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.HashMap;
import java.util.List;

/**
 * @Classname DataScope
 * @Description 数据权限查询参数
 * @Author Created by Lihaodong (alias:小东啊) lihaodongmail@163.com
 * @Date 2019-06-13 13:17
 * @Version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class DataScope extends HashMap {
    /**
     * 限制范围的字段名称
     */
    private String scopeName = "deptId";

}