package com.coolplay.user.user.dto;

import java.io.Serializable;

/**
 * Created by majiancheng on 2019/11/23.
 */
public class ReviewMessageDto implements Serializable {

    private static final long serialVersionUID = 564539995717639279L;

    /**
     * 申请加圈
     */
    public static final String APPLICATION_CIRCLE = "application_circle";

    private String type;//申请类型

    private Integer typeId;//申请业务ID

    private Integer applicationUserId;//申请人

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public Integer getApplicationUserId() {
        return applicationUserId;
    }

    public void setApplicationUserId(Integer applicationUserId) {
        this.applicationUserId = applicationUserId;
    }
}
