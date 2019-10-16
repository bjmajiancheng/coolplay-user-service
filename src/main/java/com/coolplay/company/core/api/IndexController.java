package com.coolplay.company.core.api;

import com.coolplay.company.common.tools.RedisCache;
import com.coolplay.company.common.utils.ResponseUtil;
import com.coolplay.company.common.utils.Result;
import com.coolplay.company.security.constants.SecurityConstant;
import com.coolplay.company.security.dto.FunctionDto;
import com.coolplay.company.security.exception.AuBzConstant;
import com.coolplay.company.security.exception.AuthBusinessException;
import com.coolplay.company.security.security.CoolplayUserCache;
import com.coolplay.company.security.security.SecurityUser;
import com.coolplay.company.security.service.IFunctionService;
import com.coolplay.company.security.service.IUserService;
import com.coolplay.company.security.utils.SecurityUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
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
    private IFunctionService functionService;

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private CoolplayUserCache coolplayUserCache;

    @ResponseBody
    @RequestMapping(value = "/index/current", method = RequestMethod.GET)
    public Result myFunction() {
        SecurityUser user = SecurityUtil.getCurrentSecurityUser();
        if (user == null || StringUtils.isEmpty(user.getUsername())) {
            throw new AuthBusinessException(AuBzConstant.IS_NOT_LOGIN);
        }

        String currentLoginName = SecurityUtil.getCurrentUserName();
        if (StringUtils.isEmpty(currentLoginName)) {
            throw new AuthBusinessException(AuBzConstant.IS_NOT_LOGIN);
        }
        List<FunctionDto> functions = (List<FunctionDto>) redisCache
                .get(SecurityConstant.FUNCTION_CACHE_PREFIX + currentLoginName);
        if (functions == null) {
            functions = userService.findUserFunctionByLoginName(currentLoginName);
            redisCache.set(SecurityConstant.FUNCTION_CACHE_PREFIX + currentLoginName, functions);
        }

        Map<String, Object> data = new HashMap<String, Object>();
        data.put("user", user);
        data.put("functionList", functions);

        return ResponseUtil.success(data);
    }

    @ResponseBody
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public Result authenticationRequest() {
        Integer userId = SecurityUtil.getCurrentUserId();
        coolplayUserCache.removeUserFromCacheByUserId(userId);
        return ResponseUtil.success();
    }

    /**
     * 验证某用户是否有权限
     *
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/validate/**", method = RequestMethod.GET)
    public Result htmlPage(HttpServletRequest request) {
        String uri = request.getRequestURI();
        String currentLoginName = SecurityUtil.getCurrentUserName();
        List<FunctionDto> functions = (List<FunctionDto>) redisCache
                .get(SecurityConstant.FUNCTION_CACHE_PREFIX + currentLoginName);
        if (functions == null) {
            functions = userService.findUserFunctionByLoginName(currentLoginName);
            redisCache.set(SecurityConstant.FUNCTION_CACHE_PREFIX + currentLoginName, functions);
        }

        uri = uri.substring(uri.lastIndexOf("/api/validate/") + 14);

        //md5文件替换成普通文件
        String md5Regex = "\\-[a-zA-Z0-9]+\\.html$";
        uri = uri.replaceAll(md5Regex, ".html");
        boolean validateFlag = false;
        for (FunctionDto functionDto : functions) {
            if (functionDto.getAction().equals(uri)) {
                validateFlag = true;
            }
        }

        logger.info("验证用户菜单权限, uri:{}, result:{}.", uri, validateFlag);

        if (!validateFlag) {
            return ResponseUtil.error(HttpServletResponse.SC_UNAUTHORIZED, "未授权访问");
        }

        return ResponseUtil.success();
    }

}
