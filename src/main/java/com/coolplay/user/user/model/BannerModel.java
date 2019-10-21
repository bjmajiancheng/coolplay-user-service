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
@Table(name = "d_banner")
public class BannerModel extends Sortable {
	private static final long serialVersionUID = 1L;

	//columns START
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;//"主键"

	@Column(name = "banner_title")
	private String bannerTitle;//"banner标题"

	@Column(name = "banner_img")
	private String bannerImg;//"banner图片"

	@Column(name = "banner_url")
	private String bannerUrl;//"banner链接"

	@Column(name = "sort")
	private Integer sort;//"排序"

	@Column(name = "read_cnt")
	private Integer readCnt;//"浏览次数"

	@Column(name = "system_user_id")
	private Integer systemUserId;//"添加人"

	@Column(name = "is_del")
	private Integer isDel;//"是否删除"

	@Column(name = "c_time")
	private Date ctime;//"创建时间"

	//columns END
		
	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return this.id;
	}
		
	public void setBannerTitle(String bannerTitle) {
		this.bannerTitle = bannerTitle;
	}

	public String getBannerTitle() {
		return this.bannerTitle;
	}
		
	public void setBannerImg(String bannerImg) {
		this.bannerImg = bannerImg;
	}

	public String getBannerImg() {
		return this.bannerImg;
	}
		
	public void setBannerUrl(String bannerUrl) {
		this.bannerUrl = bannerUrl;
	}

	public String getBannerUrl() {
		return this.bannerUrl;
	}
		
	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public Integer getSort() {
		return this.sort;
	}
		
	public void setReadCnt(Integer readCnt) {
		this.readCnt = readCnt;
	}

	public Integer getReadCnt() {
		return this.readCnt;
	}
		
	public void setSystemUserId(Integer systemUserId) {
		this.systemUserId = systemUserId;
	}

	public Integer getSystemUserId() {
		return this.systemUserId;
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

}

