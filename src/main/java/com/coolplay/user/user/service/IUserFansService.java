/*
 * 北京果敢时代科技有限公司
 * 北京市朝阳区望京SOHO T3 B座1607
 * 邮编：100022
 * 网址：www.davdian.com
 */

package com.coolplay.user.user.service;

import com.coolplay.user.common.baseservice.IBaseService;
import com.coolplay.user.user.model.UserFansModel;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import java.util.*;
import com.coolplay.user.user.dao.*;
import com.coolplay.user.user.service.*;

/**
 * @author  davdian
 * @version 1.0
 * @since 1.0
 */

public interface IUserFansService extends IBaseService<UserFansModel> {

	public UserFansModel findById(Integer id);

	public List<UserFansModel> find(Map<String, Object> param);

	public PageInfo<UserFansModel> selectByFilterAndPage(UserFansModel userFansModel, int pageNum, int pageSize);

	public List<UserFansModel> selectByFilter(UserFansModel userFansModel);

	/**
	 * 获取粉丝人数
	 *
	 * @param userId
	 * @return
     */
	public int findCntByUserId(Integer userId);

	/**
	 * 获取关注人数
	 *
	 * @param fansUserId
	 * @return
	 */
	public int findCntByFansUserId(Integer fansUserId);

	/**
	 * 获取关注用户信息map
	 *
	 * @param fansUserIds
	 * @return
     */
	public Map<Integer, List<Integer>> getFollowMapByFansUserIds(List<Integer> fansUserIds);

	/**
	 * 根据用户ID和粉丝ID删除关系
	 *
	 * @param userId
	 * @param fansUserId
     * @return
     */
	public int delByUserIdAndFansUserId(Integer userId, Integer fansUserId);

	public List<UserFansModel> findByUserIdAndFansUserId(Integer userId, Integer fansUserId);

	/**
	 * 根据粉丝用户ID获取关注列表
	 *
	 * @param fansUserId
	 * @return
     */
	public List<Integer> findByFansUserId(Integer fansUserId);
}
