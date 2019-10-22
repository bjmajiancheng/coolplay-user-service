package com.coolplay.user.security.security;

public class AuthenticationRequest {

    private static final long serialVersionUID = 6624726180748515507L;

    private String username;

    private String mobilePhone;

    private String password;

    private String vcode;

    private String vkey;

    private String verifyCode;

    private String thirdId;

    private Integer thirdType;

    public AuthenticationRequest() {
    }

    public AuthenticationRequest(String username, String password) {
        this.setUsername(username);
        this.setMobilePhone(username);
        this.setPassword(password);
    }

    public String getVkey() {
        return vkey;
    }

    public void setVkey(String vkey) {
        this.vkey = vkey;
    }

    public String getVcode() {
        return vcode;
    }

    public void setVcode(String vcode) {
        this.vcode = vcode;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }

    public String getThirdId() {
        return thirdId;
    }

    public void setThirdId(String thirdId) {
        this.thirdId = thirdId;
    }

    public Integer getThirdType() {
        return thirdType;
    }

    public void setThirdType(Integer thirdType) {
        this.thirdType = thirdType;
    }
}
