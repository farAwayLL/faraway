package com.sboot.study.shiro;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * 全局异常处理
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value={org.apache.shiro.authz.UnauthorizedException.class})
    public String noAuth() {
        return "401";
    }
}
