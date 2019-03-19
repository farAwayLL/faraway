package com.sboot.study.shiro;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author faraway
 * @date 2019/3/12 11:40
 */
@Controller
public class UserController {

    /**
     * 用户添加
     *
     * @return string
     */
    @RequestMapping("/user/add")
    @RequiresPermissions("user:add")
    public String add() {
        return "user/userAdd";
    }

    /**
     * 用户修改
     *
     * @return string
     */
    @RequestMapping("/user/upd")
    @RequiresPermissions("user:upd")
    public String upd() {
        return "user/userUpd";
    }

}
