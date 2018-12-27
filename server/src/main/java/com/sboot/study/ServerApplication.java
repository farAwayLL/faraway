package com.sboot.study;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author: faraway
 * @date: 2018/11/8 9:43
 * @description:
 */
@SpringBootApplication//启动入口
@MapperScan(basePackages = "com.sboot.study.mybatisMapper")//扫描mapper地址
@EnableSwagger2//开启swagger
@ImportResource(locations = "classpath:spring/spring-jdbc.xml")//导入配置文件
@EnableScheduling//开启任务调度
public class ServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServerApplication.class, args);
    }
}
