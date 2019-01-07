package com.sboot.study.controller;

import cn.hutool.core.map.MapUtil;
import com.sboot.study.entity.User;
import com.sboot.study.enums.StatusCode;
import com.sboot.study.mybatisMapper.UserMapper;
import com.sboot.study.response.BaseResponse;
import com.sboot.study.service.UserForRedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * create by faraway on 2018/12/29
 * description:学习使用redis
 */

@RestController
@RequestMapping("/user")
public class UserForRedisController {

    private static final Logger log = LoggerFactory.getLogger(UserForRedisController.class);

    @Autowired
    private UserForRedisService userForRedisService;

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/getUserDetailById/{userId}")
    public BaseResponse getUserDetailById(@PathVariable Integer userId) {
        BaseResponse response = new BaseResponse(StatusCode.SUCCESS);
        try {
            //User user = userForRedisService.getUserDetailByIdV1(userId);
            //User user = userForRedisService.getUserDetailByIdV2(userId);
            //User user = userForRedisService.getUserDetailByIdV3(userId);
            //User user = userForRedisService.getUserDetailByIdV4(userId);
            User user = userForRedisService.getUserDetailByIdV5(userId);
            Map<String, Object> resultMap = MapUtil.newHashMap();
            resultMap.put("user", user);
            response.setData(resultMap);
        } catch (Exception e) {
            e.printStackTrace();
            response = new BaseResponse(StatusCode.FAIL);
        }
        return response;
    }

    /**
     * 如何保证缓存与数据库的一致性？
     * 在对数据库进行更新修改操作时，主动往缓存中更新数据
     */
    @PostMapping("/insertUpdate")
    public BaseResponse insertUpdate(@RequestBody User user) {
        BaseResponse response = new BaseResponse(StatusCode.SUCCESS);

        try {
            if (user.getId()!=null && user.getId()>0){
                userMapper.updateByPrimaryKeySelective(user);
                userForRedisService.insertUpdate(user.getId());
            }else{
                userMapper.insertSelective(user);
                userForRedisService.insertUpdate(user.getId());
            }
        } catch (Exception e) {
            e.printStackTrace();
            response = new BaseResponse(StatusCode.FAIL);
        }
        return response;
    }
}
