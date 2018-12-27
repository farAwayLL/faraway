//package com.sboot.study.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * 配置跨域方式一 应用场景：前后分离时，在未上测试环境时，前后联调，前端访问本地服务器，解决跨域请求问题
 * Created by Administrator on 2018/9/23.
 *
 *  * Access-Control-Allow-Origin:http://192.168.0.1            表示允许该主机发起跨域请求
 *  * Access-Control-Allow-Credentials:true                     表示是否允许发送cookie
 *  * Access-Control-Allow-Methods:POST, GET, OPTIONS, DELETE   表示允许跨域请求的方式
 *  * Access-Control-Max-Age:100                                表示在100秒内不需要再发送预校验请求
 *  * Access-Control-Allow-Headers:content-type                 表示允许跨域请求包含content-type
 */
//@Configuration
//public class CustomerMvcConfig extends WebMvcConfigurerAdapter {
//
//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**")  //允许所有，包含以下四个，以下四个可写可不写
//                .allowedOrigins("*")
//                .allowedHeaders("*")
//                .allowCredentials(true)
//                .allowedMethods("*");
//    }
//}
