package com.coolplay.user.core.api;

import com.coolplay.user.common.tools.RedisCache;
import com.coolplay.user.common.utils.ResponseUtil;
import com.coolplay.user.common.utils.Result;
import com.coolplay.user.security.constants.SecurityConstant;
import com.coolplay.user.security.dto.FunctionDto;
import com.coolplay.user.security.exception.AuBzConstant;
import com.coolplay.user.security.exception.AuthBusinessException;
import com.coolplay.user.security.security.CoolplayUserCache;
import com.coolplay.user.security.security.SecurityUser;
import com.coolplay.user.security.service.IUserService;
import com.coolplay.user.security.utils.SecurityUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by majiancheng on 2019/9/18.
 */
@Controller
@RequestMapping("/api")
public class IndexController {

    private static final Logger logger = LoggerFactory.getLogger(IndexController.class);

    @Autowired
    private IUserService userService;

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private CoolplayUserCache coolplayUserCache;


    @ResponseBody
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public Result authenticationRequest() {
        Integer userId = SecurityUtil.getCurrentUserId();
        coolplayUserCache.removeUserFromCacheByUserId(userId);
        return ResponseUtil.success();
    }

}
