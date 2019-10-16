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
import java.util.List;
import java.util.Map;
import javax.persistence.*;

/**
 * @author  shawn
 * @version 1.0
 * @since 1.0
 */
@Table(name = "d_coolplay_base")
public class CoolplayBaseModel extends Sortable {
	private static final long serialVersionUID = 1L;

	//columns START
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;//"主键"

	@Column(name = "base_name")
	private String baseName;//"基地昵称"

	@Column(name = "base_full_name")
	private String baseFullName;//"基地全称"

	@Column(name = "base_desc")
	private String baseDesc;//"基地基本信息"

	@Column(name = "base_type")
	private String baseType;//"基地类型"

	@Column(name = "capacity_cnt")
	private Integer capacityCnt;//"容纳人数"

	@Column(name = "status")
	private Integer status;//"状态（0：默认，1：开放）"

	@Column(name = "pos_x")
	private BigDecimal posX;//"基地x坐标"

	@Column(name = "pos_y")
	private BigDecimal posY;//"基地y坐标"

	@Column(name = "company_id")
	private Integer companyId;//"所属俱乐部"

	@Column(name = "company_user_id")
	private Integer companyUserId;//"创建人"

	@Column(name = "departures_gps")
	private String departuresGps;//"起飞场GPS"

	@Column(name = "departures_height")
	private Integer departuresHeight;//"起飞场高度"

	@Column(name = "land_gps")
	private String landGps;//"降落场GPS"

	@Column(name = "land_height")
	private Integer landHeight;//"降落场高度"

	@Column(name = "fly_direction")
	private String flyDirection;//"可飞风向"

	@Column(name = "monitor_video_url")
	private String monitorVideoUrl;//"监控视频链接"

	@Column(name = "height")
	private Integer height;//"相对高度"

	@Column(name = "major_project")
	private String majorProject;//"主营项目"

	@Column(name = "open_time")
	private String openTime;//"开放时间"

	@Column(name = "contact_name")
	private String contactName;//"联系人"

	@Column(name = "contact_phone")
	private String contactPhone;//"联系人电话"

	@Column(name = "contact_email")
	private String contactEmail;//"联系人邮箱"

	@Column(name = "contact_address")
	private String contactAddress;//"联系人地址"

	@Column(name = "read_cnt")
	private Integer readCnt;//"浏览次数"

	@Column(name = "is_close")
	private Integer isClose;//"是否关闭（0：关闭，1：开放）"

	@Column(name = "is_del")
	private Integer isDel;//"是否删除（0：未删除，1：已删除）"

	@Column(name = "c_time")
	private Date ctime;//"创建时间"

	//columns END
	@Transient
	private String companyUserName;//"创建人名称"

	@Transient
	private List<Integer> labelIds;//"标签集合"

	@Transient
	private String labelName = "";//"标签名称"
		
	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return this.id;
	}
		
	public void setBaseName(String baseName) {
		this.baseName = baseName;
	}

	public String getBaseName() {
		return this.baseName;
	}
		
	public void setBaseFullName(String baseFullName) {
		this.baseFullName = baseFullName;
	}

	public String getBaseFullName() {
		return this.baseFullName;
	}
		
	public void setBaseDesc(String baseDesc) {
		this.baseDesc = baseDesc;
	}

	public String getBaseDesc() {
		return this.baseDesc;
	}
		
	public void setBaseType(String baseType) {
		this.baseType = baseType;
	}

	public String getBaseType() {
		return this.baseType;
	}
		
	public void setCapacityCnt(Integer capacityCnt) {
		this.capacityCnt = capacityCnt;
	}

	public Integer getCapacityCnt() {
		return this.capacityCnt;
	}
		
	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getStatus() {
		return this.status;
	}
		
	public void setPosX(BigDecimal posX) {
		this.posX = posX;
	}

	public BigDecimal getPosX() {
		return this.posX;
	}
		
	public void setPosY(BigDecimal posY) {
		this.posY = posY;
	}

	public BigDecimal getPosY() {
		return this.posY;
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
		
	public void setDeparturesGps(String departuresGps) {
		this.departuresGps = departuresGps;
	}

	public String getDeparturesGps() {
		return this.departuresGps;
	}
		
	public void setDeparturesHeight(Integer departuresHeight) {
		this.departuresHeight = departuresHeight;
	}

	public Integer getDeparturesHeight() {
		return this.departuresHeight;
	}
		
	public void setLandGps(String landGps) {
		this.landGps = landGps;
	}

	public String getLandGps() {
		return this.landGps;
	}
		
	public void setLandHeight(Integer landHeight) {
		this.landHeight = landHeight;
	}

	public Integer getLandHeight() {
		return this.landHeight;
	}
		
	public void setFlyDirection(String flyDirection) {
		this.flyDirection = flyDirection;
	}

	public String getFlyDirection() {
		return this.flyDirection;
	}
		
	public void setMonitorVideoUrl(String monitorVideoUrl) {
		this.monitorVideoUrl = monitorVideoUrl;
	}

	public String getMonitorVideoUrl() {
		return this.monitorVideoUrl;
	}
		
	public void setHeight(Integer height) {
		this.height = height;
	}

	public Integer getHeight() {
		return this.height;
	}
		
	public void setMajorProject(String majorProject) {
		this.majorProject = majorProject;
	}

	public String getMajorProject() {
		return this.majorProject;
	}
		
	public void setOpenTime(String openTime) {
		this.openTime = openTime;
	}

	public String getOpenTime() {
		return this.openTime;
	}
		
	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getContactName() {
		return this.contactName;
	}
		
	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	public String getContactPhone() {
		return this.contactPhone;
	}
		
	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}

	public String getContactEmail() {
		return this.contactEmail;
	}
		
	public void setContactAddress(String contactAddress) {
		this.contactAddress = contactAddress;
	}

	public String getContactAddress() {
		return this.contactAddress;
	}
		
	public void setReadCnt(Integer readCnt) {
		this.readCnt = readCnt;
	}

	public Integer getReadCnt() {
		return this.readCnt;
	}
		
	public void setIsClose(Integer isClose) {
		this.isClose = isClose;
	}

	public Integer getIsClose() {
		return this.isClose;
	}
		
	public void setIsDel(Integer isDel) {
		this.isDel = isDel;
	}

	public Integer getIsDel() {
		return this.isDel;
	}
		
	public void setCtime(Date ctime) {
		this.ctime = ctime;
	}

	public Date getCtime() {
		return this.ctime;
	}

	public String getCompanyUserName() {
		return companyUserName;
	}

	public void setCompanyUserName(String companyUserName) {
		this.companyUserName = companyUserName;
	}

	public List<Integer> getLabelIds() {
		return labelIds;
	}

	public void setLabelIds(List<Integer> labelIds) {
		this.labelIds = labelIds;
	}

	public String getLabelName() {
		return labelName;
	}

	public void setLabelName(String labelName) {
		this.labelName = labelName;
	}
}

