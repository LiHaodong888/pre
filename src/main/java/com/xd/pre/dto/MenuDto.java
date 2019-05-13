package com.xd.pre.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author lihaodong
 * @since 2019-04-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class MenuDto {

    private static final long serialVersionUID = 1L;

    private Integer menuId;
    private String name;
    private String perms;
    private String url;
    private Boolean isFrame;
    private Integer parentId;
    private String component;
    private String icon;
    private Integer sort;
    private Integer type;
    private String delFlag;

}
