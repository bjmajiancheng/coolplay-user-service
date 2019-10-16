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
@Table(name = "d_company_function")
public class FunctionModel extends Sortable {
	private static final long serialVersionUID = 1L;

	//columns START
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;//"主键"

	@Column(name = "parent_id")
	private Integer parentId;//"父ID"

	@Column(name = "function_name")
	private String functionName;//"权限名称"

	@Column(name = "display")
	private Integer display;//"是否显示到菜单栏"

	@Column(name = "status")
	private Integer status;//"是否启用（0：不启用，1：启用）"

	@Column(name = "action")
	private String action;//"请求链接"

	@Column(name = "icon")
	private String icon;//"菜单图标"

	@Column(name = "sort")
	private Integer sort;//"排序"

	@Column(name = "c_time")
	private Date ctime;//"创建时间"
	//columns END

	@Transient
	private String parentFunctionName;//"父菜单名称"
		
	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return this.id;
	}
		
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public Integer getParentId() {
		return this.parentId;
	}
		
	public void setFunctionName(String functionName) {
		this.functionName = functionName;
	}

	public String getFunctionName() {
		return this.functionName;
	}
		
	public void setDisplay(Integer display) {
		this.display = display;
	}

	public Integer getDisplay() {
		return this.display;
	}
		
	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getStatus() {
		return this.status;
	}
		
	public void setAction(String action) {
		this.action = action;
	}

	public String getAction() {
		return this.action;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public Integer getSort() {
		return this.sort;
	}
		
	public void setCtime(Date ctime) {
		this.ctime = ctime;
	}

	public Date getCtime() {
		return this.ctime;
	}

	public String getParentFunctionName() {
		return parentFunctionName;
	}

	public void setParentFunctionName(String parentFunctionName) {
		this.parentFunctionName = parentFunctionName;
	}
}

