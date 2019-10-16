/*
 * 北京果敢时代科技有限公司
 * 北京市朝阳区望京SOHO T3 B座1607
 * 邮编：100022
 * 网址：www.davdian.com
 */

package com.coolplay.user.user.service;

import com.coolplay.user.common.baseservice.IBaseService;
import com.coolplay.user.core.model.UserRoleModel;
import com.github.pagehelper.PageInfo;
import java.util.*;

/**
 * @author  davdian
 * @version 1.0
 * @since 1.0
 */

public interface ICompanyUserRoleService extends IBaseService<UserRoleModel> {

	public List<UserRoleModel> find(Map<String, Object> param);

	public PageInfo<UserRoleModel> selectByFilterAndPage(UserRoleModel userRoleModel, int pageNum,
			int pageSize);

	public List<UserRoleModel> selectByFilter(UserRoleModel userRoleModel);

	public int deleteByUserId(Integer userId);

}
