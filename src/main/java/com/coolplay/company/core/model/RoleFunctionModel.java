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
@Table(name = "d_company_role_function")
public class RoleFunctionModel extends Sortable {
	private static final long serialVersionUID = 1L;

	//columns START
	@Column(name = "role_id")
	private Integer roleId;//"角色ID"

	@Column(name = "function_id")
	private Integer functionId;//"菜单ID"
	//columns END
		
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public Integer getRoleId() {
		return this.roleId;
	}
		
	public void setFunctionId(Integer funcationId) {
		this.functionId = funcationId;
	}

	public Integer getFunctionId() {
		return this.functionId;
	}

}

