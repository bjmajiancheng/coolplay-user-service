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
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.*;

/**
 * @author  shawn
 * @version 1.0
 * @since 1.0
 */
@Table(name = "d_user_like")
public class UserLikeModel extends Sortable {
	private static final long serialVersionUID = 1L;

	//columns START
	@Column(name = "id")
	private Integer id;//"主键"

	@Column(name = "collect_type")
	private Integer collectType;//"收藏类型（1：圈子2：帖子3：俱乐部4：基地）"

	@Column(name = "collect_type_id")
	private Integer collectTypeId;//"收藏类型Id（圈子、帖子、俱乐部或基地id）"

	@Column(name = "user_id")
	private Integer userId;//"用户ID"

	@Column(name = "is_del")
	private Integer isDel;//"是否移除（0：未移除，1：已移除）"

	@Column(name = "c_time")
	private Date ctime;//"创建时间"

	//columns END
		
	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return this.id;
	}
		
	public void setCollectType(Integer collectType) {
		this.collectType = collectType;
	}

	public Integer getCollectType() {
		return this.collectType;
	}
		
	public void setCollectTypeId(Integer collectTypeId) {
		this.collectTypeId = collectTypeId;
	}

	public Integer getCollectTypeId() {
		return this.collectTypeId;
	}
		
	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getUserId() {
		return this.userId;
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

}

