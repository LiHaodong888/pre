package com.xd.pre;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class PreApplication {

    public static void main(String[] args) {
        SpringApplication.run(PreApplication.class, args);
    }


}
