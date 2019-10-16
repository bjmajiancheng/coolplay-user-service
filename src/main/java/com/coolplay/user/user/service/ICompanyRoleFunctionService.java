/*
 * 北京果敢时代科技有限公司
 * 北京市朝阳区望京SOHO T3 B座1607
 * 邮编：100022
 * 网址：www.davdian.com
 */

package com.coolplay.user.user.service;

import com.coolplay.user.common.baseservice.IBaseService;
import com.coolplay.user.core.model.RoleFunctionModel;
import com.github.pagehelper.PageInfo;
import java.util.*;

/**
 * @author  davdian
 * @version 1.0
 * @since 1.0
 */

public interface ICompanyRoleFunctionService extends IBaseService<RoleFunctionModel> {

	public List<RoleFunctionModel> find(Map<String, Object> param);

	public PageInfo<RoleFunctionModel> selectByFilterAndPage(RoleFunctionModel roleFunctionModel,
			int pageNum, int pageSize);

	public List<RoleFunctionModel> selectByFilter(RoleFunctionModel roleFunctionModel);

	public int delByRoleId(int roleId);

}
