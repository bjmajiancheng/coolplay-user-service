/*
 * 北京果敢时代科技有限公司
 * 北京市朝阳区望京SOHO T3 B座1607
 * 邮编：100022
 * 网址：www.davdian.com
 */

package com.coolplay.user.user.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import com.coolplay.user.common.handler.Sortable;
import com.coolplay.user.common.utils.DateStyle;
import com.coolplay.user.common.utils.DateUtil;

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
	private Date applicationTime;//"申请时间"

	@Column(name = "review_time")
	private Date reviewTime;//"审核时间"

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

	@Transient
	private String queryStr;

	@Transient
	private String nickName = "";//圈主昵称

	@Transient
	private String userDesc = "";//用户描述

	@Transient
	private String headImage = "";//圈主头图

	@Transient
	private Integer isMember = 0;//是圈子成员

	@Transient
	private Integer isOwner = 0;//是否是圈主

	@Transient
	private List<LabelModel> labelList = new ArrayList<LabelModel>();//标签集合

	@Transient
	private Integer reviewMemberCnt = 0;//待审批人数

	@Transient
	private Integer isCollect = 0;//是否收藏

	@Transient
	private List<Integer> labelIds = new ArrayList<Integer>();//标签Ids

	@Transient
	private String ctimeStr = "";//创建时间戳

	@Transient
	private String publicContent = "";//公告内容

	@Transient
	private Integer postCnt = 0;//帖子数

	@Transient
	private Integer followCnt = 0;//圈主关注人数

	@Transient
	private Integer fansCnt = 0;//圈主粉丝人数

	@Transient
	private List<CirclePublicModel> circlePublics = new ArrayList<CirclePublicModel>();//圈子公告集合

	@Transient
	private List<PostModel> circlePosts = new ArrayList<PostModel>();//圈子帖子集合

	@Transient
	private Integer type = 0;//类型, 1：我加入的圈子，2：我创建的圈子

	@Transient
	private List<Integer> ids = new ArrayList<Integer>();//圈子ID集合
		
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
		
	public void setApplicationTime(Date applicationTime) {
		this.applicationTime = applicationTime;
	}

	public Date getApplicationTime() {
		return this.applicationTime;
	}
		
	public void setReviewTime(Date reviewTime) {
		this.reviewTime = reviewTime;
	}

	public Date getReviewTime() {
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

	public String getQueryStr() {
		return queryStr;
	}

	public void setQueryStr(String queryStr) {
		this.queryStr = queryStr;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getUserDesc() {
		return userDesc;
	}

	public void setUserDesc(String userDesc) {
		this.userDesc = userDesc;
	}

	public String getHeadImage() {
		return headImage;
	}

	public void setHeadImage(String headImage) {
		this.headImage = headImage;
	}

	public Integer getIsMember() {
		return isMember;
	}

	public void setIsMember(Integer isMember) {
		this.isMember = isMember;
	}

	public Integer getIsOwner() {
		return isOwner;
	}

	public void setIsOwner(Integer isOwner) {
		this.isOwner = isOwner;
	}

	public List<LabelModel> getLabelList() {
		return labelList;
	}

	public void setLabelList(List<LabelModel> labelList) {
		this.labelList = labelList;
	}

	public Integer getReviewMemberCnt() {
		return reviewMemberCnt;
	}

	public void setReviewMemberCnt(Integer reviewMemberCnt) {
		this.reviewMemberCnt = reviewMemberCnt;
	}

	public Integer getIsCollect() {
		return isCollect;
	}

	public void setIsCollect(Integer isCollect) {
		this.isCollect = isCollect;
	}

	public List<Integer> getLabelIds() {
		return labelIds;
	}

	public void setLabelIds(List<Integer> labelIds) {
		this.labelIds = labelIds;
	}

	public String getCtimeStr() {
		if(this.getCtime() != null) {
			this.ctimeStr = DateUtil.DateToString(this.getCtime(), DateStyle.YYYY_MM_DD_HH_MM);
		}
		return ctimeStr;
	}

	public void setCtimeStr(String ctimeStr) {
		this.ctimeStr = ctimeStr;
	}

	public String getPublicContent() {
		return publicContent;
	}

	public void setPublicContent(String publicContent) {
		this.publicContent = publicContent;
	}

	public Integer getPostCnt() {
		return postCnt;
	}

	public void setPostCnt(Integer postCnt) {
		this.postCnt = postCnt;
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

	public List<CirclePublicModel> getCirclePublics() {
		return circlePublics;
	}

	public void setCirclePublics(List<CirclePublicModel> circlePublics) {
		this.circlePublics = circlePublics;
	}

	public List<PostModel> getCirclePosts() {
		return circlePosts;
	}

	public void setCirclePosts(List<PostModel> circlePosts) {
		this.circlePosts = circlePosts;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public List<Integer> getIds() {
		return ids;
	}

	public void setIds(List<Integer> ids) {
		this.ids = ids;
	}
}

