package com.coolplay.user.common.utils;

import java.util.HashMap;

/**
 * Created by shawn on 2019/09/15.
 */
public class HttpResult<Object> {
    private int code = 0;
    private String token;
    private Object data = (Object) new HashMap<String, Object>();

    public HttpResult() {
    }

    public HttpResult(int code) {
        this.code = code;
    }

    public HttpResult(int code, String token) {
        this.code = code;
        this.token = token;
    }

    public HttpResult(int code, Object data) {
        this.code = code;
        this.data = data;
    }

    public HttpResult(String token, Object data) {
        this.token = token;
        this.data = data;
    }

    public HttpResult(int code, String token, Object data) {
        this.code = code;
        this.token = token;
        this.data = data;
    }


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
