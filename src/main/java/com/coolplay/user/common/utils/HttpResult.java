package com.coolplay.user.common.utils;

import java.util.HashMap;

/**
 * Created by shawn on 2019/09/15.
 */
public class HttpResult<Object> {
    private int code = 0;
    private String message = "操作成功";
    private Object data = (Object) new HashMap<String, Object>();

    public HttpResult() {
    }

    public HttpResult(int code) {
        this.code = code;
    }

    public HttpResult(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public HttpResult(int code, Object data) {
        this.code = code;
        this.data = data;
    }

    public HttpResult(String message, Object data) {
        this.message = message;
        this.data = data;
    }

    public HttpResult(int code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
