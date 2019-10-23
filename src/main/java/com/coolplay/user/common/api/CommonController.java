package com.coolplay.user.common.api;

import com.coolplay.user.common.service.IAttachmentService;
import com.coolplay.user.common.tools.RedisCache;
import com.coolplay.user.common.utils.CommonUtil;
import com.coolplay.user.common.utils.MessageUtil;
import com.coolplay.user.common.utils.ResponseUtil;
import com.coolplay.user.common.utils.Result;
import com.coolplay.user.core.model.Attachment;
import com.coolplay.user.security.constants.SecurityConstant;
import com.coolplay.user.security.service.IUserService;
import com.coolplay.user.user.model.SystemVersionModel;
import com.coolplay.user.user.model.VerifyCodeModel;
import com.coolplay.user.user.service.ISystemVersionService;
import com.coolplay.user.user.service.IVerifyCodeService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;

/**
 * Created by majiancheng on 2019/9/23.
 */
@Controller
@RequestMapping("/api/common")
public class CommonController {

    private final static Integer IMAGE = 0;

    private final static Integer FILE = 1;

    @Autowired
    private IAttachmentService attachmentService;

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private MessageUtil messageUtil;

    @Autowired
    private IVerifyCodeService verifyCodeService;

    @Autowired
    private IUserService userService;

    @Autowired
    private ISystemVersionService systemVersionService;

    @RequestMapping(value = "/uploadFile", method = { RequestMethod.POST })
    @ResponseBody
    public Result uploadFile(HttpServletRequest request,
            @RequestParam(value = "file", required = false) MultipartFile file) throws IOException {
        try {
            if (file == null || file.isEmpty()) {
                return ResponseUtil.error("请选择文件。");
            }
            //文件夹名
            String dirName = "file";
            // 最大文件大小
            long maxSize = 20000000;

            // 定义允许上传的文件扩展名
            HashMap<String, String> extMap = new HashMap<String, String>();
            extMap.put(dirName,
                    "doc,docx,xls,xlsx,ppt,pptx,txt,zip,rar,gz,bz2,gif,jpg,jpeg,png,bmp,swf,flv,mp3,wav,wma,wmv,mid,avi,mpg,asf,rm,rmvb");

            Attachment attachment = attachmentService
                    .uploadFileAttachement(request, file, dirName, maxSize, extMap, FILE);
            return ResponseUtil.success(attachment);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseUtil.error("上传失败。");
        }
    }

    @RequestMapping(value = "/uploadImage", method = { RequestMethod.POST })
    @ResponseBody
    public Result uploadImage(HttpServletRequest request,
            @RequestParam(value = "file", required = false) MultipartFile file) throws IOException {
        try {
            if (file == null || file.isEmpty()) {
                return ResponseUtil.error("请选择文件。");
            }
            //文件夹名
            String dirName = "image";
            // 最大文件大小
            long maxSize = 10000000;

            // 定义允许上传的文件扩展名
            HashMap<String, String> extMap = new HashMap<String, String>();
            extMap.put(dirName, "gif,jpg,jpeg,png,bmp");

            Attachment attachment = attachmentService
                    .uploadFileAttachement(request, file, dirName, maxSize, extMap, IMAGE);
            return ResponseUtil.success(attachment);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseUtil.error("上传失败。");
        }
    }

    @RequestMapping(value = "/uploadFiles", method = RequestMethod.POST)
    @ResponseBody
    public Object uploadFiles(HttpServletRequest request,
            @RequestParam(value = "files[]", required = false) MultipartFile[] file) throws Exception {
        if (file.length > 0) {
            for (MultipartFile multipartFile : file) {
                //文件夹名
                String dirName = "file";
                // 最大文件大小
                long maxSize = 20000000;

                // 定义允许上传的文件扩展名
                HashMap<String, String> extMap = new HashMap<String, String>();
                extMap.put(dirName,
                        "doc,docx,xls,xlsx,ppt,pptx,txt,zip,rar,gz,bz2,gif,jpg,jpeg,png,bmp,swf,flv,mp3,wav,wma,wmv,mid,avi,mpg,asf,rm,rmvb");

                Attachment attachment = attachmentService
                        .uploadFileAttachement(request, multipartFile, dirName, maxSize, extMap, FILE);
                return attachment;
            }
        }
        return false;
    }

    @RequestMapping(value = "/attachment", method = RequestMethod.POST)
    @ResponseBody
    public Result attachment(@RequestParam("attachmentId") Integer attachmentId) throws Exception {
        if (attachmentId != null) {
            Attachment attachment = attachmentService.selectByKey(attachmentId);
            if (attachment != null) {
                return ResponseUtil.success(attachment);
            }
        }
        return ResponseUtil.error("附件id不存在。");
    }

    /**
     * 发送验证码
     *
     * @param param
     */
    @ResponseBody
    @RequestMapping(value = "/verifyCode/sendVerifyCode", method = RequestMethod.POST)
    public Result sendCaptchaCode(Map<String, Object> param) {
        String mobilePhone = CommonUtil.defaultString(param.get("mobilePhone"), "");
        if(StringUtils.isEmpty(mobilePhone)) {
            return ResponseUtil.error("请输入手机号码");
        }

        /*UserModel userModel = userService.findUserByLoginName(mobilePhone);
        if(userModel == null) {
            return ResponseUtil.error("用户不存在, 请重新操作...");
        }*/

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
    @RequestMapping(value = "/lastVersion", method = RequestMethod.POST)
    public Result lastVersion() {
        List<SystemVersionModel> systemVersions = systemVersionService.selectByFilter(new SystemVersionModel());
        Map<String, SystemVersionModel> systemVersionMap = new HashMap<String, SystemVersionModel>();

        if(CollectionUtils.isNotEmpty(systemVersions)) {
            for(SystemVersionModel systemVersion : systemVersions) {
                systemVersionMap.put(systemVersion.getAppType(), systemVersion);
            }
        }

        return ResponseUtil.success(systemVersionMap);
    }

}
