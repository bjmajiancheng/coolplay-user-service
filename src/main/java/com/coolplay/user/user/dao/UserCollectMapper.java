/*
 * 北京果敢时代科技有限公司
 * 北京市朝阳区望京SOHO T3 B座1607
 * 邮编：100022
 * 网址：www.davdian.com
 */

package com.coolplay.user.user.dao;
import com.coolplay.user.user.model.UserCollectModel;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.*;
import com.coolplay.user.user.dao.*;
import com.coolplay.user.user.service.*;

/**
 * @author  davdian
 * @version 1.0
 * @since 1.0
 */

public interface UserCollectMapper extends Mapper<UserCollectModel> {

	public List<UserCollectModel> find(Map<String, Object> param);

	public UserCollectModel findById(@Param("id") Integer id);

	public List<Integer> findPostIdsByUserIdAndPostIds(@Param("userId")Integer userId, @Param("postIds")List<Integer> postIds);

	/**
	 * 根据用户ID和圈子集合查看 用户收藏的圈子
	 *
	 * @param userId
	 * @param circleIds
	 * @return
	 */
	public List<Integer> findCircleIdsByUserIdAndCircleIds(@Param("userId")Integer userId, @Param("circleIds")List<Integer> circleIds);

	/**
	 * 根据用户ID和俱乐部集合查看 用户收藏的俱乐部
	 *
	 * @param userId
	 * @param companyIds
	 * @return
	 */
	public List<Integer> findCompanyIdsByUserIdAndCompanyIds(@Param("userId")Integer userId, @Param("companyIds")List<Integer> companyIds);

	/**
	 * 根据用户ID和基地集合查看 用户收藏的基地
	 *
	 * @param userId
	 * @param baseIds
	 * @return
	 */
	public List<Integer> findBaseIdsByUserIdAndBaseIds(@Param("userId")Integer userId, @Param("baseIds")List<Integer> baseIds);


	public Integer findCntByCollectTypeAndCollectTypeId(@Param("collectType")Integer collectType, @Param("collectTypeId")Integer collectTypeId);

	/**
	 * 根据用户ID和收藏类型信息删除用户收藏信息
	 *
	 * @param userId
	 * @param collectType
	 * @param collectTypeId
	 * @return
	 */
	public Integer delByUserIdAndCollectTypeInfo(@Param("userId")Integer userId, @Param("collectType")Integer collectType, @Param("collectTypeId")Integer collectTypeId);

	/**
	 * 添加用户收藏信息
	 *
	 * @param userCollectModel
	 * @return
     */
	public Integer insertIgnore(UserCollectModel userCollectModel);
}
