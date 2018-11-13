package com.sboot.study;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author: faraway
 * @date: 2018/11/8 9:43
 * @description:
 */
@SpringBootApplication
@MapperScan(basePackages = "com.sboot.study.mybatisMapper")
@EnableSwagger2
@ImportResource(locations = "classpath:spring/spring-jdbc.xml")
public class ServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServerApplication.class, args);
    }
}
