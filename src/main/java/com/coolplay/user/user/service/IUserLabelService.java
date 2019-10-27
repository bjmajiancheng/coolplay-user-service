/*
 * 北京果敢时代科技有限公司
 * 北京市朝阳区望京SOHO T3 B座1607
 * 邮编：100022
 * 网址：www.davdian.com
 */

package com.coolplay.user.user.service;

import com.coolplay.user.common.baseservice.IBaseService;
import com.coolplay.user.user.model.UserLabelModel;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import java.util.*;
import com.coolplay.user.user.dao.*;
import com.coolplay.user.user.service.*;

/**
 * @author  davdian
 * @version 1.0
 * @since 1.0
 */

public interface IUserLabelService extends IBaseService<UserLabelModel> {

	public UserLabelModel findById(Integer id);

	public List<UserLabelModel> find(Map<String, Object> param);

	public PageInfo<UserLabelModel> selectByFilterAndPage(UserLabelModel userLabelModel, int pageNum, int pageSize);

	public List<UserLabelModel> selectByFilter(UserLabelModel userLabelModel);

	/**
	 * 根据用户ID删除用户信息
	 *
	 * @param userId
	 * @return
     */
	public int delByUserId(Integer userId);

}
