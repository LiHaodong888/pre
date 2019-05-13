package com.xd.pre.codegen.utils;

/**
 * @Classname FileTree
 * @Description TODO
 * @Author 李号东 lihaodongmail@163.com
 * @Date 2019-04-26 14:33
 * @Version 1.0
 */

import java.io.File;
import java.util.Objects;

public class FileTree {

    public static void main(String[] args) {

        printFileTree(new FileTree().getOutputDir("pre"), 0);
    }

    /**
     * 返回项目路径
     *
     * @param projectName 项目名
     * @return 项目路径
     */
    private String getOutputDir(String projectName) {
        String path = Objects.requireNonNull(this.getClass().getClassLoader().getResource("")).getPath();
        int index = path.indexOf(projectName);
        return "/" + path.substring(1, index) + projectName;
    }

    private static void printFileTree(String driver, int level) {
        File file = new File(driver);
        if (file.isDirectory()){
            printByLevel(file.getName(), level);
        }
        if (file.exists()) {
            if (file.isDirectory()) {
                File[] children = file.listFiles();
                for (int i = 0; i < children.length; i++) {
                    printFileTree(children[i].getPath(), level + 1);
                }
            }
        }
    }

    private static void printByLevel(String name, int level) {
        for (int i = 0; i < level; i++) {
            System.out.print(" ");
        }
        System.out.println("|-" + name);
    }

}
