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
@Table(name = "d_post")
public class PostModel extends Sortable {
	private static final long serialVersionUID = 1L;

	//columns START
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;//"主键"

	@Column(name = "post_title")
	private String postTitle;//"帖子标题"

	@Column(name = "post_content")
	private String postContent;//"帖子内容（支持富文本）"

	@Column(name = "user_id")
	private Integer userId;//"帖子创建人"

	@Column(name = "img_urls")
	private String imgUrls;//"图片地址"

	@Column(name = "video_url")
	private String videoUrl;//"视频链接"

	@Column(name = "videl_path")
	private String videlPath;//"视频存储路径"

	@Column(name = "is_top")
	private Integer isTop;//"是否置顶"

	@Column(name = "like_cnt")
	private Integer likeCnt;//"喜欢人数"

	@Column(name = "share_cnt")
	private Integer shareCnt;//"分享人数"

	@Column(name = "comment_cnt")
	private Integer commentCnt;//"评论人数"

	@Column(name = "read_cnt")
	private Integer readCnt;//"阅读人数"

	@Column(name = "thumb_up_cnt")
	private Integer thumbUpCnt;//"点赞人数"

	@Column(name = "collect_cnt")
	private Integer collectCnt;//"收藏人数"

	@Column(name = "is_del")
	private Integer isDel;//"是否删除（0：未删除，1：已删除）"

	@Column(name = "c_time")
	private Date ctime;//"创建时间"

	//columns END

	@Transient
	private String searchKeyword;

	@Transient
	private String nickName = "";

	@Transient
	private String headImage = "";

	@Transient
	private List<LabelModel> labelList = new ArrayList<LabelModel>();

	@Transient
	private String ctimeStr;

	@Transient
	private List<String> imgUrlList = new ArrayList<String>();

	@Transient
	private Integer isCollect = 0;//是否收藏, 1:是， 0:否

	@Transient
	private Integer isLike = 0;//是否点赞, 1:是, 0:否

	@Transient
	private List<String> labelNames = new ArrayList<String>();//标签名称集合

	@Transient
	private List<Integer> circleIds = new ArrayList<Integer>();//圈子Ids集合

	@Transient
	private List<Integer> labelIds = new ArrayList<Integer>();//帖子标签Ids集合

	@Transient
	private List<PostCommentModel> commentList = new ArrayList<PostCommentModel>();
		
	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return this.id;
	}
		
	public void setPostTitle(String postTitle) {
		this.postTitle = postTitle;
	}

	public String getPostTitle() {
		return this.postTitle;
	}
		
	public void setPostContent(String postContent) {
		this.postContent = postContent;
	}

	public String getPostContent() {
		return this.postContent;
	}
		
	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getUserId() {
		return this.userId;
	}
		
	public void setImgUrls(String imgUrls) {
		this.imgUrls = imgUrls;
	}

	public String getImgUrls() {
		return this.imgUrls;
	}
		
	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl;
	}

	public String getVideoUrl() {
		return this.videoUrl;
	}
		
	public void setVidelPath(String videlPath) {
		this.videlPath = videlPath;
	}

	public String getVidelPath() {
		return this.videlPath;
	}
		
	public void setIsTop(Integer isTop) {
		this.isTop = isTop;
	}

	public Integer getIsTop() {
		return this.isTop;
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
		
	public void setThumbUpCnt(Integer thumbUpCnt) {
		this.thumbUpCnt = thumbUpCnt;
	}

	public Integer getThumbUpCnt() {
		return this.thumbUpCnt;
	}

	public Integer getCollectCnt() {
		return collectCnt;
	}

	public void setCollectCnt(Integer collectCnt) {
		this.collectCnt = collectCnt;
	}

	public void setIsDel(Integer isDel) {
		this.isDel = isDel;
	}

	public Integer getIsDel() {
		return this.isDel;
	}
		
	public void setCtime(Date ctime) {
		this.ctime = ctime;
	}

	public Date getCtime() {
		return this.ctime;
	}

	public String getSearchKeyword() {
		return searchKeyword;
	}

	public void setSearchKeyword(String searchKeyword) {
		this.searchKeyword = searchKeyword;
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

	public List<LabelModel> getLabelList() {
		return labelList;
	}

	public void setLabelList(List<LabelModel> labelList) {
		this.labelList = labelList;
	}

	public String getCtimeStr() {
		if(this.getCtime() != null) {
			this.setCtimeStr(DateUtil.DateToString(this.getCtime(), DateStyle.YYYY_MM_DD_HH_MM_SS));
		}
		return ctimeStr;
	}

	public void setCtimeStr(String ctimeStr) {
		this.ctimeStr = ctimeStr;
	}

	public List<String> getImgUrlList() {
		if(StringUtils.isNotEmpty(this.getImgUrls())) {
			this.imgUrlList = Arrays.asList(this.getImgUrls().split(","));
		}
		return imgUrlList;
	}

	public void setImgUrlList(List<String> imgUrlList) {
		this.imgUrlList = imgUrlList;
	}

	public Integer getIsCollect() {
		return isCollect;
	}

	public void setIsCollect(Integer isCollect) {
		this.isCollect = isCollect;
	}

	public Integer getIsLike() {
		return isLike;
	}

	public void setIsLike(Integer isLike) {
		this.isLike = isLike;
	}

	public List<String> getLabelNames() {
		return labelNames;
	}

	public void setLabelNames(List<String> labelNames) {
		this.labelNames = labelNames;
	}

	public List<Integer> getCircleIds() {
		return circleIds;
	}

	public void setCircleIds(List<Integer> circleIds) {
		this.circleIds = circleIds;
	}

	public List<Integer> getLabelIds() {
		return labelIds;
	}

	public void setLabelIds(List<Integer> labelIds) {
		this.labelIds = labelIds;
	}

	public List<PostCommentModel> getCommentList() {
		return commentList;
	}

	public void setCommentList(List<PostCommentModel> commentList) {
		this.commentList = commentList;
	}
}

