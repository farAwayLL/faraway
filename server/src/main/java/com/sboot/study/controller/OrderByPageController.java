package com.sboot.study.controller;

import com.sboot.study.response.BaseResponse;
import com.sboot.study.enums.StatusCode;
import com.sboot.study.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author: faraway
 * @date: 2018/11/8 9:53
 * @description: 分页查询
 */
@Api(description = "订单相关")
@RestController
public class OrderByPageController {

    private static final String PREFIX = "order";

    private static final Logger log = LoggerFactory.getLogger(OrderByPageController.class);

    @Autowired
    private OrderService orderService;

    @ApiOperation(value = "获取订单列表")
    @PostMapping(value = PREFIX + "/getOrderList")
    public BaseResponse getOrderList(@RequestBody Map<String, Object> requestMap) {
        BaseResponse response = new BaseResponse(StatusCode.SUCCESS);
        try {
            //如果参数为空
            String startPage = requestMap.get("startPage").toString();
            String pageSize = requestMap.get("pageSize").toString();
            if (StringUtils.isBlank(startPage)) {
                requestMap.put("startPage", 1);
            }
            if (StringUtils.isBlank(pageSize)) {
                requestMap.put("pageSize", 10);
            }
            //参数校验(如果不是正整数返回参数不合法)
            if (requestMap.get("startPage").toString().matches("^[1-9]\\d*$") && requestMap.get("pageSize").toString().matches("^[1-9]\\d*$")) {
                Map<String, Object> returnMap = orderService.getOrderList(requestMap);
                response.setData(returnMap);
            } else {
                response = new BaseResponse(StatusCode.INVALID_PARAMS);
            }
        } catch (Exception e) {
            log.error("查询订单列表失败！", e.fillInStackTrace());
            response = new BaseResponse(StatusCode.FAIL.getCode(), "查询订单列表失败！");
        }
        return response;
    }

}