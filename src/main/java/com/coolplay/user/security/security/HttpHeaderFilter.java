package com.coolplay.user.security.security;

import com.alibaba.fastjson.JSON;
import com.coolplay.user.common.utils.DateStyle;
import com.coolplay.user.common.utils.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;

public class HttpHeaderFilter implements Filter {

    @Value("${security.token.header}")
    private String tokenHeader;

    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        //HttpServletResponse response = (HttpServletResponse) res;
        //response.setHeader("Access-Control-Allow-Origin", "*");
        //response.setHeader("Access-Control-Allow-Headers",
        //"Origin, X-Requested-With, Content-Type, Accept, " + tokenHeader);

        HttpServletRequest httpRequest = (HttpServletRequest) req;

        System.out.println(String.format("前端 请求时间:%s, 请求链接:%s, 请求参数:%s, 请求body:%s.",
                DateUtil.DateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS), httpRequest.getRequestURL(),
                JSON.toJSONString(httpRequest.getParameterMap()), ""));

        chain.doFilter(req, res);
    }

    public void init(FilterConfig filterConfig) {
    }

    public void destroy() {
    }

    public static String readAsChars(HttpServletRequest request) {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(request.getInputStream(), "UTF-8"));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        String line = null;
        StringBuilder sb = new StringBuilder();
        try {
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return sb.toString();
    }
}
