/*
 * 北京果敢时代科技有限公司
 * 北京市朝阳区望京SOHO T3 B座1607
 * 邮编：100022
 * 网址：www.davdian.com
 */

package com.coolplay.company.company.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.coolplay.company.common.baseservice.impl.BaseService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.coolplay.company.company.model.CircleAdminModel;
import tk.mybatis.mapper.entity.Example;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;

import java.util.*;
import com.coolplay.company.company.dao.*;
import com.coolplay.company.company.service.*;

/**
 * @author  davdian
 * @version 1.0
 * @since 1.0
 */

@Service("circleAdminService")
public class CircleAdminServiceImpl extends BaseService<CircleAdminModel> implements ICircleAdminService{
	@Autowired
	private CircleAdminMapper circleAdminMapper;
	
	@Override
	public CircleAdminModel findById(Integer id) {
		if(id == null) {
			return null;
		}
		return circleAdminMapper.findById(id);
	}


	public List<CircleAdminModel> find(Map<String, Object> param) {
		return circleAdminMapper.find(param);
	}

	@Override
	public PageInfo<CircleAdminModel> selectByFilterAndPage(CircleAdminModel circleAdminModel, int pageNum,
		int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<CircleAdminModel> list = this.selectByFilter(circleAdminModel);
		return new PageInfo<>(list);
	}

	@Override
	public List<CircleAdminModel> selectByFilter(CircleAdminModel circleAdminModel) {
		Example example = new Example(CircleAdminModel.class);
		Example.Criteria criteria = example.createCriteria();

		if(StringUtils.isNotEmpty(circleAdminModel.getSortWithOutOrderBy())) {
			example.setOrderByClause(circleAdminModel.getSortWithOutOrderBy());
		}
		return getMapper().selectByExample(example);
	}
}
