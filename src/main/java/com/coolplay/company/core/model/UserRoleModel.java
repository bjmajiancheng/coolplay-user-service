/*
 * 北京果敢时代科技有限公司
 * 北京市朝阳区望京SOHO T3 B座1607
 * 邮编：100022
 * 网址：www.davdian.com
 */

package com.coolplay.company.core.model;

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
@Table(name = "d_company_user_role")
public class UserRoleModel extends Sortable {
	private static final long serialVersionUID = 1L;

	//columns START
	@Column(name = "user_id")
	private Integer userId;//"用户ID"

	@Column(name = "role_id")
	private Integer roleId;//"角色ID"

	//columns END
	@Transient
	private String roleName;//"角色名称"

	@Transient
	private Integer status;//"是否启用（0：不启用，1：启用）"
		
	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getUserId() {
		return this.userId;
	}
		
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public Integer getRoleId() {
		return this.roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
}

