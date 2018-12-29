package com.sboot.study.controller;

import com.sboot.study.request.ValidateUser;
import com.sboot.study.response.BaseResponse;
import com.sboot.study.enums.StatusCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


/**
 * create by faraway on 2018/12/21
 * description: 测试自定义注解校验参数合法性
 */
@RestController
public class ValidatorController {

    public static final Logger log = LoggerFactory.getLogger(ValidatorController.class);

    public static final String PREFIX = "validate";

    @RequestMapping(value = PREFIX + "/test1", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    //使用@Validated修饰入参对象,BindingResult用于收集错误信息 ValidateUser中字段有具体的自定义注解
    public BaseResponse test1(@RequestBody @Validated ValidateUser user, BindingResult result) {
        BaseResponse response = new BaseResponse(StatusCode.SUCCESS);
        log.info("接受到的前端数据为:{}", user);
        try {
            if (result.hasErrors()) {
                response = new BaseResponse(StatusCode.INVALID_PARAMS);
            }
        } catch (Exception e) {
            response = new BaseResponse(StatusCode.FAIL);
            e.printStackTrace();
        }
        return response;
    }


    @RequestMapping(value = PREFIX + "/test2", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public BaseResponse test2(@RequestBody @Validated ValidateUser user, BindingResult bindResult) {
        BaseResponse response = new BaseResponse(StatusCode.SUCCESS);
        return response;
    }
}
