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

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.coolplay.company.company.model.CirclePostModel;
import tk.mybatis.mapper.entity.Example;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;

import java.util.*;
import com.coolplay.company.company.dao.*;
import com.coolplay.company.company.service.*;
import com.coolplay.company.common.baseservice.impl.BaseService;

/**
 * @author  davdian
 * @version 1.0
 * @since 1.0
 */

@Service("circlePostService")
public class CirclePostServiceImpl extends BaseService<CirclePostModel> implements ICirclePostService{
	@Autowired
	private CirclePostMapper circlePostMapper;
	
	@Override
	public CirclePostModel findById(Integer id) {
		if(id == null) {
			return null;
		}
		return circlePostMapper.findById(id);
	}


	public List<CirclePostModel> find(Map<String, Object> param) {
		return circlePostMapper.find(param);
	}

	@Override
	public PageInfo<CirclePostModel> selectByFilterAndPage(CirclePostModel circlePostModel, int pageNum,
		int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<CirclePostModel> list = this.selectByFilter(circlePostModel);
		return new PageInfo<>(list);
	}

	@Override
	public List<CirclePostModel> selectByFilter(CirclePostModel circlePostModel) {
		Example example = new Example(CirclePostModel.class);
		Example.Criteria criteria = example.createCriteria();

		if(StringUtils.isNotEmpty(circlePostModel.getSortWithOutOrderBy())) {
			example.setOrderByClause(circlePostModel.getSortWithOutOrderBy());
		}
		return getMapper().selectByExample(example);
	}
}
