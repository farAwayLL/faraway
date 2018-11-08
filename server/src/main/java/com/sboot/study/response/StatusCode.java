package com.sboot.study.response;

/**
 * @author: faraway
 * @date: 2018/11/8 10:13
 * @description:
 */
public enum StatusCode {

    SUCCESS(200,"success"),
    FAIL(500,"fail");

    private Integer code;
    private String msg;

    StatusCode(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
