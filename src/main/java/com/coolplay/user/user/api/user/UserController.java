package com.coolplay.user.user.api.user;

import com.coolplay.user.common.constant.CommonConstant;
import com.coolplay.user.common.tools.RedisCache;
import com.coolplay.user.common.utils.*;
import com.coolplay.user.core.model.UserModel;
import com.coolplay.user.security.api.TokenController;
import com.coolplay.user.security.constants.SecurityConstant;
import com.coolplay.user.security.security.AuthenticationRequest;
import com.coolplay.user.security.security.CoolplayUserCache;
import com.coolplay.user.security.security.HttpAuthenticationDetails;
import com.coolplay.user.security.service.IUserService;
import com.coolplay.user.security.utils.SecurityUtil;
import com.coolplay.user.security.utils.TokenUtils;
import com.coolplay.user.user.model.*;
import com.coolplay.user.user.service.*;
import org.apache.commons.collections.CollectionUtils;
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
import java.util.*;

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

    @Autowired
    private IUserPassMappingService userPassMappingService;

    @Autowired
    private ILabelService labelService;

    @Autowired
    private IUserLabelService userLabelService;

    @Autowired
    private IUserFansService userFansService;

    @Autowired
    private IPostService postService;

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

        UserModel userModel = userService.findUserByMobilePhone(authenticationRequest.getMobilePhone());
        if (userModel == null) {
            return ResponseEntity.ok(HttpResponseUtil.error("用户不存在"));
        }

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                userModel.getUserName(), authenticationRequest.getPassword());
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
                .get(SecurityConstant.USER_CACHE_PREFIX + userModel.getUserName());
        if (userDetails == null) {
            userDetails = this.userDetailsService.loadUserByUsername(userModel.getUserName());
            redisCache.set(SecurityConstant.USER_CACHE_PREFIX + userModel.getUserName(), userDetails);
        }
        String token = this.tokenUtils.generateToken(userDetails);
        userService
                .updateLastLoginInfoByUserName(userModel.getUserName(), new Date(), RequestUtil.getIpAddress(request));

        UserModel userDetailInfo = getUserDetailInfo(userModel.getId());
        userDetailInfo.setToken(token);

        return ResponseEntity.ok(HttpResponseUtil.success(userDetailInfo));
    }

    @RequestMapping(value = "/loginByVerifyCode", method = RequestMethod.POST)
    public ResponseEntity<?> authenticationRequestByVerifyCode(HttpServletRequest request,
            @RequestBody AuthenticationRequest authenticationRequest) throws AuthenticationException {
        if (StringUtils.isEmpty(authenticationRequest.getVerifyCode())) {
            return ResponseEntity.ok(HttpResponseUtil.error("请输入验证码"));
        }
        if (StringUtils.isNotEmpty((String) redisCache
                .get(SecurityConstant.MOBILE_VERIFY_CODE_PREFIX + authenticationRequest.getMobilePhone()))) {
            if (!((String) redisCache
                    .get(SecurityConstant.MOBILE_VERIFY_CODE_PREFIX + authenticationRequest.getMobilePhone()))
                    .equals(authenticationRequest.getVerifyCode())) {
                return ResponseEntity.ok(HttpResponseUtil.error("验证码不正确"));
            }
        } else {
            return ResponseEntity.ok(HttpResponseUtil.error("验证码不存在或已过期"));
        }

        UserModel userModel = userService.findUserByMobilePhone(authenticationRequest.getMobilePhone());
        if (userModel == null) {
            return ResponseEntity.ok(HttpResponseUtil.error("用户不存在"));
        }

        UserPassMappingModel userPassMappingModel = userPassMappingService
                .findByPasswordEncode(userModel.getPassword());
        if (userPassMappingModel == null) {
            return ResponseEntity.ok(HttpResponseUtil.error("系统异常, 请稍后重试"));
        }

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                userModel.getUserName(), userPassMappingModel.getPassword());
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
                .get(SecurityConstant.USER_CACHE_PREFIX + userModel.getUserName());
        if (userDetails == null) {
            userDetails = this.userDetailsService.loadUserByUsername(userModel.getUserName());
            redisCache.set(SecurityConstant.USER_CACHE_PREFIX + userModel.getUserName(), userDetails);
        }
        String token = this.tokenUtils.generateToken(userDetails);
        userService
                .updateLastLoginInfoByUserName(userModel.getUserName(), new Date(), RequestUtil.getIpAddress(request));

        UserModel userDetailInfo = getUserDetailInfo(userModel.getId());
        userDetailInfo.setToken(token);

        return ResponseEntity.ok(HttpResponseUtil.success(userDetailInfo));
        //return ResponseEntity.ok(HttpResponseUtil.success(token));
    }

    /**
     * 根据第三方信息登录
     *
     * @param request
     * @param authenticationRequest
     * @return
     * @throws AuthenticationException
     */
    @RequestMapping(value = "/loginByThirdType", method = RequestMethod.POST)
    public ResponseEntity<?> authenticationRequestByThirdType(HttpServletRequest request,
            @RequestBody AuthenticationRequest authenticationRequest) throws AuthenticationException {

        if (StringUtils.isEmpty(authenticationRequest.getThirdId()) || authenticationRequest.getThirdType() == null) {
            return ResponseEntity.ok(HttpResponseUtil.error("请输入第三方登录信息"));
        }

        if (!CommonConstant.ALLOW_THIRD_TYPE.contains(authenticationRequest.getThirdType())) {
            return ResponseEntity.ok(HttpResponseUtil.error("第三方登录信息输入有误"));
        }

        UserModel userModel = userService
                .findUserByThirdInfo(authenticationRequest.getThirdId(), authenticationRequest.getThirdType());
        if (userModel == null) {
            userModel = new UserModel();
            String uuid = UUIDUtils.generUUID();
            userModel.setMobilePhone(uuid);
            userModel.setUserName(uuid);
            userModel.setMobilePhone(uuid);
            String passwordEncode = SecurityUtil.encodeString(uuid);
            userModel.setPassword(passwordEncode);
            if (authenticationRequest.getThirdType() == 1) {
                userModel.setWechatId(authenticationRequest.getThirdId());
            } else if (authenticationRequest.getThirdType() == 2) {
                userModel.setQqId(authenticationRequest.getThirdId());
            }
            userModel.setAccountNonLocked(true);
            userModel.setAccountNonExpired(true);
            userModel.setCredentialsNonExpired(true);
            userModel.setEnabled(true);
            userModel.setLastPasswordReset(new Date());

            int insertCnt = userService.saveNotNull(userModel);

            UserPassMappingModel userPassMappingModel = new UserPassMappingModel();
            userPassMappingModel.setPassword(uuid);
            userPassMappingModel.setPasswordEncode(passwordEncode);
            userPassMappingService.insert(userPassMappingModel);
        }

        UserPassMappingModel userPassMappingModel = userPassMappingService
                .findByPasswordEncode(userModel.getPassword());
        if (userPassMappingModel == null) {
            return ResponseEntity.ok(HttpResponseUtil.error("系统异常, 请稍后重试"));
        }

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                userModel.getUserName(), userPassMappingModel.getPassword());
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
                .get(SecurityConstant.USER_CACHE_PREFIX + userModel.getUserName());
        if (userDetails == null) {
            userDetails = this.userDetailsService.loadUserByUsername(userModel.getUserName());
            redisCache.set(SecurityConstant.USER_CACHE_PREFIX + userModel.getUserName(), userDetails);
        }
        String token = this.tokenUtils.generateToken(userDetails);
        userService
                .updateLastLoginInfoByUserName(userModel.getUserName(), new Date(), RequestUtil.getIpAddress(request));

        UserModel userDetailInfo = getUserDetailInfo(userModel.getId());
        userDetailInfo.setToken(token);

        return ResponseEntity.ok(HttpResponseUtil.success(userDetailInfo));
        //return ResponseEntity.ok(HttpResponseUtil.success(token));
    }

    /**
     * 用户注册
     *
     * @param mobilePhone
     * @param verifyCode
     * @param password
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public Result register(@RequestParam("mobilePhone") String mobilePhone,
            @RequestParam("verifyCode") String verifyCode, @RequestParam("password") String password) {
        if (StringUtils.isEmpty(verifyCode)) {
            return ResponseUtil.error("请输入验证码");
        }
        if (StringUtils.isNotEmpty((String) redisCache.get(SecurityConstant.MOBILE_VERIFY_CODE_PREFIX + mobilePhone))) {
            if (!((String) redisCache.get(SecurityConstant.MOBILE_VERIFY_CODE_PREFIX + mobilePhone))
                    .equals(verifyCode)) {
                return ResponseUtil.error("验证码不正确");
            }
        } else {
            return ResponseUtil.error("验证码不存在或已过期");
        }
        UserModel userInfo = userService.findUserByMobilePhone(mobilePhone);
        if (userInfo != null) {
            return ResponseUtil.error("手机号已使用, 请更换手机号.");
        }

        UserModel userModel = new UserModel();
        userModel.setMobilePhone(mobilePhone);
        String passwordEncode = SecurityUtil.encodeString(password);
        userModel.setPassword(passwordEncode);
        userModel.setUserName(mobilePhone);
        userModel.setNickName(mobilePhone.substring(3, 7));
        userModel.setAccountNonLocked(true);
        userModel.setAccountNonExpired(true);
        userModel.setCredentialsNonExpired(true);
        userModel.setEnabled(true);
        userModel.setLastPasswordReset(new Date());

        int insertCnt = userService.saveNotNull(userModel);

        UserPassMappingModel userPassMappingModel = new UserPassMappingModel();
        userPassMappingModel.setPassword(password);
        userPassMappingModel.setPasswordEncode(passwordEncode);
        userPassMappingService.insert(userPassMappingModel);

        return ResponseUtil.success("注册成功");
    }

    /**
     * 重置密码
     *
     * @param mobilePhone
     * @param verifyCode
     * @param password
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/resetPassword", method = RequestMethod.POST)
    public Result resetPassword(@RequestParam("mobilePhone") String mobilePhone,
            @RequestParam("verifyCode") String verifyCode, @RequestParam("password") String password) {
        if (StringUtils.isEmpty(verifyCode)) {
            return ResponseUtil.error("请输入验证码");
        }
        if (StringUtils.isNotEmpty((String) redisCache.get(SecurityConstant.MOBILE_VERIFY_CODE_PREFIX + mobilePhone))) {
            if (!((String) redisCache.get(SecurityConstant.MOBILE_VERIFY_CODE_PREFIX + mobilePhone))
                    .equals(verifyCode)) {
                return ResponseUtil.error("验证码不正确");
            }
        } else {
            return ResponseUtil.error("验证码不存在或已过期");
        }

        UserModel userInfo = userService.findUserByMobilePhone(mobilePhone);
        if (userInfo == null) {
            return ResponseUtil.error("账户不存在");
        }

        UserModel userModel = new UserModel();
        userModel.setId(userInfo.getId());
        String passwordEncode = SecurityUtil.encodeString(password);
        userModel.setPassword(passwordEncode);
        userModel.setLastPasswordReset(new Date());
        int updateCnt = userService.updateNotNull(userModel);

        UserPassMappingModel userPassMappingModel = new UserPassMappingModel();
        userPassMappingModel.setPassword(password);
        userPassMappingModel.setPasswordEncode(passwordEncode);
        userPassMappingService.insert(userPassMappingModel);

        return ResponseUtil.success("修改密码成功");
    }

    /**
     * 重置密码
     *
     * @param mobilePhone
     * @param verifyCode
     * @param password
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/updatePassword", method = RequestMethod.POST)
    public Result updatePassword(@RequestParam("mobilePhone") String mobilePhone,
            @RequestParam("verifyCode") String verifyCode, @RequestParam("password") String password) {
        if (StringUtils.isEmpty(verifyCode)) {
            return ResponseUtil.error("请输入验证码");
        }
        if (StringUtils.isNotEmpty((String) redisCache.get(SecurityConstant.MOBILE_VERIFY_CODE_PREFIX + mobilePhone))) {
            if (!((String) redisCache.get(SecurityConstant.MOBILE_VERIFY_CODE_PREFIX + mobilePhone))
                    .equals(verifyCode)) {
                return ResponseUtil.error("验证码不正确");
            }
        } else {
            return ResponseUtil.error("验证码不存在或已过期");
        }

        UserModel userInfo = userService.findUserByMobilePhone(mobilePhone);
        if (userInfo == null) {
            return ResponseUtil.error("账户不存在");
        }

        UserModel userModel = new UserModel();
        userModel.setId(userInfo.getId());
        String passwordEncode = SecurityUtil.encodeString(password);
        userModel.setPassword(passwordEncode);
        userModel.setLastPasswordReset(new Date());
        int updateCnt = userService.updateNotNull(userModel);

        UserPassMappingModel userPassMappingModel = new UserPassMappingModel();
        userPassMappingModel.setPassword(password);
        userPassMappingModel.setPasswordEncode(passwordEncode);
        userPassMappingService.insert(userPassMappingModel);

        return ResponseUtil.success("修改密码成功");
    }

    @ResponseBody
    @RequestMapping(value = "/userDetail", method = RequestMethod.POST)
    public Result userDetail(@RequestParam("id") Integer id) {

        return ResponseUtil.success(getUserDetailInfo(id));
    }

    /**
     * 退出登录
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public Result logout() {
        Integer userId = SecurityUtil.getCurrentUserId();
        coolplayUserCache.removeUserFromCacheByUserId(userId);
        return ResponseUtil.success();
    }

    /**
     * 获取用户详细信息
     *
     * @param id
     * @return
     */
    private UserModel getUserDetailInfo(Integer id) {
        UserModel userModel = userService.findById(id);

        Map<Integer, List<LabelModel>> labelModels = labelService.findMapByUserIds(Collections.singletonList(id));
        if (CollectionUtils.isNotEmpty(labelModels.get(id))) {
            userModel.setLabelList(labelModels.get(id));
        }

        return userModel;
    }

    /**
     * 更新用户信息
     *
     * @param userModel
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/updateUser", method = RequestMethod.POST)
    public Result updateUser(@RequestBody UserModel userModel) {

        int currUserId = SecurityUtil.getCurrentUserId();
        if (userModel.getId() != currUserId) {
            return ResponseUtil.error("无权限修改其他用户详细信息");
        }

        if (CollectionUtils.isNotEmpty(userModel.getIdCardImageArr())) {
            StringBuffer sb = new StringBuffer();
            for (String idCardImage : userModel.getIdCardImageArr()) {
                if (sb.length() > 0) {
                    sb.append(",");
                }

                sb.append(idCardImage);
            }

            userModel.setIdCardImages(sb.toString());
        }

        int delCnt = userLabelService.delByUserId(userModel.getId());
        if (CollectionUtils.isNotEmpty(userModel.getLabelIds())) {
            for (Integer labelId : userModel.getLabelIds()) {
                UserLabelModel userLabelModel = new UserLabelModel();
                userLabelModel.setUserId(currUserId);
                userLabelModel.setLabelId(labelId);
                userLabelService.saveNotNull(userLabelModel);
            }
        }

        return ResponseUtil.success();
    }

    @ResponseBody
    @RequestMapping(value = "/updateMobilePhone", method = RequestMethod.POST)
    public Result updateMobilePhone(@RequestParam("id") Integer id,
            @RequestParam("mobilePhone") String mobilePhone,
            @RequestParam("verifyCode") String verifyCode) {
        if(id != SecurityUtil.getCurrentUserId()) {
            return ResponseUtil.error("无权限修改其他用户手机号码");
        }

        if (StringUtils.isEmpty(verifyCode)) {
            return ResponseUtil.error("请输入验证码");
        }
        if (StringUtils.isNotEmpty((String) redisCache.get(SecurityConstant.MOBILE_VERIFY_CODE_PREFIX + mobilePhone))) {
            if (!((String) redisCache.get(SecurityConstant.MOBILE_VERIFY_CODE_PREFIX + mobilePhone))
                    .equals(verifyCode)) {
                return ResponseUtil.error("验证码不正确");
            }
        } else {
            return ResponseUtil.error("验证码不存在或已过期");
        }

        UserModel userInfo = userService.findUserByUserId(id);
        if (userInfo == null) {
            return ResponseUtil.error("账户不存在");
        }

        UserModel userModel = new UserModel();
        userModel.setId(id);
        userModel.setMobilePhone(mobilePhone);
        int updateCnt = userService.updateNotNull(userModel);

        return ResponseUtil.success();
    }

    /**
     * 通知信息调整
     *
     * @param allowMessage
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/allowMessage", method=RequestMethod.POST)
    public Result allowMessage(@RequestParam("userId") Integer userId, @RequestParam("allowMessage") Integer allowMessage) {
        if(userId != SecurityUtil.getCurrentUserId()) {
            return ResponseUtil.error("无权限修改其他用户信息");
        }

        UserModel userModel = new UserModel();
        userModel.setId(userId);
        userModel.setAllowMessage(allowMessage);

        int updateCnt = userService.updateNotNull(userModel);

        return ResponseUtil.success();
    }

    @ResponseBody
    @RequestMapping(value = "/userSpaceDetail", method = RequestMethod.POST)
    public Result userSpaceDetail(@RequestParam("userId")Integer userId) {
        UserModel userModel = userService.findById(userId);

        int fansCnt = userFansService.findCntByUserId(userId);
        int followCnt = userFansService.findCntByFansUserId(userId);

        userModel.setFansCnt(fansCnt);
        userModel.setFollowCnt(followCnt);

        Map<Integer, List<Integer>> followUserMap = userFansService.getFollowMapByFansUserIds(Collections.singletonList(SecurityUtil.getCurrentUserId()));
        List<Integer> followUsers = followUserMap.get(SecurityUtil.getCurrentUserId());
        if(CollectionUtils.isNotEmpty(followUsers) && followUsers.contains(userId)) {
            userModel.setIsFans(1);
        }

        PostModel postModel = new PostModel();
        postModel.setUserId(userId);
        postModel.setIsDel(0);
        List<PostModel> dynamicList = postService.selectByFilter(postModel);
        userModel.setDynamicList(dynamicList);

        return ResponseUtil.success(userModel);
    }
}
