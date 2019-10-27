/*
 * 北京果敢时代科技有限公司
 * 北京市朝阳区望京SOHO T3 B座1607
 * 邮编：100022
 * 网址：www.davdian.com
 */

package com.coolplay.user.user.service;

import com.coolplay.user.common.baseservice.IBaseService;
import com.coolplay.user.user.model.LabelModel;
import com.github.pagehelper.PageInfo;
import java.util.*;

/**
 * @author  davdian
 * @version 1.0
 * @since 1.0
 */

public interface ILabelService extends IBaseService<LabelModel> {

	public LabelModel findById(Integer id);

	public List<LabelModel> find(Map<String, Object> param);

	public PageInfo<LabelModel> selectByFilterAndPage(LabelModel labelModel, int pageNum, int pageSize);

	public List<LabelModel> selectByFilter(LabelModel labelModel);

	/**
	 * 根据帖子获取标签信息
	 *
	 * @param postIds
	 * @return
     */
	public Map<Integer, List<LabelModel>> findMapByPostIds(List<Integer> postIds);

	/**
	 * 根据酷玩圈获取标签信息
	 *
	 * @param circleIds
	 * @return
     */
	public Map<Integer, List<LabelModel>> findMapByCircleIds(List<Integer> circleIds);

	/**
	 * 根据用户获取标签信息
	 *
	 * @param userIds
	 * @return
     */
	public Map<Integer, List<LabelModel>> findMapByUserIds(List<Integer> userIds);

	/**
	 * 根据基地获取标签信息
	 *
	 * @param baseIds
	 * @return
     */
	public Map<Integer, List<LabelModel>> findMapByBaseIds(List<Integer> baseIds);
}
