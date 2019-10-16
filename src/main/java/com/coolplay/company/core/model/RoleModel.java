/*
 * 北京果敢时代科技有限公司
 * 北京市朝阳区望京SOHO T3 B座1607
 * 邮编：100022
 * 网址：www.davdian.com
 */

package com.coolplay.company.core.model;

import com.coolplay.company.common.handler.Sortable;
import org.springframework.data.annotation.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.*;
import javax.persistence.Id;
import javax.persistence.Transient;

/**
 * @author  shawn
 * @version 1.0
 * @since 1.0
 */
@Table(name = "d_company_role")
public class RoleModel extends Sortable {
	private static final long serialVersionUID = 1L;

	//columns START
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;//"主键"

	@Column(name = "role_name")
	private String roleName;//"角色名称"

	@Column(name = "company_id")
	private Integer companyId;//"公司ID"

	@Column(name = "status")
	private Integer status;//"是否启用（0：不启用，1：启用）"

	@Column(name = "c_time")
	private Date ctime;//"创建时间"
	//columns END

	@Transient
	private List<Integer> functionIds;
		
	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return this.id;
	}
		
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRoleName() {
		return this.roleName;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getStatus() {
		return this.status;
	}
		
	public void setCtime(Date ctime) {
		this.ctime = ctime;
	}

	public Date getCtime() {
		return this.ctime;
	}

	public List<Integer> getFunctionIds() {
		return functionIds;
	}

	public void setFunctionIds(List<Integer> functionIds) {
		this.functionIds = functionIds;
	}
}

