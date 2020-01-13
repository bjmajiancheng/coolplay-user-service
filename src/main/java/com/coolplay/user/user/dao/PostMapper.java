/*
 * 北京果敢时代科技有限公司
 * 北京市朝阳区望京SOHO T3 B座1607
 * 邮编：100022
 * 网址：www.davdian.com
 */

package com.coolplay.user.user.dao;
import com.coolplay.user.user.model.PostModel;
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

public interface PostMapper extends Mapper<PostModel> {

	public List<PostModel> find(Map<String, Object> param);

	public PostModel findById(@Param("id") Integer id);

	public int columnPlusNumber(@Param("id")Integer id, @Param("columnName")String columnName, @Param("num")Integer number);

	/**
	 * 根据酷玩圈ID获取帖子集合
	 *
	 * @param circleId
	 * @return
	 */
	public List<PostModel> findByCircleId(@Param("circleId") Integer circleId);

	/**
	 * 根据id集合获取酷玩圈信息
	 *
	 * @param ids
	 * @return
     */
	public List<PostModel> findByIds(@Param("ids") List<Integer> ids);

	public List<Integer> findPostIdsByLabelName(@Param("labelName")String labelName);
}
