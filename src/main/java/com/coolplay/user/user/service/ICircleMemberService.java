/*
 * 北京果敢时代科技有限公司
 * 北京市朝阳区望京SOHO T3 B座1607
 * 邮编：100022
 * 网址：www.davdian.com
 */

package com.coolplay.user.user.service;

import com.coolplay.user.user.model.CircleMemberModel;
import com.github.pagehelper.PageInfo;
import java.util.*;

import com.coolplay.user.common.baseservice.IBaseService;

/**
 * @author  davdian
 * @version 1.0
 * @since 1.0
 */

public interface ICircleMemberService extends IBaseService<CircleMemberModel> {

	public CircleMemberModel findById(Integer id);

	public List<CircleMemberModel> find(Map<String, Object> param);

	public PageInfo<CircleMemberModel> selectByFilterAndPage(CircleMemberModel circleMemberModel, int pageNum,
			int pageSize);

	public List<CircleMemberModel> selectByFilter(CircleMemberModel circleMemberModel);

	public List<Integer> findByMemberUserId(Integer memberUserId);

	public Map<Integer, List<Integer>> findMapByCircleIds(List<Integer> circleIds);

	/**
	 * 查询当前用户所属的圈子集合
	 *
	 * @param currUserId
	 * @param circleId
     * @return
     */
	public List<Integer> findCircleIdsByUserIdAndCircleIds(Integer currUserId, List<Integer> circleId);

	/**
	 * 查询圈子待审核成员数量
	 *
	 * @param circleIds
	 * @return
     */
	public Map<Integer, Integer> findReviewMemberCntByCircleIds(List<Integer> circleIds);

	/**
	 * 更新酷玩圈成员状态信息
	 *
	 * @param circleId
	 * @param memberUserId
	 * @param reviewStatus
	 * @param status
     * @return
     */
	public int updateByCircleIdMemberUserId(Integer circleId, Integer memberUserId, Integer reviewStatus, Integer status);

	public int delByCircleIdAndMemberUserId(Integer circleId, Integer memberUserId);
}
