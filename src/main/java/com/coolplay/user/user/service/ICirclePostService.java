/*
 * 北京果敢时代科技有限公司
 * 北京市朝阳区望京SOHO T3 B座1607
 * 邮编：100022
 * 网址：www.davdian.com
 */

package com.coolplay.user.user.service;

import com.coolplay.user.user.model.CirclePostModel;
import com.github.pagehelper.PageInfo;
import java.util.*;

import com.coolplay.user.common.baseservice.IBaseService;

/**
 * @author  davdian
 * @version 1.0
 * @since 1.0
 */

public interface ICirclePostService extends IBaseService<CirclePostModel> {

	public CirclePostModel findById(Integer id);

	public List<CirclePostModel> find(Map<String, Object> param);

	public PageInfo<CirclePostModel> selectByFilterAndPage(CirclePostModel circlePostModel, int pageNum, int pageSize);

	public List<CirclePostModel> selectByFilter(CirclePostModel circlePostModel);

	/**
	 * 根据酷玩圈ID获取帖子数量
	 *
	 * @param circleId
	 * @return
     */
	public int findPostCntByCircleId(Integer circleId);

	/**
	 * 根据帖子Id、圈主和是否置顶获取关联关系
	 *
	 * @param id
	 * @param currentUserId
	 * @param isTop
     * @return
     */
	public List<CirclePostModel> findByPostIdAndOwnerId(Integer id, Integer currentUserId, Integer isTop);

	public int updateTopByCircleIdsPostId(List<Integer> circleIds, Integer postId, Integer isTop);
}
