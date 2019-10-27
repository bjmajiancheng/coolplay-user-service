package com.coolplay.user.user.dto;

import java.io.Serializable;

/**
 * Created by majiancheng on 2019/10/24.
 */
public class CircleUserDto implements Serializable {

    private static final long serialVersionUID = -2157402102449552893L;

    private Integer circleId = 0;

    private Integer userId = 0;

    private String nickName = "";

    private String headImage = "";

    private Integer isOwner = 0;

    private Integer isAdmin = 0;

    public CircleUserDto(Integer circleId, Integer userId, Integer isOwner, Integer isAdmin) {
        this.circleId = circleId;
        this.userId = userId;
        this.isOwner = isOwner;
        this.isAdmin = isAdmin;
    }

    public Integer getCircleId() {
        return circleId;
    }

    public void setCircleId(Integer circleId) {
        this.circleId = circleId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getHeadImage() {
        return headImage;
    }

    public void setHeadImage(String headImage) {
        this.headImage = headImage;
    }

    public Integer getIsOwner() {
        return isOwner;
    }

    public void setIsOwner(Integer isOwner) {
        this.isOwner = isOwner;
    }

    public Integer getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(Integer isAdmin) {
        this.isAdmin = isAdmin;
    }
}
