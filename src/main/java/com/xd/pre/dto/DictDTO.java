package com.xd.pre.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * @Classname DictDTO
 * @Description 字典dto
 * @Author 李号东 lihaodongmail@163.com
 * @Date 2019-06-02 09:36
 * @Version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class DictDTO {


    /**
     * 编号
     */
    private Integer id;


    /**
     * 数据值
     */
    private String value;

    /**
     * 标签名
     */
    private String label;

    /**
     * 名称
     */
    private String name;

    /**
     * 描述
     */
    private String description;

    /**
     * 排序（升序）
     */
    private Integer sort;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 备注信息
     */
    private String remark;
}
