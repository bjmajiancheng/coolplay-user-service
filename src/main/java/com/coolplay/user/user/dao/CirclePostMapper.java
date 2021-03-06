/*
 * 北京果敢时代科技有限公司
 * 北京市朝阳区望京SOHO T3 B座1607
 * 邮编：100022
 * 网址：www.davdian.com
 */

package com.coolplay.user.user.dao;
import com.coolplay.user.user.model.CirclePostModel;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.*;

/**
 * @author  davdian
 * @version 1.0
 * @since 1.0
 */

public interface CirclePostMapper extends Mapper<CirclePostModel> {

	public List<CirclePostModel> find(Map<String, Object> param);

	public CirclePostModel findById(@Param("id") Integer id);

	/**
	 * 根据酷玩圈ID获取帖子数量
	 *
	 * @param circleId
	 * @return
	 */
	public int findPostCntByCircleId(@Param("circleId")Integer circleId);

	public List<CirclePostModel> findByPostIdAndOwnerId(@Param("postId")Integer id, @Param("userId")Integer currentUserId, @Param("isTop")Integer isTop);

	public int updateTopByCircleIdsPostId(@Param("circleIds")List<Integer> circleIds, @Param("postId")Integer postId, @Param("isTop")Integer isTop);
}
