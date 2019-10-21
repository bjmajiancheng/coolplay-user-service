package com.coolplay.user.user.api.user;

import com.coolplay.user.common.tools.RedisCache;
import com.coolplay.user.common.utils.HttpResponseUtil;
import com.coolplay.user.common.utils.RequestUtil;
import com.coolplay.user.common.utils.ResponseUtil;
import com.coolplay.user.common.utils.Result;
import com.coolplay.user.core.model.UserModel;
import com.coolplay.user.security.api.TokenController;
import com.coolplay.user.security.constants.SecurityConstant;
import com.coolplay.user.security.security.AuthenticationRequest;
import com.coolplay.user.security.security.CoolplayUserCache;
import com.coolplay.user.security.security.HttpAuthenticationDetails;
import com.coolplay.user.security.service.IUserService;
import com.coolplay.user.security.utils.SecurityUtil;
import com.coolplay.user.security.utils.TokenUtils;
import com.coolplay.user.user.model.CompanyLogModel;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * Created by majiancheng on 2019/10/16.
 */
@Controller
@RequestMapping("/api/user")
public class UserController {

    private final static Logger logger = LoggerFactory.getLogger(TokenController.class);

    @Autowired
    private TokenUtils tokenUtils;

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private IUserService userService;

    @Autowired
    private CoolplayUserCache coolplayUserCache;

    @RequestMapping(value = "/loginByMobilePhone", method = RequestMethod.POST)
    public ResponseEntity<?> authenticationRequest(HttpServletRequest request,
            @RequestBody AuthenticationRequest authenticationRequest) throws AuthenticationException {
        /*if (StringUtils.isEmpty(authenticationRequest.getVcode()) || StringUtils
                .isEmpty(authenticationRequest.getVkey())) {
            return ResponseEntity.ok(HttpResponseUtil.error("请输入验证码"));
        }
        if (StringUtils.isNotEmpty((String) redisCache.get(authenticationRequest.getVkey()))) {
            if (!((String) redisCache.get(authenticationRequest.getVkey())).equals(authenticationRequest.getVcode())) {
                return ResponseEntity.ok(HttpResponseUtil.error("验证码不正确"));
            }
        } else {
            return ResponseEntity.ok(HttpResponseUtil.error("验证码不存在或已过期"));
        }
        redisCache.del(authenticationRequest.getVkey());*/
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                authenticationRequest.getMobilePhone(), authenticationRequest.getPassword());
        usernamePasswordAuthenticationToken.setDetails(new HttpAuthenticationDetails());

        Authentication authentication = null;
        try {
            authentication = this.authenticationManager.authenticate(usernamePasswordAuthenticationToken);
            if (authentication == null) {
                return ResponseEntity.ok(HttpResponseUtil.error("未检测到验证信息"));
            }
        } catch (InternalAuthenticationServiceException failed) {
            logger.error("An internal error occurred while trying to authenticate the user.", failed);
            return ResponseEntity.ok(HttpResponseUtil.error(failed.getMessage()));
        } catch (AuthenticationException failed) {
            return ResponseEntity.ok(HttpResponseUtil.error(failed.getMessage()));
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetails userDetails = (UserDetails) redisCache
                .get(SecurityConstant.USER_CACHE_PREFIX + authenticationRequest.getMobilePhone());
        if (userDetails == null)  {
            userDetails = this.userDetailsService.loadUserByUsername(authenticationRequest.getMobilePhone());
            redisCache.set(SecurityConstant.USER_CACHE_PREFIX + authenticationRequest.getMobilePhone(), userDetails);
        }
        String token = this.tokenUtils.generateToken(userDetails);
        userService.updateLastLoginInfoByUserName(authenticationRequest.getMobilePhone(), new Date(),
                RequestUtil.getIpAddress(request));

        return ResponseEntity.ok(HttpResponseUtil.success(token));
    }

    @RequestMapping(value = "/loginByVerifyCode", method = RequestMethod.POST)
    public ResponseEntity<?> authenticationRequestByVerifyCode(HttpServletRequest request,
            @RequestBody AuthenticationRequest authenticationRequest) throws AuthenticationException {
        if (StringUtils.isEmpty(authenticationRequest.getVerifyCode())) {
            return ResponseEntity.ok(HttpResponseUtil.error("请输入验证码"));
        }
        if (StringUtils.isNotEmpty((String) redisCache.get(SecurityConstant.MOBILE_VERIFY_CODE_PREFIX + authenticationRequest.getMobilePhone()))) {
            if (!((String) redisCache.get(SecurityConstant.MOBILE_VERIFY_CODE_PREFIX + authenticationRequest.getMobilePhone())).equals(authenticationRequest.getVerifyCode())) {
                return ResponseEntity.ok(HttpResponseUtil.error("验证码不正确"));
            }
        } else {
            return ResponseEntity.ok(HttpResponseUtil.error("验证码不存在或已过期"));
        }

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                authenticationRequest.getMobilePhone(), authenticationRequest.getPassword());
        usernamePasswordAuthenticationToken.setDetails(new HttpAuthenticationDetails());

        Authentication authentication = null;
        try {
            authentication = this.authenticationManager.authenticate(usernamePasswordAuthenticationToken);
            if (authentication == null) {
                return ResponseEntity.ok(HttpResponseUtil.error("未检测到验证信息"));
            }
        } catch (InternalAuthenticationServiceException failed) {
            logger.error("An internal error occurred while trying to authenticate the user.", failed);
            return ResponseEntity.ok(HttpResponseUtil.error(failed.getMessage()));
        } catch (AuthenticationException failed) {
            return ResponseEntity.ok(HttpResponseUtil.error(failed.getMessage()));
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetails userDetails = (UserDetails) redisCache
                .get(SecurityConstant.USER_CACHE_PREFIX + authenticationRequest.getMobilePhone());
        if (userDetails == null)  {
            userDetails = this.userDetailsService.loadUserByUsername(authenticationRequest.getMobilePhone());
            redisCache.set(SecurityConstant.USER_CACHE_PREFIX + authenticationRequest.getMobilePhone(), userDetails);
        }
        String token = this.tokenUtils.generateToken(userDetails);
        userService.updateLastLoginInfoByUserName(authenticationRequest.getMobilePhone(), new Date(),
                RequestUtil.getIpAddress(request));

        return ResponseEntity.ok(HttpResponseUtil.success(token));
    }

    @RequestMapping(value = "/loginByThirdType", method = RequestMethod.POST)
    public ResponseEntity<?> authenticationRequestByThirdType(HttpServletRequest request,
            @RequestBody AuthenticationRequest authenticationRequest) throws AuthenticationException {
        if (StringUtils.isEmpty(authenticationRequest.getVerifyCode())) {
            return ResponseEntity.ok(HttpResponseUtil.error("请输入验证码"));
        }
        if (StringUtils.isNotEmpty((String) redisCache.get(SecurityConstant.MOBILE_VERIFY_CODE_PREFIX + authenticationRequest.getMobilePhone()))) {
            if (!((String) redisCache.get(SecurityConstant.MOBILE_VERIFY_CODE_PREFIX + authenticationRequest.getMobilePhone())).equals(authenticationRequest.getVerifyCode())) {
                return ResponseEntity.ok(HttpResponseUtil.error("验证码不正确"));
            }
        } else {
            return ResponseEntity.ok(HttpResponseUtil.error("验证码不存在或已过期"));
        }

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                authenticationRequest.getMobilePhone(), authenticationRequest.getPassword());
        usernamePasswordAuthenticationToken.setDetails(new HttpAuthenticationDetails());

        Authentication authentication = null;
        try {
            authentication = this.authenticationManager.authenticate(usernamePasswordAuthenticationToken);
            if (authentication == null) {
                return ResponseEntity.ok(HttpResponseUtil.error("未检测到验证信息"));
            }
        } catch (InternalAuthenticationServiceException failed) {
            logger.error("An internal error occurred while trying to authenticate the user.", failed);
            return ResponseEntity.ok(HttpResponseUtil.error(failed.getMessage()));
        } catch (AuthenticationException failed) {
            return ResponseEntity.ok(HttpResponseUtil.error(failed.getMessage()));
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetails userDetails = (UserDetails) redisCache
                .get(SecurityConstant.USER_CACHE_PREFIX + authenticationRequest.getMobilePhone());
        if (userDetails == null)  {
            userDetails = this.userDetailsService.loadUserByUsername(authenticationRequest.getMobilePhone());
            redisCache.set(SecurityConstant.USER_CACHE_PREFIX + authenticationRequest.getMobilePhone(), userDetails);
        }
        String token = this.tokenUtils.generateToken(userDetails);
        userService.updateLastLoginInfoByUserName(authenticationRequest.getMobilePhone(), new Date(),
                RequestUtil.getIpAddress(request));

        return ResponseEntity.ok(HttpResponseUtil.success(token));
    }

    @ResponseBody
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public Result register(@RequestParam("mobilePhone") String mobilePhone, @RequestParam("verifyCode") String verifyCode, @RequestParam("password") String password) {
        if (StringUtils.isEmpty(verifyCode)) {
            return ResponseUtil.error("请输入验证码");
        }
        if (StringUtils.isNotEmpty((String) redisCache.get(SecurityConstant.MOBILE_VERIFY_CODE_PREFIX + mobilePhone))) {
            if (!((String) redisCache.get(SecurityConstant.MOBILE_VERIFY_CODE_PREFIX + mobilePhone)).equals(verifyCode)) {
                return ResponseUtil.error("验证码不正确");
            }
        } else {
            return ResponseUtil.error("验证码不存在或已过期");
        }

        UserModel userModel = new UserModel();
        userModel.setMobilePhone(mobilePhone);
        userModel.setPassword(SecurityUtil.encodeString(password));
        userModel.setUserName(mobilePhone);
        userModel.setAccountNonLocked(true);
        userModel.setAccountNonExpired(true);
        userModel.setCredentialsNonExpired(true);
        userModel.setEnabled(true);

        int insertCnt = userService.saveNotNull(userModel);

        return ResponseUtil.success("注册成功");
    }

    @ResponseBody
    @RequestMapping(value = "/resetPassword", method = RequestMethod.POST)
    public Result resetPassword(@RequestParam("mobilePhone") String mobilePhone, @RequestParam("verifyCode") String verifyCode, @RequestParam("password") String password) {
        if (StringUtils.isEmpty(verifyCode)) {
            return ResponseUtil.error("请输入验证码");
        }
        if (StringUtils.isNotEmpty((String) redisCache.get(SecurityConstant.MOBILE_VERIFY_CODE_PREFIX + mobilePhone))) {
            if (!((String) redisCache.get(SecurityConstant.MOBILE_VERIFY_CODE_PREFIX + mobilePhone)).equals(verifyCode)) {
                return ResponseUtil.error("验证码不正确");
            }
        } else {
            return ResponseUtil.error("验证码不存在或已过期");
        }

        UserModel userModel = new UserModel();
        userModel.setMobilePhone(mobilePhone);
        userModel.setPassword(SecurityUtil.encodeString(password));
        int updateCnt = userService.updateNotNull(userModel);

        return ResponseUtil.success("修改密码成功");
    }

    @ResponseBody
    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public Result authenticationRequest() {
        Integer userId = SecurityUtil.getCurrentUserId();
        coolplayUserCache.removeUserFromCacheByUserId(userId);
        return ResponseUtil.success();
    }
}
