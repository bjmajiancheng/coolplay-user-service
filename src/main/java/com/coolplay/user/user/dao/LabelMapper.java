/*
 * 北京果敢时代科技有限公司
 * 北京市朝阳区望京SOHO T3 B座1607
 * 邮编：100022
 * 网址：www.davdian.com
 */

package com.coolplay.user.user.dao;
import com.coolplay.user.user.model.LabelModel;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.*;

/**
 * @author  davdian
 * @version 1.0
 * @since 1.0
 */

public interface LabelMapper extends Mapper<LabelModel> {

	public List<LabelModel> find(Map<String, Object> param);

	public LabelModel findById(@Param("id") Integer id);

	/**
	 * 获取用户可用的标签集合
	 *
	 * @param creatorUserId
	 * @return
	 */
	public List<LabelModel> findUserAvailableLabel(@Param("creatorUserId")Integer creatorUserId, @Param("catId")Integer catId);

	public List<LabelModel> findByPostIds(@Param("postIds")List<Integer> postIds);

	public List<LabelModel> findByCircleIds(@Param("circleIds")List<Integer> circleIds);

	public List<LabelModel> findByUserIds(@Param("userIds")List<Integer> userIds);

	public List<LabelModel> findByBaseIds(@Param("baseIds")List<Integer> baseIds);
}
