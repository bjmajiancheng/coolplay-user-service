/*
 * 北京果敢时代科技有限公司
 * 北京市朝阳区望京SOHO T3 B座1607
 * 邮编：100022
 * 网址：www.davdian.com
 */

package com.coolplay.user.user.dao;
import com.coolplay.user.user.model.CircleMemberReviewModel;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.*;

/**
 * @author  davdian
 * @version 1.0
 * @since 1.0
 */

public interface CircleMemberReviewMapper extends Mapper<CircleMemberReviewModel> {

	public List<CircleMemberReviewModel> find(Map<String, Object> param);

	public CircleMemberReviewModel findById(@Param("id") Integer id);

	/**
	 * 修改酷玩圈成员审核表
	 *
	 * @param circleId
	 * @param inviteUserId
	 * @param memberUserId
	 * @param reviewStatus
	 * @return
	 */
	public int updateByCircleIdAndInviteMemberUserId(@Param("circleId")Integer circleId, @Param("inviteUserId")Integer inviteUserId,
			@Param("memberUserId")Integer memberUserId, @Param("reviewStatus")Integer reviewStatus);
}
