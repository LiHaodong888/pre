package com.xd.pre.vo;

import lombok.Data;
import org.jasypt.util.text.BasicTextEncryptor;

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

    public static void main(String[] args) {
        BasicTextEncryptor textEncryptor = new BasicTextEncryptor();
        textEncryptor.setPassword("EbfYkitulv73I2p0mXI50JMXoaxZTKJ1");

        String password = textEncryptor.encrypt("jdbc:mysql://101.132.64.80:3306/pre?useUnicode=true&characterEncoding=UTF-8&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=Asia/Shanghai");
        System.out.println(password);

    }
}
