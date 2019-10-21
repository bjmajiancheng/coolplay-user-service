/*
 * 北京果敢时代科技有限公司
 * 北京市朝阳区望京SOHO T3 B座1607
 * 邮编：100022
 * 网址：www.davdian.com
 */

package com.coolplay.user.user.service;

import com.coolplay.user.common.baseservice.IBaseService;
import com.coolplay.user.user.model.CircleAdminModel;
import com.github.pagehelper.PageInfo;
import java.util.*;

/**
 * @author  davdian
 * @version 1.0
 * @since 1.0
 */

public interface ICircleAdminService extends IBaseService<CircleAdminModel> {

	public CircleAdminModel findById(Integer id);

	public List<CircleAdminModel> find(Map<String, Object> param);

	public PageInfo<CircleAdminModel> selectByFilterAndPage(CircleAdminModel circleAdminModel, int pageNum,
			int pageSize);

	public List<CircleAdminModel> selectByFilter(CircleAdminModel circleAdminModel);

	public List<Integer> findByAdminUserId(Integer adminUserId);

}
