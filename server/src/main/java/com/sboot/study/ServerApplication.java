package com.sboot.study;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author: faraway
 * @date: 2018/11/8 9:43
 * @description:
 */
@SpringBootApplication
@MapperScan(basePackages = "com.sboot.study.mapper")
public class ServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServerApplication.class, args);
    }
}
