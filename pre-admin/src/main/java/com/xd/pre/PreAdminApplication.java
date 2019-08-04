package com.xd.pre;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;


@EnableAsync
@SpringBootApplication
public class PreAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(PreAdminApplication.class, args);
    }

}
