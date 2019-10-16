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
@Table(name = "d_coolplay_base_label")
public class CoolplayBaseLabelModel extends Sortable {
	private static final long serialVersionUID = 1L;

	//columns START
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;//"主键"

	@Column(name = "coolplay_base_id")
	private Integer coolplayBaseId;//"酷玩基地ID"

	@Column(name = "label_id")
	private Integer labelId;//"标签ID"

	@Column(name = "c_time")
	private Date ctime;//"创建时间"

	//columns END

	@Transient
	private String labelName;//"标签内容"
		
	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return this.id;
	}
		
	public void setCoolplayBaseId(Integer coolplayBaseId) {
		this.coolplayBaseId = coolplayBaseId;
	}

	public Integer getCoolplayBaseId() {
		return this.coolplayBaseId;
	}
		
	public void setLabelId(Integer labelId) {
		this.labelId = labelId;
	}

	public Integer getLabelId() {
		return this.labelId;
	}
		
	public void setCtime(Date ctime) {
		this.ctime = ctime;
	}

	public Date getCtime() {
		return this.ctime;
	}

	public String getLabelName() {
		return labelName;
	}

	public void setLabelName(String labelName) {
		this.labelName = labelName;
	}
}

