package com.sboot.study.controller;

import com.google.common.collect.Maps;
import com.sboot.study.utils.HttpClientUtil;

import java.util.Map;

/**
 * @Author : faraway
 * @Date : create in 2018/12/5 16:35
 * @Description : 模拟HTTP请求
 */
public class HttpRequestController {

    private String requestUrl = "www.baidu.com";

    public void test() {
        Map<String, String> params = Maps.newHashMap();
        params.put("startPage","1");
        params.put("pageSize","10");

        //get请求
        String json1 = HttpClientUtil.doGet(requestUrl);
        String json2 = HttpClientUtil.doGet(requestUrl, params);

        //post请求
        String json3 = HttpClientUtil.doPost(requestUrl);
        String json4 = HttpClientUtil.doPost(requestUrl, params);

        String json = "{\"startPage\":\"2\",\"pageSize\":\"2\"}";
        String json5 = HttpClientUtil.doPostJson(requestUrl, json);
    }

}
