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
@Table(name = "d_circle")
public class CircleModel extends Sortable {
	private static final long serialVersionUID = 1L;

	//columns START
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;//"主键"

	@Column(name = "user_id")
	private Integer userId;//"圈主"

	@Column(name = "circle_name")
	private String circleName;//"圈子名称"

	@Column(name = "circle_desc")
	private String circleDesc;//"圈子描述"

	@Column(name = "circle_type")
	private Integer circleType;//"圈子类型（1：公开圈，2：私密圈）"

	@Column(name = "reason")
	private String reason;//"建圈理由"

	@Column(name = "backgroud_img")
	private String backgroudImg;//"背景图"

	@Column(name = "member_cnt")
	private Integer memberCnt;//"成员人数"

	@Column(name = "like_cnt")
	private Integer likeCnt;//"点赞人数"

	@Column(name = "share_cnt")
	private Integer shareCnt;//"分享人数"

	@Column(name = "comment_cnt")
	private Integer commentCnt;//"评论人数"

	@Column(name = "read_cnt")
	private Integer readCnt;//"浏览次数"

	@Column(name = "application_time")
	private Integer applicationTime;//"申请时间"

	@Column(name = "review_time")
	private Integer reviewTime;//"审核时间"

	@Column(name = "review_status")
	private Integer reviewStatus;//"审核状态（0：未审核，1：已审核）"

	@Column(name = "status")
	private Integer status;//"审核结果（0：默认，1：通过，2：驳回）"

	@Column(name = "reject_reason")
	private String rejectReason;//"驳回原因"

	@Column(name = "company_id")
	private Integer companyId;//"关联企业ID"

	@Column(name = "disabled")
	private Integer disabled;//"是否禁用（0：未禁用，1：已禁用）"

	@Column(name = "c_time")
	private Date ctime;//"创建时间"

	//columns END
		
	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return this.id;
	}
		
	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getUserId() {
		return this.userId;
	}
		
	public void setCircleName(String circleName) {
		this.circleName = circleName;
	}

	public String getCircleName() {
		return this.circleName;
	}
		
	public void setCircleDesc(String circleDesc) {
		this.circleDesc = circleDesc;
	}

	public String getCircleDesc() {
		return this.circleDesc;
	}
		
	public void setCircleType(Integer circleType) {
		this.circleType = circleType;
	}

	public Integer getCircleType() {
		return this.circleType;
	}
		
	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getReason() {
		return this.reason;
	}
		
	public void setBackgroudImg(String backgroudImg) {
		this.backgroudImg = backgroudImg;
	}

	public String getBackgroudImg() {
		return this.backgroudImg;
	}
		
	public void setMemberCnt(Integer memberCnt) {
		this.memberCnt = memberCnt;
	}

	public Integer getMemberCnt() {
		return this.memberCnt;
	}
		
	public void setLikeCnt(Integer likeCnt) {
		this.likeCnt = likeCnt;
	}

	public Integer getLikeCnt() {
		return this.likeCnt;
	}
		
	public void setShareCnt(Integer shareCnt) {
		this.shareCnt = shareCnt;
	}

	public Integer getShareCnt() {
		return this.shareCnt;
	}
		
	public void setCommentCnt(Integer commentCnt) {
		this.commentCnt = commentCnt;
	}

	public Integer getCommentCnt() {
		return this.commentCnt;
	}
		
	public void setReadCnt(Integer readCnt) {
		this.readCnt = readCnt;
	}

	public Integer getReadCnt() {
		return this.readCnt;
	}
		
	public void setApplicationTime(Integer applicationTime) {
		this.applicationTime = applicationTime;
	}

	public Integer getApplicationTime() {
		return this.applicationTime;
	}
		
	public void setReviewTime(Integer reviewTime) {
		this.reviewTime = reviewTime;
	}

	public Integer getReviewTime() {
		return this.reviewTime;
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
		
	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public Integer getCompanyId() {
		return this.companyId;
	}
		
	public void setDisabled(Integer disabled) {
		this.disabled = disabled;
	}

	public Integer getDisabled() {
		return this.disabled;
	}
		
	public void setCtime(Date ctime) {
		this.ctime = ctime;
	}

	public Date getCtime() {
		return this.ctime;
	}

}

