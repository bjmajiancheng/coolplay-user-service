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
@Table(name = "d_system_version")
public class SystemVersionModel extends Sortable {
	private static final long serialVersionUID = 1L;

	//columns START
	@Column(name = "id")
	private Integer id;//"id"

	@Column(name = "app_type")
	private String appType;//"APP类型"

	@Column(name = "version")
	private String version;//"APP版本"

	@Column(name = "is_forced_update")
	private Boolean isForcedUpdate;//"是否强制更新"

	@Column(name = "app_url")
	private String appUrl;//"APP下载URL"

	@Column(name = "app_path")
	private String appPath;//"APP文件地址"

	@Column(name = "c_time")
	private Date ctime;//"创建时间"

	@Column(name = "u_time")
	private Date utime;//"更新时间"

	//columns END
		
	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return this.id;
	}
		
	public void setAppType(String appType) {
		this.appType = appType;
	}

	public String getAppType() {
		return this.appType;
	}
		
	public void setVersion(String version) {
		this.version = version;
	}

	public String getVersion() {
		return this.version;
	}
		
	public void setIsForcedUpdate(Boolean isForcedUpdate) {
		this.isForcedUpdate = isForcedUpdate;
	}

	public Boolean getIsForcedUpdate() {
		return this.isForcedUpdate;
	}
		
	public void setAppUrl(String appUrl) {
		this.appUrl = appUrl;
	}

	public String getAppUrl() {
		return this.appUrl;
	}
		
	public void setAppPath(String appPath) {
		this.appPath = appPath;
	}

	public String getAppPath() {
		return this.appPath;
	}
		
	public void setCtime(Date ctime) {
		this.ctime = ctime;
	}

	public Date getCtime() {
		return this.ctime;
	}
		
	public void setUtime(Date utime) {
		this.utime = utime;
	}

	public Date getUtime() {
		return this.utime;
	}

}

