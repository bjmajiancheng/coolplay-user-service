package com.coolplay.user.common.utils;

import cn.jiguang.common.ClientConfig;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import java.io.Serializable;
import java.util.Collections;
import java.util.Map;

/**
 * Created by majiancheng on 2020/1/4.
 */
public class JPushUtil implements Serializable {

    private static final long serialVersionUID = -4826395692356500281L;

    private final static Logger logger = LoggerFactory.getLogger(JPushUtil.class);

    private JPushClient jPushClient;

    private boolean apnsProduction;//是否是线上环境

    public JPushUtil(JPushClient jPushClient, boolean apnsProduction) {
        this.jPushClient = jPushClient;
        this.apnsProduction = apnsProduction;
    }

    /**
     * 极光发送消息
     *
     * @param userId
     * @param title
     * @param message
     * @return
     */
    public boolean sendMessage(Integer userId, String title, String message, Map<String, String> extras) {
        try {
            logger.info("极光推送消息, userId:{}, title:{}, message:{}.", userId, title, message);

            PushResult result = jPushClient.sendPush(buildPushObject(String.valueOf(userId), title, message, extras));

            logger.info("极光推送消息完成, userId:{}, code:{}, sendno:{}, error_info:{}.", userId, result.getResponseCode(),
                    result.sendno, (result.error == null) ? "" : JSON.toJSONString(result.error));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    /**
     * 极光发送消息
     *
     * @param userId
     * @param title
     * @param message
     * @return
     */
    public boolean sendMessage(Integer userId, String title, String message) {
        try {
            logger.info("极光推送消息, userId:{}, title:{}, message:{}.", userId, title, message);

            PushResult result = jPushClient.sendPush(buildPushObject(String.valueOf(userId), title, message,
                    Collections.emptyMap()));

            logger.info("极光推送消息完成, userId:{}, code:{}, sendno:{}, error_info:{}.", userId, result.getResponseCode(),
                    result.sendno, (result.error == null) ? "" : JSON.toJSONString(result.error));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    /**
     * 构建推送消息
     *
     * @param userId
     * @param title
     * @param content
     * @return
     */
    public PushPayload buildPushObject(String userId, String title, String content, Map<String, String> extras) {
        return PushPayload.newBuilder().setPlatform(Platform.android_ios()).setAudience(Audience.tag(userId))
                .setNotification(
                        Notification.newBuilder()
                        .addPlatformNotification(IosNotification.newBuilder().incrBadge(5).setSound("default").setAlert(content).addExtra(title,content).addExtras(extras).build())
                        .addPlatformNotification(AndroidNotification.newBuilder().setAlert(content).setTitle(title).addExtras(extras).build()).build())
                .setOptions(Options.newBuilder().setApnsProduction(apnsProduction).build()).build();
    }

    public static void main(String[] args) {
        JPushClient jPushClient = new JPushClient("a2a521af82fafd7d63ec094e", "9b1dbf6a02bdb51ea6bf269a");

        JPushUtil jPushUtil = new JPushUtil(jPushClient, false);
        jPushUtil.sendMessage(42, "test title", "test msg.");
    }

}
