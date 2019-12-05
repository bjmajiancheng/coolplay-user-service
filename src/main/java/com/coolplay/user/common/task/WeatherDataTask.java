package com.coolplay.user.common.task;

import com.alibaba.fastjson.JSON;
import com.coolplay.user.common.utils.*;
import com.coolplay.user.user.model.CompanyModel;
import com.coolplay.user.user.model.CoolplayBaseModel;
import com.coolplay.user.user.service.ICompanyService;
import com.coolplay.user.user.service.ICoolplayBaseService;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import java.util.*;

/**
 * Created by majiancheng on 2019/12/4.
 */
public class WeatherDataTask {

    private static final Logger logger = LoggerFactory.getLogger(WeatherDataTask.class);

    private ICoolplayBaseService coolplayBaseService = (ICoolplayBaseService) SpringContextUtil
            .getBean("coolplayBaseService");

    private ICompanyService companyService = (ICompanyService) SpringContextUtil.getBean("companyService");

    @Value("${weather.picture.url}")
    private String weatherPictureUrl;

    /**
     * 同步天气图片信息
     */
    public void syncWeacherPictureInfo() {
        List<CompanyModel> companyModels = companyService.find(Collections.singletonMap("isDel", 0));
        List<CoolplayBaseModel> coolplayBaseModels = coolplayBaseService.find(Collections.singletonMap("isDel", 0));

        List<Map<String, Object>> paramList = new ArrayList<Map<String, Object>>();
        if (CollectionUtils.isNotEmpty(companyModels)) {
            for (CompanyModel companyModel : companyModels) {
                Map<String, Object> param = new HashMap<String, Object>();
                param.put("lat", companyModel.getPosX());
                param.put("lon", companyModel.getPosY());

                paramList.add(param);
            }
        }

        if (CollectionUtils.isNotEmpty(coolplayBaseModels)) {
            for (CoolplayBaseModel coolplayBaseModel : coolplayBaseModels) {
                Map<String, Object> param = new HashMap<String, Object>();
                param.put("lat", coolplayBaseModel.getPosX());
                param.put("lon", coolplayBaseModel.getPosY());

                paramList.add(param);
            }
        }

        try {
            HttpClientResult result = HttpClientUtil.doGet(String.format("%s?latArr=%s", weatherPictureUrl, JSON.toJSONString(paramList)));

            logger.info("增量同步天气图片信息, 请求参数:{}, 返回结果:{}.", JSON.toJSONString(paramList), JSON.toJSONString(result));

        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 每分钟同步天气图片信息
     */
    public void syncMinuteWeacherPictureInfo() {
        String preMinuteStr = DateUtil.DateToString(DateUtil.addMinute(new Date(), -1), DateStyle.YYYY_MM_DD_HH_MM_SS);

        List<CompanyModel> companyModels = companyService.findByLastUpdatetime(preMinuteStr);
        List<CoolplayBaseModel> coolplayBaseModels = coolplayBaseService.findByLastUpdatetime(preMinuteStr);
        //List<CompanyModel> companyModels = companyService.find(Collections.singletonMap("isDel", 0));
        //List<CoolplayBaseModel> coolplayBaseModels = coolplayBaseService.find(Collections.singletonMap("isDel", 0));

        List<Map<String, Object>> paramList = new ArrayList<Map<String, Object>>();
        if (CollectionUtils.isNotEmpty(companyModels)) {
            for (CompanyModel companyModel : companyModels) {
                Map<String, Object> param = new HashMap<String, Object>();
                param.put("lat", companyModel.getPosX());
                param.put("lon", companyModel.getPosY());

                paramList.add(param);
            }
        }

        if (CollectionUtils.isNotEmpty(coolplayBaseModels)) {
            for (CoolplayBaseModel coolplayBaseModel : coolplayBaseModels) {
                Map<String, Object> param = new HashMap<String, Object>();
                param.put("lat", coolplayBaseModel.getPosX());
                param.put("lon", coolplayBaseModel.getPosY());

                paramList.add(param);
            }
        }

        try {
            HttpClientResult result = HttpClientUtil.doGet(String.format("%s?latArr=%s", weatherPictureUrl, JSON.toJSONString(paramList)));

            logger.info("分钟增量同步天气图片信息, 请求参数:{}, 返回结果:{}.", JSON.toJSONString(paramList), JSON.toJSONString(result));

        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
