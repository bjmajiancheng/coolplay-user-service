/*
 * 北京果敢时代科技有限公司
 * 北京市朝阳区望京SOHO T3 B座1607
 * 邮编：100022
 * 网址：www.davdian.com
 */

package com.coolplay.company.core.model;

import com.coolplay.company.common.handler.Sortable;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.valuehandling.UnwrapValidatedValue;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author  davdian
 * @version 1.0
 * @since 1.0
 */
@Table(name = "d_company_user")
public class UserModel extends Sortable {
	private static final long serialVersionUID = 1L;

	//columns START
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;//"主键"

	@Column(name = "company_id")
	private Integer companyId;//"企业ID"

	@Column(name = "user_name")
	@NotEmpty
	@Size(min = 4, max = 20)
	private String userName;//"用户名"

	@NotEmpty
	@Size(min = 8, max = 64)
	@JsonIgnore
	private String password;//"密码"

	@Column(name = "display_name")
	@NotEmpty
	@Size(min = 4, max = 20)
	private String displayName;//"显示名称"

	@Column(name = "contact_phone")
	private String contactPhone;//"联系电话"

	@Column(name = "dept_id")
	private Integer deptId;//"部门ID"

	@Column(name = "last_login_ip")
	private String lastLoginIp;//"最后登录IP"

	@Column(name = "last_login_time")
	private Date lastLoginTime;//"最后登录时间"

	@Column(name = "account_non_locked")
	private Boolean accountNonLocked;

	@Column(name = "account_non_expired")
	private Boolean accountNonExpired;

	@Column(name = "credentials_non_expired")
	private Boolean credentialsNonExpired;

	@Column(name = "is_admin")
	private Integer isAdmin;//"是否是超级用户（0：否， 1：是）"

	@Column(name = "enabled")
	private Integer enabled;//"用户状态（0：正常，1：启用，2：禁用）"

	@Column(name = "last_password_reset")
	private Date lastPasswordReset;

	@Column(name = "c_time")
	private Date ctime;//"创建时间"

	//columns END

	@Transient
	private String roleName;

	@Transient
	private String deptName;

	@Transient
	private List<Integer> roleIds;//"角色Ids"
		
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
		
	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserName() {
		return this.userName;
	}
		
	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassword() {
		return this.password;
	}
		
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getDisplayName() {
		return this.displayName;
	}
		
	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	public String getContactPhone() {
		return this.contactPhone;
	}
		
	public void setDeptId(Integer deptId) {
		this.deptId = deptId;
	}

	public Integer getDeptId() {
		return this.deptId;
	}
		
	public void setLastLoginIp(String lastLoginIp) {
		this.lastLoginIp = lastLoginIp;
	}

	public String getLastLoginIp() {
		return this.lastLoginIp;
	}
		
	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public Date getLastLoginTime() {
		return this.lastLoginTime;
	}

	public Boolean getAccountNonLocked() {
		return accountNonLocked;
	}

	public void setAccountNonLocked(Boolean accountNonLocked) {
		this.accountNonLocked = accountNonLocked;
	}

	public Boolean getAccountNonExpired() {
		return accountNonExpired;
	}

	public void setAccountNonExpired(Boolean accountNonExpired) {
		this.accountNonExpired = accountNonExpired;
	}

	public Boolean getCredentialsNonExpired() {
		return credentialsNonExpired;
	}

	public void setCredentialsNonExpired(Boolean credentialsNonExpired) {
		this.credentialsNonExpired = credentialsNonExpired;
	}

	public void setIsAdmin(Integer isAdmin) {
		this.isAdmin = isAdmin;
	}

	public Integer getIsAdmin() {
		return this.isAdmin;
	}

	public Integer getEnabled() {
		return enabled;
	}

	public void setEnabled(Integer enabled) {
		this.enabled = enabled;
	}

	public void setCtime(Date ctime) {
		this.ctime = ctime;
	}

	public Date getCtime() {
		return this.ctime;
	}

	public Date getLastPasswordReset() {
		return lastPasswordReset;
	}

	public void setLastPasswordReset(Date lastPasswordReset) {
		this.lastPasswordReset = lastPasswordReset;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public List<Integer> getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(List<Integer> roleIds) {
		this.roleIds = roleIds;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
}

