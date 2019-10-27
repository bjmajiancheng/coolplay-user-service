/*
 * 北京果敢时代科技有限公司
 * 北京市朝阳区望京SOHO T3 B座1607
 * 邮编：100022
 * 网址：www.davdian.com
 */

package com.coolplay.user.user.dao;
import com.coolplay.user.user.model.CircleAdminModel;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.*;

/**
 * @author  davdian
 * @version 1.0
 * @since 1.0
 */

public interface CircleAdminMapper extends Mapper<CircleAdminModel> {

	public List<CircleAdminModel> find(Map<String, Object> param);

	public CircleAdminModel findById(@Param("id") Integer id);

	public List<Integer> findByAdminUserId(@Param("adminUserId")Integer adminUserId);

	/**
	 * 根据圈主删除圈子管理员
	 *
	 * @param circleId
	 * @return
	 */
	public int delByCircleId(@Param("circleId") Integer circleId);
}
