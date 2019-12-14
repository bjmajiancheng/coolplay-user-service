/*
 * 北京果敢时代科技有限公司
 * 北京市朝阳区望京SOHO T3 B座1607
 * 邮编：100022
 * 网址：www.davdian.com
 */

package com.coolplay.user.user.model;

import com.coolplay.user.common.handler.Sortable;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.*;
import javax.persistence.*;

/**
 * @author  shawn
 * @version 1.0
 * @since 1.0
 */
@Table(name = "d_post_comment")
public class PostCommentModel extends Sortable {
	private static final long serialVersionUID = 1L;

	//columns START
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;//"主键"

	@Column(name = "post_id")
	private Integer postId;//"圈子ID"

	@Column(name = "comment_user_id")
	private Integer commentUserId;//"评论人"

	@Column(name = "comment_content")
	private String commentContent;//"评论内容"

	@Column(name = "comment_level")
	private Integer commentLevel;//"评论级别 1：直接评论帖子，2：回复帖子评论"

	@Column(name = "post_comment_id")
	private Integer postCommentId;//"当评论级别为2时，指定回复帖子ID"

	@Column(name = "reply_user_id")
	private Integer replyUserId;//"回复人"

	@Column(name = "is_del")
	private Integer isDel;//"是否删除（0：未删除，1：已删除）"

	@Column(name = "c_time")
	private Date ctime;//"创建时间"

	//columns END

	@Transient
	private List<PostCommentModel> commentList = new ArrayList<PostCommentModel>();

	@Transient
	private String commentNickName = "";

	@Transient
	private String commentHeadImage = "";

	@Transient
	private String replyNickName = "";

	@Transient
	private String replyHeadImage = "";
		
	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return this.id;
	}
		
	public void setPostId(Integer postId) {
		this.postId = postId;
	}

	public Integer getPostId() {
		return this.postId;
	}
		
	public void setCommentUserId(Integer commentUserId) {
		this.commentUserId = commentUserId;
	}

	public Integer getCommentUserId() {
		return this.commentUserId;
	}
		
	public void setCommentContent(String commentContent) {
		this.commentContent = commentContent;
	}

	public String getCommentContent() {
		return this.commentContent;
	}
		
	public void setCommentLevel(Integer commentLevel) {
		this.commentLevel = commentLevel;
	}

	public Integer getCommentLevel() {
		return this.commentLevel;
	}
		
	public void setPostCommentId(Integer postCommentId) {
		this.postCommentId = postCommentId;
	}

	public Integer getPostCommentId() {
		return this.postCommentId;
	}

	public Integer getReplyUserId() {
		return replyUserId;
	}

	public void setReplyUserId(Integer replyUserId) {
		this.replyUserId = replyUserId;
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

	public List<PostCommentModel> getCommentList() {
		return commentList;
	}

	public void setCommentList(List<PostCommentModel> commentList) {
		this.commentList = commentList;
	}

	public String getCommentNickName() {
		return commentNickName;
	}

	public void setCommentNickName(String commentNickName) {
		this.commentNickName = commentNickName;
	}

	public String getCommentHeadImage() {
		return commentHeadImage;
	}

	public void setCommentHeadImage(String commentHeadImage) {
		this.commentHeadImage = commentHeadImage;
	}

	public String getReplyNickName() {
		return replyNickName;
	}

	public void setReplyNickName(String replyNickName) {
		this.replyNickName = replyNickName;
	}

	public String getReplyHeadImage() {
		return replyHeadImage;
	}

	public void setReplyHeadImage(String replyHeadImage) {
		this.replyHeadImage = replyHeadImage;
	}
}

