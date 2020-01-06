package com.coolplay.user.security.security;

import com.alibaba.fastjson.JSON;
import com.coolplay.user.common.utils.DateStyle;
import com.coolplay.user.common.utils.DateUtil;
import com.coolplay.user.common.utils.HttpResponseUtil;
import com.coolplay.user.security.utils.BaseWebUtils;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

/**
 * Created by majiancheng on 2019/9/15.
 */
public class HttpAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
            AuthenticationException e) throws IOException, ServletException {

        String parameters = "";

        if (BaseWebUtils.isJsonPost(httpServletRequest)) {
            httpServletRequest = new MultiReadHttpServletRequest(httpServletRequest);

            parameters = BaseWebUtils.getParameters(httpServletRequest);
        }

        System.out.println(String.format("未授权访问请求. 前端 请求时间:[%s], 请求链接:[%s], 请求参数:%s, 请求body: %s.",
                DateUtil.DateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS), httpServletRequest.getRequestURL(),
                JSON.toJSONString(httpServletRequest.getParameterMap()), parameters));

        HttpResponseUtil.error(httpServletResponse, HttpServletResponse.SC_UNAUTHORIZED, "未授权访问");
    }
}
