package com.xd.pre;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@MapperScan("com.xd.pre.mapper")
@RestController
@SpringBootApplication
public class PreApplication {

    public static void main(String[] args) {
        SpringApplication.run(PreApplication.class, args);
    }


    @RequestMapping("/login")
    public Map<String,Object> login(String username, String password){
        Map<String,Object> map = new HashMap<>();
        List<String> list = new ArrayList<>();
        list.add("admin");
        map.put("roles",list);
        map.put("token" ,"admin");
        map.put("avatar", "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
        map.put("name","Super Admin");
        return map;
    }

    @RequestMapping("/info")
    public Map<String, Object> info() {
        Map<String, Object> map = new HashMap<>();
        List<String> list = new ArrayList<>();
        list.add("admin");
        map.put("roles", list);
        map.put("token", "admin");
        map.put("avatar", "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
        map.put("name", "Super Admin");
        return map;
    }
}
