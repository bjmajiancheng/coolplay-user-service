/*
 * 北京果敢时代科技有限公司
 * 北京市朝阳区望京SOHO T3 B座1607
 * 邮编：100022
 * 网址：www.davdian.com
 */

package com.coolplay.user.user.service;

import com.coolplay.user.user.model.CircleModel;
import com.github.pagehelper.PageInfo;
import java.util.*;

import com.coolplay.user.common.baseservice.IBaseService;

/**
 * @author  davdian
 * @version 1.0
 * @since 1.0
 */

public interface ICircleService extends IBaseService<CircleModel> {

	public CircleModel findById(Integer id);

	public List<CircleModel> find(Map<String, Object> param);

	public PageInfo<CircleModel> selectByFilterAndPage(CircleModel circleModel, int pageNum, int pageSize);

	public List<CircleModel> selectByFilter(CircleModel circleModel);

	public Map<Integer, CircleModel> findMapByIds(List<Integer> circleIds);

	public List<CircleModel> findByIds(List<Integer> ids);

	/**
	 * 根据圈子ID获取管理员和成员ID集合
	 *
	 * @param circleIds
	 * @return
     */
	public Map<Integer, List<Integer>> getAdminOrMemberByIds(List<Integer> circleIds);

	/**
	 *
	 * 根据圈主ID获取圈子集合
	 *
	 * @param currentUserId
	 * @return
     */
	public List<Integer> findByUserId(Integer currentUserId);
}
