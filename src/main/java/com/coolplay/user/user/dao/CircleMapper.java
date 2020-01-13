/*
 * 北京果敢时代科技有限公司
 * 北京市朝阳区望京SOHO T3 B座1607
 * 邮编：100022
 * 网址：www.davdian.com
 */

package com.coolplay.user.user.dao;
import com.coolplay.user.user.model.CircleModel;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.*;

/**
 * @author  davdian
 * @version 1.0
 * @since 1.0
 */

public interface CircleMapper extends Mapper<CircleModel> {

	public List<CircleModel> find(Map<String, Object> param);

	public CircleModel findById(@Param("id") Integer id);

	public List<Integer> findByUserId(@Param("userId")Integer userId);

	public List<CircleModel> findByPostIds(@Param("postIds")List<Integer> postIds);

	public List<Integer> findCircleIdsByLabelName(@Param("labelName")String labelName);
}
