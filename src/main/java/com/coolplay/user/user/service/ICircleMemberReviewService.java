/*
 * 北京果敢时代科技有限公司
 * 北京市朝阳区望京SOHO T3 B座1607
 * 邮编：100022
 * 网址：www.davdian.com
 */

package com.coolplay.user.user.service;

import com.coolplay.user.common.baseservice.IBaseService;
import com.coolplay.user.user.model.CircleMemberReviewModel;
import com.github.pagehelper.PageInfo;
import java.util.*;

/**
 * @author  davdian
 * @version 1.0
 * @since 1.0
 */

public interface ICircleMemberReviewService extends IBaseService<CircleMemberReviewModel> {

	public CircleMemberReviewModel findById(Integer id);

	public List<CircleMemberReviewModel> find(Map<String, Object> param);

	public PageInfo<CircleMemberReviewModel> selectByFilterAndPage(CircleMemberReviewModel circleMemberReviewModel,
			int pageNum, int pageSize);

	public List<CircleMemberReviewModel> selectByFilter(CircleMemberReviewModel circleMemberReviewModel);

	/**
	 * 修改酷玩圈成员审核表
	 *
	 * @param circleId
	 * @param inviteUserId
	 * @param memberUserId
	 * @param reviewStatus
     * @return
     */
	public int updateByCircleIdAndInviteMemberUserId(Integer circleId, Integer inviteUserId, Integer memberUserId, Integer reviewStatus);
}
