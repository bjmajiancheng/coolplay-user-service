package com.coolplay.user.security.security;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import javax.servlet.*;
import java.io.IOException;

public class HttpHeaderFilter implements Filter {

    @Value("${security.token.header}")
    private String tokenHeader;

    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        //HttpServletResponse response = (HttpServletResponse) res;
        //response.setHeader("Access-Control-Allow-Origin", "*");
        //response.setHeader("Access-Control-Allow-Headers",
        //"Origin, X-Requested-With, Content-Type, Accept, " + tokenHeader);

        System.out.println(String.format("前端请求参数:%s.", JSON.toJSONString(req.getParameterMap())));
        chain.doFilter(req, res);
    }

    public void init(FilterConfig filterConfig) {
    }

    public void destroy() {
    }

}
