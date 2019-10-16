package com.coolplay.company.common.api;

import com.alibaba.fastjson.JSON;
import com.coolplay.company.common.dto.ResetPassDto;
import com.coolplay.company.common.tools.RedisCache;
import com.coolplay.company.common.utils.MessageUtil;
import com.coolplay.company.common.utils.ResponseUtil;
import com.coolplay.company.common.utils.Result;
import com.coolplay.company.company.model.VerifyCodeModel;
import com.coolplay.company.company.service.IVerifyCodeService;
import com.coolplay.company.core.model.UserModel;
import com.coolplay.company.security.constants.SecurityConstant;
import com.coolplay.company.security.service.IUserService;
import com.coolplay.company.security.utils.SecurityUtil;
import com.google.code.kaptcha.Producer;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Date;
import java.util.Random;

/**
 * Created by majiancheng on 2019/9/17.
 */
@Controller
@RequestMapping("/api/noneAuth")
public class NoneAuthController {

    private static final Logger logger = LoggerFactory.getLogger(NoneAuthController.class);

    @Autowired
    private Producer producer;

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private MessageUtil messageUtil;

    @Autowired
    private IVerifyCodeService verifyCodeService;

    @Autowired
    private IUserService userService;

    @RequestMapping("/captcha")
    public void captcha(@RequestParam("vkey") String vkey, HttpServletRequest httpServletRequest,
            HttpServletResponse response) throws IOException {
        response.setHeader("Cache-Control", "no-store, no-cache");
        response.setContentType("image/jpeg");

        //生成文字验证码
        String text = producer.createText();
        //生成图片验证码
        BufferedImage image = producer.createImage(text);

        redisCache.set(vkey, text, 120);

        ServletOutputStream out = response.getOutputStream();
        ImageIO.write(image, "jpg", out);
    }

    /**
     * 发送验证码
     *
     * @param mobilePhone
     */
    @ResponseBody
    @RequestMapping(value = "/sendCaptchaCode", method = RequestMethod.GET)
    public Result sendCaptchaCode(@RequestParam("mobilePhone")String mobilePhone) {
        if(StringUtils.isEmpty(mobilePhone)) {
            return ResponseUtil.error("请输入手机号码");
        }


        UserModel userModel = userService.findUserByLoginName(mobilePhone);
        if(userModel == null) {
            return ResponseUtil.error("用户不存在, 请重新操作...");
        }

        String verifyCode = "";
        Object obj = redisCache.get(SecurityConstant.MOBILE_VERIFY_CODE_PREFIX + mobilePhone);
        if(obj != null) {
            verifyCode = String.valueOf(obj);
        }

        if(StringUtils.isNotEmpty(verifyCode)) {
            return ResponseUtil.error("验证码还在有效期内,请输入之前验证码。");
        }


        verifyCode = String.valueOf(new Random().nextInt(8999) + 1000);

        String key = "sms_0000000003";
        String[] values = {verifyCode};


        String msgContent = messageUtil.getProperty(key, values);

        VerifyCodeModel verifyCodeModel = new VerifyCodeModel();
        verifyCodeModel.setMobilePhone(mobilePhone);
        verifyCodeModel.setVerifyCode(verifyCode);
        verifyCodeModel.setContent(msgContent);
        verifyCodeModel.setCtime(new Date());
        verifyCodeService.saveNotNull(verifyCodeModel);

        //验证码设置缓存信息
        redisCache.set(SecurityConstant.MOBILE_VERIFY_CODE_PREFIX + mobilePhone, verifyCode, SecurityConstant.THREE_MINUTES_EXPIRE_SECOND);

        Result result = messageUtil.sendMessage(mobilePhone, key, values);

        return ResponseUtil.success("验证码发送成功");
    }

    @ResponseBody
    @RequestMapping(value = "/resetPass", method = RequestMethod.POST)
    public Result resetPass(ResetPassDto resetPassDto) {
        if(resetPassDto == null) {
            return ResponseUtil.error("系统异常, 请稍后重试...");
        }

        String verifyCode = String.valueOf(redisCache.get(SecurityConstant.MOBILE_VERIFY_CODE_PREFIX + resetPassDto.getContactPhone()));
        if(StringUtils.isEmpty(verifyCode)) {
            return ResponseUtil.error("验证码已过期, 请重新操作...");
        }

        if(!verifyCode.equals(resetPassDto.getCaptchaCode())) {
            return ResponseUtil.error("验证码不正确, 请修改验证码后重新操作...");
        }

        UserModel userModel = userService.findUserByLoginName(resetPassDto.getContactPhone());
        if(userModel == null) {
            return ResponseUtil.error("用户不存在, 请确定后重新操作");
        }

        UserModel updateUserModel = new UserModel();
        updateUserModel.setId(userModel.getId());
        updateUserModel.setPassword(SecurityUtil.encodeString(resetPassDto.getPassword()));
        userService.updateNotNull(updateUserModel);

        redisCache.del(SecurityConstant.USER_CACHE_PREFIX + userModel.getUserName());

        return ResponseUtil.success();
    }
}
