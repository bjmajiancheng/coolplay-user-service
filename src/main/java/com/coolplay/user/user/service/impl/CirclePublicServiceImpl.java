/*
 * 北京果敢时代科技有限公司
 * 北京市朝阳区望京SOHO T3 B座1607
 * 邮编：100022
 * 网址：www.davdian.com
 */

package com.coolplay.user.user.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.coolplay.user.user.model.CirclePublicModel;
import tk.mybatis.mapper.entity.Example;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;

import java.util.*;
import com.coolplay.user.user.dao.*;
import com.coolplay.user.user.service.*;
import com.coolplay.user.common.baseservice.impl.BaseService;

/**
 * @author  davdian
 * @version 1.0
 * @since 1.0
 */

@Service("circlePublicService")
public class CirclePublicServiceImpl extends BaseService<CirclePublicModel> implements ICirclePublicService{
	@Autowired
	private CirclePublicMapper circlePublicMapper;
	
	@Override
	public CirclePublicModel findById(Integer id) {
		if(id == null) {
			return null;
		}
		return circlePublicMapper.findById(id);
	}


	public List<CirclePublicModel> find(Map<String, Object> param) {
		return circlePublicMapper.find(param);
	}

	@Override
	public PageInfo<CirclePublicModel> selectByFilterAndPage(CirclePublicModel circlePublicModel, int pageNum,
		int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<CirclePublicModel> list = this.selectByFilter(circlePublicModel);
		return new PageInfo<>(list);
	}

	@Override
	public List<CirclePublicModel> selectByFilter(CirclePublicModel circlePublicModel) {
		Example example = new Example(CirclePublicModel.class);
		Example.Criteria criteria = example.createCriteria();

		if(circlePublicModel.getCircleId() != null) {
			criteria.andEqualTo("circleId", circlePublicModel.getCircleId());
		}

		if(StringUtils.isNotEmpty(circlePublicModel.getSortWithOutOrderBy())) {
			example.setOrderByClause(circlePublicModel.getSortWithOutOrderBy());
		}
		return getMapper().selectByExample(example);
	}


	@Override
	public CirclePublicModel findLastPublicByCircleId(Integer circleId) {
		return circlePublicMapper.findLastPublicByCircleId(circleId);
	}
}
