/*
 * 北京果敢时代科技有限公司
 * 北京市朝阳区望京SOHO T3 B座1607
 * 邮编：100022
 * 网址：www.davdian.com
 */

package com.coolplay.user.user.dao;
import com.coolplay.user.user.model.CircleMemberModel;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.*;

/**
 * @author  davdian
 * @version 1.0
 * @since 1.0
 */

public interface CircleMemberMapper extends Mapper<CircleMemberModel> {

	public List<CircleMemberModel> find(Map<String, Object> param);

	public CircleMemberModel findById(@Param("id") Integer id);

	public List<Integer> findByMemberUserId(@Param("memberUserId")Integer memberUserId);

	public List<CircleMemberModel> findByCircleIds(@Param("circleIds")List<Integer> circleIds);

	public List<Integer> findCircleIdsByUserIdAndCircleIds(@Param("currUserId")Integer currUserId, @Param("circleIds")List<Integer> circleIds);

	public List<Map<Integer, Integer>> findReviewMemberCntByCircleIds(@Param("circleIds")List<Integer> circleIds);
}
