package com.sboot.study.controller;

import com.google.common.collect.Maps;
import com.sboot.study.entity.ProductStock;
import com.sboot.study.response.BaseResponse;
import com.sboot.study.response.StatusCode;
import com.sboot.study.service.GroupSelectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * create by faraway on 2018/12/14
 * description:mysql分组查询相关学习
 */
@Api(description = "测试分组且取每组最新数据")
@RestController
public class GroupBySelectController {

    private static final Logger log = LoggerFactory.getLogger(GroupBySelectController.class);

    private static final String PREFIX = "/group";

    @Autowired
    private GroupSelectService groupSelectService;

    /**
     * 需求一：分组查询，且获取每组最新的一条数据
     * 获取不同产品类型的最新一条
     * @return
     */
    @ApiOperation("分组查询，且获取每组最新的一条数据")
    @PostMapping(PREFIX+"/getNewestDataByGorup")
    public BaseResponse getNewestDataByGorup(){
        BaseResponse response = new BaseResponse(StatusCode.SUCCESS);
        try {
            List<ProductStock> productStockList = groupSelectService.getNewestDataByGorup();
            Map<String,Object> returnMap = Maps.newHashMap();
            returnMap.put("productStockList",productStockList);
            response.setData(returnMap);
        } catch (Exception e){
            log.error("获取分组最新商品失败!",e.fillInStackTrace());
            response = new BaseResponse(StatusCode.FAIL.getCode(),"获取分组最新商品失败!");
        }
        return response;
    }
}
