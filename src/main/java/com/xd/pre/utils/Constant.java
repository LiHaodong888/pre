package com.xd.pre.utils;

/**
 * @Classname Constant
 * @Description 常量
 * @Author 李号东 lihaodongmail@163.com
 * @Date 2019-04-29 19:49
 * @Version 1.0
 */
public class Constant {

    /**
     * 菜单类型
     */
    public enum MenuType {
        /**
         * 目录
         */
        CATALOG(0),
        /**
         * 菜单
         */
        MENU(1),
        /**
         * 按钮
         */
        BUTTON(2);

        private int value;

        MenuType(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

}
