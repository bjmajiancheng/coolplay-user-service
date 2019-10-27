/*
 * 北京果敢时代科技有限公司
 * 北京市朝阳区望京SOHO T3 B座1607
 * 邮编：100022
 * 网址：www.davdian.com
 */

package com.coolplay.user.user.dao;
import com.coolplay.user.user.model.UserFansModel;
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

public interface UserFansMapper extends Mapper<UserFansModel> {

	public List<UserFansModel> find(Map<String, Object> param);

	public UserFansModel findById(@Param("id") Integer id);


	/**
	 * 获取粉丝人数
	 *
	 * @param userId
	 * @return
	 */
	public int findCntByUserId(@Param("userId") Integer userId);

	/**
	 * 获取关注人数
	 *
	 * @param fansUserId
	 * @return
	 */
	public int findCntByFansUserId(@Param("fansUserId") Integer fansUserId);

	/**
	 * 根据用户ID和粉丝ID删除关系
	 *
	 * @param userId
	 * @param fansUserId
	 * @return
	 */
	public int delByUserIdAndFansUserId(@Param("userId")Integer userId, @Param("fansUserId")Integer fansUserId);

}
