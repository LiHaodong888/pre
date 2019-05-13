package com.xd.pre.vo;

import lombok.Data;

import java.util.List;

/**
 * @Classname menuVo
 * @Description TODO
 * @Author 李号东 lihaodongmail@163.com
 * @Date 2019-05-05 16:38
 * @Version 1.0
 */
@Data
public class MenuVo {

    private String name;
    private String path;
    private String redirect;
    private String component;
    private Boolean alwaysShow;
    private MenuMetaVo meta;
    private List<MenuVo> children;

}
