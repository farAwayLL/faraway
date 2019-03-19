package com.sboot.study.shiro;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author faraway
 * @date 2019/3/13 14:53
 */
@Controller
public class IndexController {

    /**
     * 首页
     * @return string
     */
    @RequestMapping("/index")
    public String index() {
        return "index";
    }

    /**
     * 没有权限显示页面
     * @return string
     */
    @RequestMapping("/401")
    public String noAuth() {
        return "401";
    }
}
