/*
 * 北京果敢时代科技有限公司
 * 北京市朝阳区望京SOHO T3 B座1607
 * 邮编：100022
 * 网址：www.davdian.com
 */

package com.coolplay.company.company.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.*;
import com.coolplay.company.common.handler.Sortable;

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

}

