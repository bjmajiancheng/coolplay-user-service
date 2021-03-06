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

import java.net.URLEncoder;
import java.util.*;

/**
 * Created by majiancheng on 2019/12/4.
 */
public class WeatherDataTask {

    private static final Logger logger = LoggerFactory.getLogger(WeatherDataTask.class);

    private ICoolplayBaseService coolplayBaseService = (ICoolplayBaseService) SpringContextUtil
            .getBean("coolplayBaseService");

    private ICompanyService companyService = (ICompanyService) SpringContextUtil.getBean("companyService");

    @Value("${weather.picture.batch.url}")
    private String weatherPictureBatchUrl;

    /**
     * 同步天气图片信息
     */
    public void syncWeatherPictureInfo() {
        List<CompanyModel> companyModels = companyService.find(Collections.singletonMap("isDel", 0));
        List<CoolplayBaseModel> coolplayBaseModels = coolplayBaseService.find(Collections.singletonMap("isDel", 0));

        List<Map<String, Object>> paramList = new ArrayList<Map<String, Object>>();
        if (CollectionUtils.isNotEmpty(companyModels)) {
            for (CompanyModel companyModel : companyModels) {
                if(companyModel.getPosX().floatValue() == 0 || companyModel.getPosY().floatValue() == 0) continue;
                Map<String, Object> param = new HashMap<String, Object>();
                param.put("lon", companyModel.getPosX().floatValue());
                param.put("lat", companyModel.getPosY().floatValue());

                paramList.add(param);
            }
        }

        if (CollectionUtils.isNotEmpty(coolplayBaseModels)) {
            for (CoolplayBaseModel coolplayBaseModel : coolplayBaseModels) {
                if(coolplayBaseModel.getPosX().floatValue() == 0 || coolplayBaseModel.getPosY().floatValue() == 0) continue;
                Map<String, Object> param = new HashMap<String, Object>();
                param.put("lon", coolplayBaseModel.getPosX().floatValue());
                param.put("lat", coolplayBaseModel.getPosY().floatValue());

                paramList.add(param);
            }
        }

        try {

            if(CollectionUtils.isNotEmpty(paramList)) {
                String url = String.format("%s?latArr=%s", weatherPictureBatchUrl, URLEncoder.encode(JSON.toJSONString(paramList), "UTF-8"));
                HttpClientResult result = HttpClientUtil.doGet(url);

                logger.info("增量同步天气图片信息, url:{}, 请求参数:{}, 返回结果:{}.", url, JSON.toJSONString(paramList), JSON.toJSONString(result));
            } else {
                logger.info("增量同步天气图片信息, 无增量更新信息.");
            }


        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 每分钟同步天气图片信息
     */
    public void syncMinuteWeatherPictureInfo() {
        String preMinuteStr = DateUtil.DateToString(DateUtil.addMinute(new Date(), -5), DateStyle.YYYY_MM_DD_HH_MM_SS);

        List<CompanyModel> companyModels = companyService.findByLastUpdatetime(preMinuteStr);
        List<CoolplayBaseModel> coolplayBaseModels = coolplayBaseService.findByLastUpdatetime(preMinuteStr);

        List<Map<String, Object>> paramList = new ArrayList<Map<String, Object>>();
        if (CollectionUtils.isNotEmpty(companyModels)) {
            for (CompanyModel companyModel : companyModels) {
                if(companyModel.getPosX().floatValue() == 0 || companyModel.getPosY().floatValue() == 0) continue;
                Map<String, Object> param = new HashMap<String, Object>();
                param.put("lon", companyModel.getPosX().floatValue());
                param.put("lat", companyModel.getPosY().floatValue());

                paramList.add(param);
            }
        }

        if (CollectionUtils.isNotEmpty(coolplayBaseModels)) {
            for (CoolplayBaseModel coolplayBaseModel : coolplayBaseModels) {
                if(coolplayBaseModel.getPosX().floatValue() == 0 || coolplayBaseModel.getPosY().floatValue() == 0) continue;
                Map<String, Object> param = new HashMap<String, Object>();
                param.put("lon", coolplayBaseModel.getPosX().floatValue());
                param.put("lat", coolplayBaseModel.getPosY().floatValue());

                paramList.add(param);
            }
        }

        try {

            if(CollectionUtils.isNotEmpty(paramList)) {
                String url = String.format("%s?latArr=%s", weatherPictureBatchUrl, URLEncoder.encode(JSON.toJSONString(paramList), "UTF-8"));
                HttpClientResult result = HttpClientUtil.doGet(url);

                logger.info("分钟增量同步天气图片信息, url:{}, 请求参数:{}, 返回结果:{}.", url, JSON.toJSONString(paramList), JSON.toJSONString(result));
            } else {
                logger.info("分钟增量同步天气图片信息, 无增量更新信息.");
            }

        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
