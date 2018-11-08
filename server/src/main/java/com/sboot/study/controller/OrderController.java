package com.sboot.study.controller;

import com.google.common.collect.Maps;
import com.sboot.study.entity.OrderRecord;
import com.sboot.study.response.BaseResponse;
import com.sboot.study.response.StatusCode;
import com.sboot.study.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author: faraway
 * @date: 2018/11/8 9:53
 * @description:
 */
@RestController
public class OrderController {

    private static final String PREFIX = "order";

    private static final Logger log = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private OrderService orderService;

    @PostMapping(value = PREFIX + "/getOrderList")
    public BaseResponse getOrderList() {
        BaseResponse response = new BaseResponse(StatusCode.SUCCESS);
        try {
            List<OrderRecord> orderRecordList = orderService.getOrderList();
            Map<String, Object> resMap = Maps.newHashMap();
            resMap.put("orderRecordList", orderRecordList);
            response.setData(resMap);
        } catch (Exception e) {
            log.error("查询订单列表失败！", e.fillInStackTrace());
            response = new BaseResponse(StatusCode.FAIL.getCode(), "查询订单列表失败！");
        }
        return response;
    }

}