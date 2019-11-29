package com.coolplay.user.common.utils;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.coolplay.user.common.api.NoneAuthController;
import com.mysql.jdbc.log.LogFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by majiancheng on 2019/11/29.
 */
public class WechatUtils {

    private final static Logger logger = LoggerFactory.getLogger(WechatUtils.class);

    /**
     * 根据openId和token获取微信信息
     *
     * @param openId
     * @param accessToken
     * @return
     */
    public static Map<String, Object> getWechatInfoByOpenIdAndAccessToken(String openId, String accessToken) {
        if (StringUtils.isEmpty(openId) || StringUtils.isEmpty(accessToken)) {
            return Collections.emptyMap();
        }

        Map<String, String> params = new HashMap<String, String>();
        params.put("openid", openId);
        params.put("access_token", accessToken);
        params.put("lang", "zh_CN");
        try {
            HttpClientResult result = HttpClientUtil.doGet("https://api.weixin.qq.com/sns/userinfo", params);

            logger.info("获取微信用户信息:{}.", result.getContent());
            return JSON.parseObject(result.getContent(), Map.class);
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyMap();
        }

    }

    public static void main(String[] args) {
        Map<String, Object> result = WechatUtils.getWechatInfoByOpenIdAndAccessToken(
                "o_P-GtxCZB6chKdVRilC1PyJXI-E",
                "sM4AOVdWfPE4DxkXGEs8VJh4dzE_LY1qPYRS-hJs6ZiAlxrGOQQiM1HkI-bzZL-1gWU2-ZO6DoESE89380TACw"
                );

        System.out.println(JSON.toJSONString(result));
    }
}
