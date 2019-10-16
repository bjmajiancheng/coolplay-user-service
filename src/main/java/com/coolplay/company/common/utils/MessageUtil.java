package com.coolplay.company.common.utils;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by majiancheng on 2019/10/5.
 */
public class MessageUtil implements Serializable {

    private static final Logger logger = LoggerFactory.getLogger(MessageUtil.class);

    private String userId;

    private String account;

    private String password;

    private String uri;

    private Properties smsProperties;

    /**
     * 加载配置
     */
    public void loadProperty() {
        String path = MessageUtil.class.getResource("").getPath();
        path = path.split("classes/com")[0] + "classes";
        smsProperties = new Properties();
        String smsFilePath = path + "/properties/message.properties";
        try {
            smsProperties.load(new InputStreamReader(
                    new FileInputStream(smsFilePath), "UTF-8"));
        } catch (IOException e) {
            logger.error(String.format("message.properties not find %s", smsFilePath), e);
        }

    }

    public String getProperty(String key, String[] values) {
        String msgContent = smsProperties.getProperty(key);

        if(StringUtils.isEmpty(msgContent)) {
            return "";
        }

        if(values == null || values.length == 0) {
            return msgContent;
        } else {
            return MessageFormat.format(msgContent, values);
        }
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    /**
     * 发送短信信息
     *
     * @param mobile
     * @param key
     * @param values
     * @return
     */
    public Result sendMessage(String mobile, String key, String[] values) {

        try{
            Map<String, String> params = new HashMap<String, String>();
            params.put("UserID", this.getUserId());
            params.put("Account", this.getAccount());
            params.put("Password", this.getPassword());
            params.put("Phones", String.format("%s;", mobile));
            params.put("SendTime", "");
            params.put("SendType", "1");
            params.put("PostFixNumber", "");

            String content = this.getProperty(key, values);
            if(StringUtils.isEmpty(content)) {
                return ResponseUtil.error("短信模板不存在");
            }
            params.put("Content", content);


            HttpClientResult result = HttpClientUtil.doGet(this.getUri(), params);
            logger.info("手机号码:{}, 短信发送结果:{}", mobile, JSON.toJSONString(result));

            return ResponseUtil.success(JSON.toJSONString(result));
        } catch(Exception e) {
            e.printStackTrace();
        }


        return ResponseUtil.success();
    }

}
