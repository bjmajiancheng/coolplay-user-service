/*
 * 北京果敢时代科技有限公司
 * 北京市朝阳区望京SOHO T3 B座1607
 * 邮编：100022
 * 网址：www.davdian.com
 */

package com.coolplay.company.company.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.*;
import com.coolplay.company.common.handler.Sortable;

/**
 * @author  shawn
 * @version 1.0
 * @since 1.0
 */
@Table(name = "d_circle_member_review")
public class CircleMemberReviewModel extends Sortable {
	private static final long serialVersionUID = 1L;

	//columns START
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;//"主键"

	@Column(name = "circle_id")
	private Integer circleId;//"酷玩圈ID"

	@Column(name = "invite_user_id")
	private Integer inviteUserId;//"邀请人"

	@Column(name = "member_user_id")
	private Integer memberUserId;//"成员ID"

	@Column(name = "review_status")
	private Integer reviewStatus;//"审核状态（0：待审核，1：申请驳回，2：审核通过）"

	@Column(name = "application_reason")
	private String applicationReason;//"邀请或申请原因"

	@Column(name = "reject_reason")
	private String rejectReason;//"驳回原因"

	@Column(name = "c_time")
	private Date ctime;//"创建时间"

	//columns END
		
	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return this.id;
	}
		
	public void setCircleId(Integer circleId) {
		this.circleId = circleId;
	}

	public Integer getCircleId() {
		return this.circleId;
	}
		
	public void setInviteUserId(Integer inviteUserId) {
		this.inviteUserId = inviteUserId;
	}

	public Integer getInviteUserId() {
		return this.inviteUserId;
	}
		
	public void setMemberUserId(Integer memberUserId) {
		this.memberUserId = memberUserId;
	}

	public Integer getMemberUserId() {
		return this.memberUserId;
	}
		
	public void setReviewStatus(Integer reviewStatus) {
		this.reviewStatus = reviewStatus;
	}

	public Integer getReviewStatus() {
		return this.reviewStatus;
	}
		
	public void setApplicationReason(String applicationReason) {
		this.applicationReason = applicationReason;
	}

	public String getApplicationReason() {
		return this.applicationReason;
	}
		
	public void setRejectReason(String rejectReason) {
		this.rejectReason = rejectReason;
	}

	public String getRejectReason() {
		return this.rejectReason;
	}
		
	public void setCtime(Date ctime) {
		this.ctime = ctime;
	}

	public Date getCtime() {
		return this.ctime;
	}

}

