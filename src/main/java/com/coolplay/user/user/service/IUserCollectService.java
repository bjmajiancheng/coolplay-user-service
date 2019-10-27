/*
 * 北京果敢时代科技有限公司
 * 北京市朝阳区望京SOHO T3 B座1607
 * 邮编：100022
 * 网址：www.davdian.com
 */

package com.coolplay.user.user.service;

import com.coolplay.user.common.baseservice.IBaseService;
import com.coolplay.user.user.model.UserCollectModel;
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

public interface IUserCollectService extends IBaseService<UserCollectModel> {

	public UserCollectModel findById(Integer id);

	public List<UserCollectModel> find(Map<String, Object> param);

	public PageInfo<UserCollectModel> selectByFilterAndPage(UserCollectModel userCollectModel, int pageNum,
			int pageSize);

	public List<UserCollectModel> selectByFilter(UserCollectModel userCollectModel);

	/**
	 * 根据用户ID和帖子集合查看 用户收藏的帖子
	 *
	 * @param userId
	 * @param postIds
     * @return
     */
	public List<Integer> findPostIdsByUserIdAndPostIds(Integer userId, List<Integer> postIds);

	/**
	 * 根据用户ID和圈子集合查看 用户收藏的圈子
	 *
	 * @param userId
	 * @param circleIds
	 * @return
	 */
	public List<Integer> findCircleIdsByUserIdAndCircleIds(Integer userId, List<Integer> circleIds);

	/**
	 * 根据用户ID和俱乐部集合查看 用户收藏的俱乐部
	 *
	 * @param userId
	 * @param companyIds
     * @return
     */
	public List<Integer> findCompanyIdsByUserIdAndCompanyIds(Integer userId, List<Integer> companyIds);

	/**
	 * 根据收藏类型和收藏类型业务ID 获取收藏数
	 *
	 * @param collectType
	 * @param collectTypeId
     * @return
     */
	public Integer findCntByCollectTypeAndCollectTypeId(Integer collectType, Integer collectTypeId);

	/**
	 * 根据用户ID和收藏类型信息删除用户收藏信息
	 *
	 * @param userId
	 * @param collectType
	 * @param collectTypeId
     * @return
     */
	public Integer delByUserIdAndCollectTypeInfo(Integer userId, Integer collectType, Integer collectTypeId);

	/**
	 * 插入用户收藏信息
	 *
	 * @param userCollectModel
	 * @return
     */
	public int insertIgnore(UserCollectModel userCollectModel);
}
