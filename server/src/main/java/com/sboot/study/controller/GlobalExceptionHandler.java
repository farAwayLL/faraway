package com.sboot.study.controller;

import com.google.common.collect.Maps;
import com.sboot.study.exception.NotFoundException;
import com.sboot.study.exception.SystemException;
import com.sboot.study.response.BaseResponse;
import com.sboot.study.enums.StatusCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * create by faraway on 2018/12/28
 * description:全局异常处理
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(value = SystemException.class)
    @ResponseBody
    public BaseResponse systemException(Exception e, HttpServletRequest request){
        BaseResponse response = new BaseResponse(StatusCode.FAIL);
        Map<String,Object> resultMap = Maps.newHashMap();
        resultMap.put("errorMessage",e.getMessage());
        resultMap.put("uri",request.getRequestURI());
        response.setData(resultMap);
        return response;
    }

    @ExceptionHandler(value = NotFoundException.class)
    public String notFoundPage(Exception e, HttpServletRequest request){
        log.info("异常信息：{} ",e.getMessage());
        request.setAttribute("errorInfo",e.getMessage());
        return "404";
    }

}
