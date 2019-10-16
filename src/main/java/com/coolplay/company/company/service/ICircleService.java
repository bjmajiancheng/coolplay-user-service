/*
 * 北京果敢时代科技有限公司
 * 北京市朝阳区望京SOHO T3 B座1607
 * 邮编：100022
 * 网址：www.davdian.com
 */

package com.coolplay.company.company.service;

import com.coolplay.company.company.model.CircleModel;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import java.util.*;
import com.coolplay.company.company.dao.*;
import com.coolplay.company.company.service.*;
import com.coolplay.company.common.baseservice.IBaseService;

/**
 * @author  davdian
 * @version 1.0
 * @since 1.0
 */

public interface ICircleService extends IBaseService<CircleModel> {

	public CircleModel findById(Integer id);

	public List<CircleModel> find(Map<String, Object> param);

	public PageInfo<CircleModel> selectByFilterAndPage(CircleModel circleModel, int pageNum, int pageSize);

	public List<CircleModel> selectByFilter(CircleModel circleModel);

	public Map<Integer, CircleModel> findMapByIds(List<Integer> circleIds);
}
