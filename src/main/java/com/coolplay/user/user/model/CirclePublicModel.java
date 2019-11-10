/*
 * 北京果敢时代科技有限公司
 * 北京市朝阳区望京SOHO T3 B座1607
 * 邮编：100022
 * 网址：www.davdian.com
 */

package com.coolplay.user.user.model;

import java.util.Date;
import javax.persistence.*;
import com.coolplay.user.common.handler.Sortable;
import com.coolplay.user.common.utils.DateStyle;
import com.coolplay.user.common.utils.DateUtil;

/**
 * @author  shawn
 * @version 1.0
 * @since 1.0
 */
@Table(name = "d_circle_public")
public class CirclePublicModel extends Sortable {
	private static final long serialVersionUID = 1L;

	//columns START
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;//"主键"

	@Column(name = "circle_id")
	private Integer circleId;//"酷玩圈ID"

	@Column(name = "public_content")
	private String publicContent;//"公告内容"

	@Column(name = "c_time")
	private Date ctime;//"创建时间"

	//columns END

	@Transient
	private String ctimeStr;//创建时间
		
	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return this.id;
	}
		
	public void setCircleId(Integer circleId) {
		this.circleId = circleId;
	}

	public Integer getCircleId() {
		return this.circleId;
	}
		
	public void setPublicContent(String publicContent) {
		this.publicContent = publicContent;
	}

	public String getPublicContent() {
		return this.publicContent;
	}
		
	public void setCtime(Date ctime) {
		this.ctime = ctime;
	}

	public Date getCtime() {
		return this.ctime;
	}

	public String getCtimeStr() {
		if(this.getCtime() != null) {
			this.ctimeStr = DateUtil.DateToString(this.ctime, DateStyle.YYYY_MM_DD_HH_MM_SS);
		}
		return ctimeStr;
	}

	public void setCtimeStr(String ctimeStr) {
		this.ctimeStr = ctimeStr;
	}
}

