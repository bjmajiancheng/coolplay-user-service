/*
 * 北京果敢时代科技有限公司
 * 北京市朝阳区望京SOHO T3 B座1607
 * 邮编：100022
 * 网址：www.davdian.com
 */

package com.coolplay.user.user.service;

import com.coolplay.user.user.model.CompanyModel;
import com.coolplay.user.user.model.CoolplayBaseModel;
import com.github.pagehelper.PageInfo;
import java.util.*;

import com.coolplay.user.common.baseservice.IBaseService;

/**
 * @author  davdian
 * @version 1.0
 * @since 1.0
 */

public interface ICoolplayBaseService extends IBaseService<CoolplayBaseModel> {

	public CoolplayBaseModel findById(Integer id);

	public List<CoolplayBaseModel> find(Map<String, Object> param);

	public PageInfo<CoolplayBaseModel> selectByFilterAndPage(CoolplayBaseModel coolplayBaseModel, int pageNum,
			int pageSize);

	public List<CoolplayBaseModel> selectByFilter(CoolplayBaseModel coolplayBaseModel);

	/**
	 * 获取基地信息
	 *
	 * @param ids
	 * @return
     */
	public Map<Integer, CoolplayBaseModel> findMapByIds(List<Integer> ids);

	public List<CoolplayBaseModel> findOptionDatas();

	public List<CoolplayBaseModel> findByLastUpdatetime(String updateTime);

}
