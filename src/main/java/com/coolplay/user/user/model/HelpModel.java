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
@Table(name = "d_help")
public class HelpModel extends Sortable {
	private static final long serialVersionUID = 1L;

	//columns START
	@Column(name = "id")
	private Integer id;//"主键"

	@Column(name = "help_title")
	private String helpTitle;//"帮助标题"

	@Column(name = "help_content")
	private String helpContent;//"帮助内容"

	@Column(name = "help_level")
	private Integer helpLevel;//"帮助级别"

	@Column(name = "parent_id")
	private Integer parentId;//"帮助父ID"

	@Column(name = "is_del")
	private Integer isDel;//"是否删除"

	@Column(name = "system_user_id")
	private Integer systemUserId;//"系统用户ID"

	@Column(name = "c_time")
	private Date ctime;//"创建时间"

	//columns END

	@Transient
	private List<HelpModel> subHelpModels = new ArrayList<HelpModel>();//帮助列表
		
	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return this.id;
	}
		
	public void setHelpTitle(String helpTitle) {
		this.helpTitle = helpTitle;
	}

	public String getHelpTitle() {
		return this.helpTitle;
	}
		
	public void setHelpContent(String helpContent) {
		this.helpContent = helpContent;
	}

	public String getHelpContent() {
		return this.helpContent;
	}
		
	public void setHelpLevel(Integer helpLevel) {
		this.helpLevel = helpLevel;
	}

	public Integer getHelpLevel() {
		return this.helpLevel;
	}
		
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public Integer getParentId() {
		return this.parentId;
	}
		
	public void setIsDel(Integer isDel) {
		this.isDel = isDel;
	}

	public Integer getIsDel() {
		return this.isDel;
	}
		
	public void setSystemUserId(Integer systemUserId) {
		this.systemUserId = systemUserId;
	}

	public Integer getSystemUserId() {
		return this.systemUserId;
	}
		
	public void setCtime(Date ctime) {
		this.ctime = ctime;
	}

	public Date getCtime() {
		return this.ctime;
	}

	public List<HelpModel> getSubHelpModels() {
		return subHelpModels;
	}

	public void setSubHelpModels(List<HelpModel> subHelpModels) {
		this.subHelpModels = subHelpModels;
	}
}

