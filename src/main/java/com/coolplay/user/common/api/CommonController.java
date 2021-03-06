package com.coolplay.user.common.api;

import com.alibaba.fastjson.JSON;
import com.coolplay.user.common.dto.BaseOption;
import com.coolplay.user.common.dto.CompanyOption;
import com.coolplay.user.common.service.IAttachmentService;
import com.coolplay.user.common.tools.RedisCache;
import com.coolplay.user.common.utils.*;
import com.coolplay.user.core.model.Attachment;
import com.coolplay.user.security.constants.SecurityConstant;
import com.coolplay.user.security.service.IUserService;
import com.coolplay.user.user.model.*;
import com.coolplay.user.user.service.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
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

    private final static Logger logger = LoggerFactory.getLogger(CommonController.class);

    @Value("${weather.url}")
    private String weatherUrl;

    @Value("${weather.picture.url}")
    private String weatherPictureUrl;

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

    @Autowired
    private ICompanyService companyService;

    @Autowired
    private ICoolplayBaseService coolplayBaseService;

    @Autowired
    private IHelpService helpService;

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
                    "doc,docx,xls,xlsx,ppt,pptx,txt,zip,rar,gz,bz2,gif,jpg,jpeg,png,bmp,swf,flv,mp3,wav,wma,wmv,mid,avi,mpg,asf,rm,rmvb,mp4");

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
                        "doc,docx,xls,xlsx,ppt,pptx,txt,zip,rar,gz,bz2,gif,jpg,jpeg,png,bmp,swf,flv,mp3,wav,wma,wmv,mid,avi,mpg,asf,rm,rmvb,mp4");

                Attachment attachment = attachmentService
                        .uploadFileAttachement(request, multipartFile, dirName, maxSize, extMap, FILE);
                return attachment;
            }
        }
        return false;
    }

    @RequestMapping(value = "/attachment", method = RequestMethod.POST)
    @ResponseBody
    public Result attachment(@RequestParam("attachmentId") Integer attachmentId) {
        try {
            if (attachmentId != null) {
                Attachment attachment = attachmentService.selectByKey(attachmentId);
                if (attachment != null) {
                    return ResponseUtil.success(attachment);
                }
            }
            return ResponseUtil.error("附件id不存在。");
        } catch(Exception e) {
            e.printStackTrace();

            return ResponseUtil.error("系统异常, 请稍后重试。");
        }
    }

    /**
     * 发送验证码
     *
     * @param param
     */
    @ResponseBody
    @RequestMapping(value = "/verifyCode/sendVerifyCode", method = RequestMethod.POST)
    public Result sendCaptchaCode(@RequestBody Map<String, Object> param) {
        String mobilePhone = CommonUtil.defaultString(param.get("mobilePhone"), "");
        if(StringUtils.isEmpty(mobilePhone)) {
            return ResponseUtil.error("请输入手机号码");
        }

        try {
            String verifyCode = "";
            Object obj = redisCache.get(SecurityConstant.MOBILE_VERIFY_CODE_PREFIX + mobilePhone);
            if(obj != null) {
                verifyCode = String.valueOf(obj);
            }

            /*if(StringUtils.isNotEmpty(verifyCode)) {
                return ResponseUtil.error("验证码还在有效期内,请输入之前验证码。");
            }*/


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
        } catch(Exception e) {
            e.printStackTrace();

            return ResponseUtil.error("系统异常, 请稍后重试。");
        }
    }

    @ResponseBody
    @RequestMapping(value = "/lastVersion", method = RequestMethod.POST)
    public Result lastVersion() {

        try {
            List<SystemVersionModel> systemVersions = systemVersionService.selectByFilter(new SystemVersionModel());
            Map<String, SystemVersionModel> systemVersionMap = new HashMap<String, SystemVersionModel>();

            if(CollectionUtils.isNotEmpty(systemVersions)) {
                for(SystemVersionModel systemVersion : systemVersions) {
                    systemVersionMap.put(systemVersion.getAppType(), systemVersion);
                }
            }

            return ResponseUtil.success(systemVersionMap);
        } catch(Exception e) {
            e.printStackTrace();

            return ResponseUtil.error("系统异常, 请稍后重试。");
        }
    }

    /**
     * 获取天气数据
     *
     * @param lat
     * @param lon
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/weatherData", method = RequestMethod.POST)
    public Result weatherData(@RequestParam("lat") String lat, @RequestParam("lon")String lon) {
        if("0".equals(lat) || "0".equals(lon)) {
            return ResponseUtil.error("请求经纬度无效, 请选用有效的值.");
        }

        try {
            String cityKey = MapUtil.getCoordinate(lon, lat);

            //String weatherData = (String) redisCache.get(String.format(SecurityConstant.WEATHER_DATA_KEY, lat, lon));
            String weatherData = (String) redisCache.get(String.format(SecurityConstant.WEATHER_CITY_DATA_KEY, cityKey));
            if(StringUtils.isEmpty(weatherData)) {
                HttpClientResult result = HttpClientUtil.doGet(String.format("%s?lat=%s&lon=%s", weatherUrl, lat, lon));

                String content = result.getContent();

                Map<String, Object> contentMap = JSON.parseObject(content, Map.class);

                if(MapUtils.isNotEmpty(contentMap) && result.getCode() == 200
                        && "success".equals(String.valueOf(contentMap.get("responseCode")))) {
                    weatherData = String.valueOf(contentMap.get("data"));

                    redisCache.set(String.format(SecurityConstant.WEATHER_CITY_DATA_KEY, cityKey), weatherData, 12 * 60 * 60);
                } else {
                    logger.info("获取天气数据异常, 返回结果:[{}].", content);
                    System.out.println("获取天气数据异常, 返回结果:" + content);
                    return ResponseUtil.error("获取天气数据异常, 请稍后重试。");
                }
            }

            return ResponseUtil.success(JSON.parseObject(weatherData, Map.class));

        } catch(Exception e) {
            e.printStackTrace();

            return ResponseUtil.error("系统异常, 请稍后重试。");
        }
    }

    /**
     * 获取天气图片数据
     *
     * @param lat
     * @param lon
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/weatherPictureData", method = RequestMethod.POST)
    public Result weatherPictureData(@RequestParam("lat") String lat, @RequestParam("lon")String lon) {
        if("0".equals(lat) || "0".equals(lon)) {
            return ResponseUtil.error("请求经纬度无效, 请选用有效的值.");
        }

        try {
            String weatherData = (String) redisCache.get(String.format(SecurityConstant.WEATHER_PICTURE_DATA_KEY, lat, lon));
            if(StringUtils.isEmpty(weatherData)) {
                HttpClientResult result = HttpClientUtil.doGet(String.format("%s?lat=%s&lon=%s", weatherPictureUrl, lat, lon));

                String content = result.getContent();

                Map<String, Object> contentMap = JSON.parseObject(content, Map.class);

                if(MapUtils.isNotEmpty(contentMap) && result.getCode() == 200
                        && "success".equals(String.valueOf(contentMap.get("responseCode")))) {
                    weatherData = String.valueOf(contentMap.get("data"));

                    redisCache.set(String.format(SecurityConstant.WEATHER_PICTURE_DATA_KEY, lat, lon), weatherData, 12 * 60 * 60);
                } else {
                    return ResponseUtil.error("获取天气数据异常, 请稍后重试。");
                }
            }

            return ResponseUtil.success(JSON.parseObject(weatherData, Map.class));

        } catch(Exception e) {
            e.printStackTrace();

            return ResponseUtil.error("系统异常, 请稍后重试。");
        }
    }

    /**
     * 企业基地关联数据
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/companyOptions", method = RequestMethod.POST)
    public Result companyOptions() {
        try{
            List<CompanyModel> companyModels = companyService.findOptionDatas();
            List<CoolplayBaseModel> coolplayBaseModels = coolplayBaseService.findOptionDatas();

            Map<Integer, List<CoolplayBaseModel>> coolplayBaseMap = new HashMap<Integer, List<CoolplayBaseModel>>();
            if(CollectionUtils.isNotEmpty(coolplayBaseModels)) {
                for(CoolplayBaseModel coolplayBaseModel : coolplayBaseModels) {
                    List<CoolplayBaseModel> coolplayBases = coolplayBaseMap.get(coolplayBaseModel.getCompanyId());
                    if(CollectionUtils.isEmpty(coolplayBases)) {
                        coolplayBases = new ArrayList<CoolplayBaseModel>();
                    }

                    coolplayBases.add(coolplayBaseModel);

                    coolplayBaseMap.put(coolplayBaseModel.getCompanyId(), coolplayBases);
                }
            }

            List<CompanyOption> companyOptions = new ArrayList<CompanyOption>();
            if(CollectionUtils.isNotEmpty(companyModels)) {
                for(CompanyModel companyModel : companyModels) {
                    CompanyOption companyOption = new CompanyOption(companyModel.getId(), companyModel.getCompanyName());

                    List<CoolplayBaseModel> tmpCoolplayBases = coolplayBaseMap.get(companyModel.getId());

                    List<BaseOption> baseOptions = new ArrayList<BaseOption>();
                    if(CollectionUtils.isNotEmpty(tmpCoolplayBases)) {
                        for(CoolplayBaseModel tmpCoolplayBase : tmpCoolplayBases) {
                            baseOptions.add(new BaseOption(tmpCoolplayBase.getId(), tmpCoolplayBase.getBaseName()));
                        }
                    }
                    companyOption.setBaseOptions(baseOptions);

                    companyOptions.add(companyOption);
                }
            }

            Map<String, Object> optionMap = new HashMap<String, Object>();
            optionMap.put("companys", companyOptions);
            optionMap.put("bases", coolplayBaseModels);

            return ResponseUtil.success(optionMap);
        } catch(Exception e) {
            e.printStackTrace();

            return ResponseUtil.error("系统异常, 请稍后重试。");
        }
    }

    /**
     * 帮助列表信息
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/helpList", method = RequestMethod.POST)
    public Result helpList(@RequestParam("helpTitle") String helpTitle) {
        try {

            HelpModel helpModel = new HelpModel();
            helpModel.setHelpTitle(helpTitle);
            helpModel.setIsDel(0);
            List<HelpModel> helpModels = helpService.selectByFilter(helpModel);
            List<HelpModel> helpResultDatas = new ArrayList<HelpModel>();

            Map<Integer, List<HelpModel>> subHelpModelMap = new HashMap<Integer, List<HelpModel>>();
            if(CollectionUtils.isNotEmpty(helpModels)) {
                for(HelpModel tmpHelpModel : helpModels) {
                    if(tmpHelpModel.getParentId() == 0) {
                        helpResultDatas.add(tmpHelpModel);
                    } else {
                        List<HelpModel> subHelpModels = subHelpModelMap.get(tmpHelpModel.getParentId());
                        if(CollectionUtils.isEmpty(subHelpModels)) {
                            subHelpModels = new ArrayList<HelpModel>();
                        }
                        subHelpModels.add(tmpHelpModel);

                        subHelpModelMap.put(tmpHelpModel.getParentId(), subHelpModels);
                    }
                }

                //如果分类为空, 子帮助文档不为空,则补全分类数据
                if(CollectionUtils.isEmpty(helpResultDatas) && MapUtils.isNotEmpty(subHelpModelMap)) {
                    List<Integer> ids = new ArrayList<Integer>(subHelpModelMap.keySet());
                    List<HelpModel> parentHelps = helpService.find(Collections.singletonMap("ids", ids));
                    if(CollectionUtils.isNotEmpty(parentHelps)) {
                        helpResultDatas = parentHelps;
                    }
                }

                if(CollectionUtils.isNotEmpty(helpResultDatas)) {
                    for(HelpModel tmpHelpModel : helpResultDatas) {
                        tmpHelpModel.setSubHelpModels(subHelpModelMap.getOrDefault(tmpHelpModel.getId(), Collections.emptyList()));
                    }
                }
            }

            return ResponseUtil.success(Collections.singletonMap("helpList", helpResultDatas));
        } catch(Exception e) {
            e.printStackTrace();

            return ResponseUtil.error("系统异常, 请稍后重试。");
        }
    }

    /**
     * 帮助详细信息
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/helpDetail", method = RequestMethod.POST)
    public Result helpDetail(@RequestParam("helpId") Integer helpId) {
        try {
            HelpModel helpModel = helpService.findById(helpId);

            return ResponseUtil.success(Collections.singletonMap("helpModel", helpModel));
        } catch(Exception e) {
            e.printStackTrace();

            return ResponseUtil.error("系统异常, 请稍后重试。");
        }
    }

    /**
     * 初始化天气信息
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/syncWeatherPictureData", method = RequestMethod.POST)
    public Result syncWeatherPictureData(@RequestParam("lat") String lat, @RequestParam("lon")String lon, @RequestParam("weatherData") String weatherData) {
        try {
            redisCache.set(String.format(SecurityConstant.WEATHER_PICTURE_DATA_KEY, lat, lon), weatherData, 12 * 60 * 60);
            logger.info("同步天气截图信息,lat:{}, lon:{}, weatherData:{}.", lat, lon, weatherData);

            return ResponseUtil.success();
        } catch(Exception e) {
            e.printStackTrace();

            return ResponseUtil.error("系统异常, 请稍后重试。");
        }
    }

}
