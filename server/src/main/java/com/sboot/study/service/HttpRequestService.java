package com.sboot.study.service;

import com.google.common.collect.Maps;
import com.sboot.study.utils.HttpClientUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @Author : faraway
 * @Date : create in 2018/12/5 16:35
 * @Description : 模拟HTTP请求 1.加入依赖 2.创建HttpClientUtils工具类 3.在某个业务层需要调用的时候调用
 */
@RestController
public class HttpRequestService {

    @Value("${http.request}")
    private String requestUrl;

    public void test() {
        Map<String,String> params = Maps.newHashMap();
        String json = "";

        //get请求
        String json1 = HttpClientUtil.doGet("url");
        String json2 = HttpClientUtil.doGet("url",params);

        //post请求
        String json3 = HttpClientUtil.doPost("url");
        String json4 = HttpClientUtil.doPost("url",params);
        String json5 = HttpClientUtil.doPostJson("url",json);
    }

}
