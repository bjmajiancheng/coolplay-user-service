/*
 * 北京果敢时代科技有限公司
 * 北京市朝阳区望京SOHO T3 B座1607
 * 邮编：100022
 * 网址：www.davdian.com
 */

package com.coolplay.user.core.model;

import com.coolplay.user.common.handler.Sortable;
import com.coolplay.user.user.model.LabelModel;
import com.coolplay.user.user.model.PostModel;
import org.apache.commons.lang.StringUtils;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.*;
import javax.persistence.*;

/**
 * @author  shawn
 * @version 1.0
 * @since 1.0
 */
@Table(name = "d_user")
public class UserModel extends Sortable {
	private static final long serialVersionUID = 1L;

	//columns START
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;//"主键"

	@Column(name = "user_name")
	private String userName;//"用户名"

	@Column(name = "password")
	private String password;//"密码"

	@Column(name = "mobile_phone")
	private String mobilePhone;//"手机号"

	@Column(name = "wechat_id")
	private String wechatId;//"微信唯一ID"

	@Column(name = "qq_id")
	private String qqId;//"QQ唯一ID"

	@Column(name = "nick_name")
	private String nickName;//"昵称"

	@Column(name = "real_name")
	private String realName;//"真实姓名"

	@Column(name = "id_card")
	private String idCard;//"身份证号"

	@Column(name = "id_card_images")
	private String idCardImages;//"身份证照片"

	@Column(name = "user_desc")
	private String userDesc;//"个人简介"

	@Column(name = "signature")
	private String signature;//"个人签名"

	@Column(name = "last_verify_code")
	private String lastVerifyCode;//"最后一次验证码"

	@Column(name = "last_verify_time")
	private Date lastVerifyTime;//"验证码发送时间"

	@Column(name = "head_image")
	private String headImage;//"用户头像"

	@Column(name = "account_non_locked")
	private Boolean accountNonLocked;//"未锁定状态，0=正常，1=锁定"

	@Column(name = "account_non_expired")
	private Boolean accountNonExpired;//"账号过期状态，1=正常，0=过期"

	@Column(name = "credentials_non_expired")
	private Boolean credentialsNonExpired;//"密码失效状态：1：未失效 0：已失效"

	@Column(name = "last_login_ip")
	private String lastLoginIp;//"最后登录IP"

	@Column(name = "last_login_time")
	private Date lastLoginTime;//"最后登录时间"

	@Column(name = "enabled")
	private Boolean enabled;//"状态，0=冻结，1=正常"

	@Column(name = "allowMessage")
	private Integer allowMessage;//"是否允许通知消息， 0：否, 1:是"

	@Column(name = "last_password_reset")
	private Date lastPasswordReset;//"上次密码重置时间"

	@Column(name = "c_time")
	private Date ctime;//"创建时间"

	//columns END

	@Transient
	private List<String> idCardImageArr = new ArrayList<String>();//身份证图片链接数组

	@Transient
	private List<LabelModel> labelList = new ArrayList<LabelModel>();//标签数组

	@Transient
	private Integer followCnt = 0;//关注人数

	@Transient
	private Integer fansCnt = 0;//粉丝人数

	@Transient
	private Integer isFans = 0;//是否关注 1：已关注，0未关注

	@Transient
	private List<PostModel> dynamicList = new ArrayList<PostModel>();

	@Transient
	private String token;

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return this.id;
	}
		
	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserName() {
		return this.userName;
	}
		
	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassword() {
		return this.password;
	}
		
	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public String getMobilePhone() {
		return this.mobilePhone;
	}
		
	public void setWechatId(String wechatId) {
		this.wechatId = wechatId;
	}

	public String getWechatId() {
		return this.wechatId;
	}
		
	public void setQqId(String qqId) {
		this.qqId = qqId;
	}

	public String getQqId() {
		return this.qqId;
	}
		
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getNickName() {
		return this.nickName;
	}
		
	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getRealName() {
		return this.realName;
	}
		
	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public String getIdCard() {
		return this.idCard;
	}
		
	public void setIdCardImages(String idCardImages) {
		this.idCardImages = idCardImages;
	}

	public String getIdCardImages() {
		return this.idCardImages;
	}
		
	public void setUserDesc(String userDesc) {
		this.userDesc = userDesc;
	}

	public String getUserDesc() {
		return this.userDesc;
	}
		
	public void setSignature(String signature) {
		this.signature = signature;
	}

	public String getSignature() {
		return this.signature;
	}
		
	public void setLastVerifyCode(String lastVerifyCode) {
		this.lastVerifyCode = lastVerifyCode;
	}

	public String getLastVerifyCode() {
		return this.lastVerifyCode;
	}
		
	public void setLastVerifyTime(Date lastVerifyTime) {
		this.lastVerifyTime = lastVerifyTime;
	}

	public Date getLastVerifyTime() {
		return this.lastVerifyTime;
	}
		
	public void setHeadImage(String headImage) {
		this.headImage = headImage;
	}

	public String getHeadImage() {
		return this.headImage;
	}
		
	public void setAccountNonLocked(Boolean accountNonLocked) {
		this.accountNonLocked = accountNonLocked;
	}

	public Boolean getAccountNonLocked() {
		return this.accountNonLocked;
	}
		
	public void setAccountNonExpired(Boolean accountNonExpired) {
		this.accountNonExpired = accountNonExpired;
	}

	public Boolean getAccountNonExpired() {
		return this.accountNonExpired;
	}
		
	public void setCredentialsNonExpired(Boolean credentialsNonExpired) {
		this.credentialsNonExpired = credentialsNonExpired;
	}

	public Boolean getCredentialsNonExpired() {
		return this.credentialsNonExpired;
	}
		
	public void setLastLoginIp(String lastLoginIp) {
		this.lastLoginIp = lastLoginIp;
	}

	public String getLastLoginIp() {
		return this.lastLoginIp;
	}
		
	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public Date getLastLoginTime() {
		return this.lastLoginTime;
	}
		
	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public Boolean getEnabled() {
		return this.enabled;
	}

	public Integer getAllowMessage() {
		return allowMessage;
	}

	public void setAllowMessage(Integer allowMessage) {
		this.allowMessage = allowMessage;
	}

	public void setLastPasswordReset(Date lastPasswordReset) {
		this.lastPasswordReset = lastPasswordReset;
	}

	public Date getLastPasswordReset() {
		return this.lastPasswordReset;
	}
		
	public void setCtime(Date ctime) {
		this.ctime = ctime;
	}

	public Date getCtime() {
		return this.ctime;
	}

	public List<String> getIdCardImageArr() {
		if(StringUtils.isNotEmpty(this.getIdCardImages())) {
			this.idCardImageArr = Arrays.asList(this.getIdCardImages().split(","));
		}

		return idCardImageArr;
	}

	public void setIdCardImageArr(List<String> idCardImageArr) {
		this.idCardImageArr = idCardImageArr;
	}

	public List<LabelModel> getLabelList() {
		return labelList;
	}

	public void setLabelList(List<LabelModel> labelList) {
		this.labelList = labelList;
	}

	public Integer getFollowCnt() {
		return followCnt;
	}

	public void setFollowCnt(Integer followCnt) {
		this.followCnt = followCnt;
	}

	public Integer getFansCnt() {
		return fansCnt;
	}

	public void setFansCnt(Integer fansCnt) {
		this.fansCnt = fansCnt;
	}

	public Integer getIsFans() {
		return isFans;
	}

	public void setIsFans(Integer isFans) {
		this.isFans = isFans;
	}

	public List<PostModel> getDynamicList() {
		return dynamicList;
	}

	public void setDynamicList(List<PostModel> dynamicList) {
		this.dynamicList = dynamicList;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}

