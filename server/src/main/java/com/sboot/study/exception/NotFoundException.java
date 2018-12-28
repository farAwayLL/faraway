package com.sboot.study.exception;

/**
 * create by faraway on 2018/12/28
 * description:404异常
 */
public class NotFoundException extends Exception {
    public NotFoundException(String message) {
        super(message);
    }
}
