/*
 * 北京果敢时代科技有限公司
 * 北京市朝阳区望京SOHO T3 B座1607
 * 邮编：100022
 * 网址：www.davdian.com
 */

package com.coolplay.user.user.model;

import com.coolplay.user.common.handler.Sortable;
import com.coolplay.user.common.utils.DateStyle;
import com.coolplay.user.common.utils.DateUtil;

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
@Table(name = "d_message")
public class MessageModel extends Sortable {
	private static final long serialVersionUID = 1L;

	//columns START
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;//"主键"

	@Column(name = "message_name")
	private String messageName;//"消息名称"

	@Column(name = "message_content")
	private String messageContent;//"消息内容"

	@Column(name = "message_type")
	private Integer messageType;//"消息类型（1：活动与邀请，2：通知）"

	@Column(name = "user_id")
	private Integer userId;//"C端用户ID"

	@Column(name = "message_url")
	private String messageUrl;//"消息点击链接"

	@Column(name = "is_read")
	private Integer isRead;//"是否已读（0：未读，1：已读）"

	@Column(name = "is_agree")
	private Integer isAgree;//"是否同意（0：默认，1：同意，2：不同意）"

	@Column(name = "c_time")
	private Date ctime;//"创建时间"

	//columns END

	@Transient
	private String ctimeStr;//"创建时间"

	@Transient
	private String yesBtn = "";

	@Transient
	private String noBtn = "";

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return this.id;
	}
		
	public void setMessageName(String messageName) {
		this.messageName = messageName;
	}

	public String getMessageName() {
		return this.messageName;
	}
		
	public void setMessageContent(String messageContent) {
		this.messageContent = messageContent;
	}

	public String getMessageContent() {
		return this.messageContent;
	}
		
	public void setMessageType(Integer messageType) {
		this.messageType = messageType;
	}

	public Integer getMessageType() {
		return this.messageType;
	}
		
	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getUserId() {
		return this.userId;
	}
		
	public void setMessageUrl(String messageUrl) {
		this.messageUrl = messageUrl;
	}

	public String getMessageUrl() {
		return this.messageUrl;
	}
		
	public void setIsRead(Integer isRead) {
		this.isRead = isRead;
	}

	public Integer getIsRead() {
		return this.isRead;
	}
		
	public void setIsAgree(Integer isAgree) {
		this.isAgree = isAgree;
	}

	public Integer getIsAgree() {
		return this.isAgree;
	}
		
	public void setCtime(Date ctime) {
		this.ctime = ctime;
	}

	public Date getCtime() {
		return this.ctime;
	}

	public String getCtimeStr() {
		if(this.getCtime() != null) {
			this.ctimeStr = DateUtil.DateToString(this.getCtime(), DateStyle.YYYY_MM_DD_HH_MM_SS);
		}
		return ctimeStr;
	}

	public void setCtimeStr(String ctimeStr) {
		this.ctimeStr = ctimeStr;
	}

	public void setYesBtn(String yesBtn) {
		this.yesBtn = yesBtn;
	}

	public String getYesBtn() {
		return yesBtn;
	}

	public void setNoBtn(String noBtn) {
		this.noBtn = noBtn;
	}

	public String getNoBtn() {
		return noBtn;
	}
}

