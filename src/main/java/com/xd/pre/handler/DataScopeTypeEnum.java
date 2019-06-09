package com.xd.pre.handler;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Classname DataScopeTypeEnum
 * @Description TODO
 * @Author Created by Lihaodong (alias:小东啊) lihaodongmail@163.com
 * @Date 2019-06-08 16:02
 * @Version 1.0
 */
@Getter
@AllArgsConstructor
public enum DataScopeTypeEnum {

    /**
     * 全部
     */
    ALL(1, "全部"),
    /**
     * 本级
     */
    THIS_LEVEL(2, "本级"),

    /**
     * 本级以及子级
     */
    THIS_LEVEL_CHILDREN(3,"本级以及子级"),
    /**
     * 自定义
     */
    CUSTOMIZE(4, "自定义");


    private int value;
    private String description;


    public static DataScopeTypeEnum valueOf(int value) {
        for(DataScopeTypeEnum type : DataScopeTypeEnum.values()) {
            if(type.getValue() == value) {
                return type;
            }
        }
        return  ALL;
    }


}
