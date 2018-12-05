package com.sboot.study.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author : faraway
 * @Date : create in 2018/12/3 9:41
 * @Description :网站首页
 */

@RestController
@RequestMapping("/")
public class IndexController {

    @RequestMapping("/")
    public String index() {
        return "<center><h1>欢迎来到浪的飞起个人网！</h1></center>";
    }
}
