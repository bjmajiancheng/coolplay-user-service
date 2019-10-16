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
@Table(name = "d_company_circle")
public class CompanyCircleModel extends Sortable {
	private static final long serialVersionUID = 1L;

	//columns START
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;//"主键"

	@Column(name = "circle_id")
	private Integer circleId;//"圈子ID"

	@Column(name = "company_id")
	private Integer companyId;//"企业ID"

	@Column(name = "application_time")
	private Date applicationTime;//"申请时间"

	@Column(name = "application_user_id")
	private Integer applicationUserId;//"申请用户ID"

	@Column(name = "review_status")
	private Integer reviewStatus;//"审核状态（0：未审核， 1：已审核）"

	@Column(name = "status")
	private Integer status;//"审核结果（0：默认，1：通过，2：驳回）"

	@Column(name = "reject_reason")
	private String rejectReason;//"不通过原因"

	@Column(name = "is_disable")
	private Integer isDisable;//"是否取消关联"

	@Column(name = "disable_reason")
	private String disableReason;//"取消原因"

	@Column(name = "c_time")
	private Date ctime;//"创建时间"

	//columns END

	@Transient
	private String applicationStartTime;

	@Transient
	private String applicationEndTime;

	@Transient
	private String applicationUserName;//"申请用户名称"

	@Transient
	private String circleName;//"圈子名称"

	@Transient
	private Integer memberCnt;//"圈子人数"

	@Transient
	private String publicContent;//"圈子公告"

	@Transient
	private String circleLabels;//"圈子标签"
		
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
		
	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public Integer getCompanyId() {
		return this.companyId;
	}
		
	public void setApplicationTime(Date applicationTime) {
		this.applicationTime = applicationTime;
	}

	public Date getApplicationTime() {
		return this.applicationTime;
	}
		
	public void setApplicationUserId(Integer applicationUserId) {
		this.applicationUserId = applicationUserId;
	}

	public Integer getApplicationUserId() {
		return this.applicationUserId;
	}
		
	public void setReviewStatus(Integer reviewStatus) {
		this.reviewStatus = reviewStatus;
	}

	public Integer getReviewStatus() {
		return this.reviewStatus;
	}
		
	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getStatus() {
		return this.status;
	}
		
	public void setRejectReason(String rejectReason) {
		this.rejectReason = rejectReason;
	}

	public String getRejectReason() {
		return this.rejectReason;
	}
		
	public void setIsDisable(Integer isDisable) {
		this.isDisable = isDisable;
	}

	public Integer getIsDisable() {
		return this.isDisable;
	}
		
	public void setDisableReason(String disableReason) {
		this.disableReason = disableReason;
	}

	public String getDisableReason() {
		return this.disableReason;
	}
		
	public void setCtime(Date ctime) {
		this.ctime = ctime;
	}

	public Date getCtime() {
		return this.ctime;
	}

	public String getApplicationStartTime() {
		return applicationStartTime;
	}

	public void setApplicationStartTime(String applicationStartTime) {
		this.applicationStartTime = applicationStartTime;
	}

	public String getApplicationEndTime() {
		return applicationEndTime;
	}

	public void setApplicationEndTime(String applicationEndTime) {
		this.applicationEndTime = applicationEndTime;
	}

	public String getApplicationUserName() {
		return applicationUserName;
	}

	public void setApplicationUserName(String applicationUserName) {
		this.applicationUserName = applicationUserName;
	}

	public String getCircleName() {
		return circleName;
	}

	public void setCircleName(String circleName) {
		this.circleName = circleName;
	}

	public Integer getMemberCnt() {
		return memberCnt;
	}

	public void setMemberCnt(Integer memberCnt) {
		this.memberCnt = memberCnt;
	}

	public String getPublicContent() {
		return publicContent;
	}

	public void setPublicContent(String publicContent) {
		this.publicContent = publicContent;
	}

	public String getCircleLabels() {
		return circleLabels;
	}

	public void setCircleLabels(String circleLabels) {
		this.circleLabels = circleLabels;
	}
}

