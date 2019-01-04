//package com.sboot.study.config;


import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Administrator on 2018/9/23.
 * 配置跨域方式二 应用场景：前后分离时，在未上测试环境时，前后联调，前端访问本地服务器，解决跨域请求问题
 *
 * Access-Control-Allow-Origin:http://192.168.0.1            表示允许该主机发起跨域请求
 * Access-Control-Allow-Credentials:true                     表示是否允许发送cookie
 * Access-Control-Allow-Methods:POST, GET, OPTIONS, DELETE   表示允许跨域请求的方式
 * Access-Control-Max-Age:100                                表示在100秒内不需要再发送预校验请求
 * Access-Control-Allow-Headers:content-type                 表示允许跨域请求包含content-type
 */
//@Component
//public class CustomerMvcConfigV2 implements Filter {
//
//    public void init(FilterConfig filterConfig) throws ServletException {
//    }
//
//    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
//        HttpServletResponse response = (HttpServletResponse) res;
//        HttpServletRequest request = (HttpServletRequest) req;
//
        /**简单来说，CORS是一种访问机制，英文全称是Cross-Origin Resource Sharing*/
        /**即我们常说的跨域资源共享，通过在服务器端设置响应头，把发起跨域的原始域名添加到Access-Control-Allow-Origin 即可*/
//        response.setHeader("Access-Control-Allow-Origin",request.getHeader("*"));
//        response.setHeader("Access-Control-Allow-Credentials", "true");
//        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
//        response.setHeader("Access-Control-Max-Age", "3600");
//        response.setHeader("Access-Control-Allow-Headers", "accept,x-requested-with,content-type");
//        chain.doFilter(req, res);
//    }
//
//    public void destroy() {
//    }
//}
