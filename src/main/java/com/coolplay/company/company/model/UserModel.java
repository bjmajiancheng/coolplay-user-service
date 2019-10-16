/*
 * 北京果敢时代科技有限公司
 * 北京市朝阳区望京SOHO T3 B座1607
 * 邮编：100022
 * 网址：www.davdian.com
 */

package com.coolplay.company.company.model;

import com.coolplay.company.common.handler.Sortable;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
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

	@Column(name = "last_verify_code")
	private String lastVerifyCode;//"最后一次验证码"

	@Column(name = "last_verify_time")
	private Date lastVerifyTime;//"验证码发送时间"

	@Column(name = "head_image")
	private String headImage;//"用户头像"

	@Column(name = "c_time")
	private Date ctime;//"创建时间"

	//columns END
		
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
		
	public void setCtime(Date ctime) {
		this.ctime = ctime;
	}

	public Date getCtime() {
		return this.ctime;
	}

}

