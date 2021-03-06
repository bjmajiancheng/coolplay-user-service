/*
 * 北京果敢时代科技有限公司
 * 北京市朝阳区望京SOHO T3 B座1607
 * 邮编：100022
 * 网址：www.davdian.com
 */

package com.coolplay.user.user.model;

import com.coolplay.user.common.handler.Sortable;

import java.util.Date;
import javax.persistence.*;

/**
 * @author  shawn
 * @version 1.0
 * @since 1.0
 */
@Table(name = "d_label")
public class LabelModel extends Sortable {
	private static final long serialVersionUID = 1L;

	//columns START
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;//"主键"

	@Column(name = "cat_id")
	private Integer catId;//"所属分类"

	@Column(name = "label_name")
	private String labelName;//"标签名称"

	@Column(name = "creator")
	private String creator;//"创建人"

	@Column(name = "creator_user_id")
	private Integer creatorUserId;//"创建人用户ID"

	@Column(name = "type")
	private Integer type;//"标签类型，1：系统创建，2：用户创建"

	@Column(name = "status")
	private Integer status;//"状态（0：正常显示，1：不显示）"

	@Column(name = "is_del")
	private Integer isDel;//"是否删除"

	@Column(name = "c_time")
	private Date ctime;//"创建时间"

	//columns END

	@Transient
	private Integer postId;

	@Transient
	private Integer circleId;

	@Transient
	private Integer userId;

	@Transient
	private Integer baseId;
		
	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return this.id;
	}
		
	public void setCatId(Integer catId) {
		this.catId = catId;
	}

	public Integer getCatId() {
		return this.catId;
	}
		
	public void setLabelName(String labelName) {
		this.labelName = labelName;
	}

	public String getLabelName() {
		return this.labelName;
	}
		
	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getCreator() {
		return this.creator;
	}

	public Integer getCreatorUserId() {
		return creatorUserId;
	}

	public void setCreatorUserId(Integer creatorUserId) {
		this.creatorUserId = creatorUserId;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getStatus() {
		return this.status;
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

	public Integer getPostId() {
		return postId;
	}

	public void setPostId(Integer postId) {
		this.postId = postId;
	}

	public Integer getCircleId() {
		return circleId;
	}

	public void setCircleId(Integer circleId) {
		this.circleId = circleId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getBaseId() {
		return baseId;
	}

	public void setBaseId(Integer baseId) {
		this.baseId = baseId;
	}
}

