/*
 * 北京果敢时代科技有限公司
 * 北京市朝阳区望京SOHO T3 B座1607
 * 邮编：100022
 * 网址：www.davdian.com
 */

package com.coolplay.company.company.model;

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
@Table(name = "d_company_user_opt_log")
public class CompanyUserOptLogModel extends Sortable {
	private static final long serialVersionUID = 1L;

	//columns START
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;//"主键"

	@Column(name = "company_id")
	private Integer companyId;//"公司ID"

	@Column(name = "company_user_id")
	private Integer companyUserId;//"公司用户ID"

	@Column(name = "opt_page")
	private String optPage;//"操作页面"

	@Column(name = "opt_type")
	private Integer optType;//"操作类型（1：增加，2：删除，3：修改）"

	@Column(name = "opt_type_desc")
	private String optTypeDesc;//"操作事件描述"

	@Column(name = "c_time")
	private Date ctime;//"创建时间"
	//columns END
		
	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return this.id;
	}
		
	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public Integer getCompanyId() {
		return this.companyId;
	}
		
	public void setCompanyUserId(Integer companyUserId) {
		this.companyUserId = companyUserId;
	}

	public Integer getCompanyUserId() {
		return this.companyUserId;
	}
		
	public void setOptPage(String optPage) {
		this.optPage = optPage;
	}

	public String getOptPage() {
		return this.optPage;
	}
		
	public void setOptType(Integer optType) {
		this.optType = optType;
	}

	public Integer getOptType() {
		return this.optType;
	}
		
	public void setOptTypeDesc(String optTypeDesc) {
		this.optTypeDesc = optTypeDesc;
	}

	public String getOptTypeDesc() {
		return this.optTypeDesc;
	}
		
	public void setCtime(Date ctime) {
		this.ctime = ctime;
	}

	public Date getCtime() {
		return this.ctime;
	}

}

