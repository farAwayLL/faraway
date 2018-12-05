package com.sboot.study.service;

import com.google.common.collect.Maps;
import com.sboot.study.response.BaseResponse;
import com.sboot.study.utils.HttpClientUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

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
        //get请求
        String s = HttpClientUtils.doGet("http://10.3.2.23:8899/ws/Information.asmx/GtInformation?idcard=9AF0FCDD63EB6AB89BF295F575176955062E478E97BBE21B");
        //post请求
        Map<String,Object> params = Maps.newHashMap();
        BaseResponse response = postRestTemplate(requestUrl + "/activity/myactivitylist", params);
    }

    /**
     * POST请求
     */
    private BaseResponse postRestTemplate(String url, Object params) {
        return new RestTemplate().postForEntity(url, params, BaseResponse.class).getBody();
    }
}
