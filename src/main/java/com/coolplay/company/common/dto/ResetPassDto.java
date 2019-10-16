package com.coolplay.company.common.dto;

import java.io.Serializable;

/**
 * Created by majiancheng on 2019/10/7.
 */
public class ResetPassDto implements Serializable {

    private static final long serialVersionUID = -4007234918393479625L;

    private String contactPhone;

    private String captchaCode;

    private String password;

    private String confirmPassword;

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getCaptchaCode() {
        return captchaCode;
    }

    public void setCaptchaCode(String captchaCode) {
        this.captchaCode = captchaCode;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
