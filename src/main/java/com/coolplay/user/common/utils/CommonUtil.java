package com.coolplay.user.common.utils;

/**
 * Created by majiancheng on 2019/10/23.
 */
public class CommonUtil {

    public static String defaultString(Object obj, String defaultVal) {
        if(obj == null) {
            return defaultVal;
        }

        return String.valueOf(obj);
    }

    public static Integer defaultInteger(Object obj, Integer defaultVal) {
        if(obj == null) {
            return defaultVal;
        }

        return Integer.parseInt(String.valueOf(obj));
    }
}
