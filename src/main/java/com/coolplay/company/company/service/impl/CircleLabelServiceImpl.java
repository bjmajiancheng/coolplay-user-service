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
import com.coolplay.company.company.model.CircleLabelModel;
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

@Service("circleLabelService")
public class CircleLabelServiceImpl extends BaseService<CircleLabelModel> implements ICircleLabelService{
	@Autowired
	private CircleLabelMapper circleLabelMapper;
	
	@Override
	public CircleLabelModel findById(Integer id) {
		if(id == null) {
			return null;
		}
		return circleLabelMapper.findById(id);
	}


	public List<CircleLabelModel> find(Map<String, Object> param) {
		return circleLabelMapper.find(param);
	}

	@Override
	public PageInfo<CircleLabelModel> selectByFilterAndPage(CircleLabelModel circleLabelModel, int pageNum,
		int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<CircleLabelModel> list = this.selectByFilter(circleLabelModel);
		return new PageInfo<>(list);
	}

	@Override
	public List<CircleLabelModel> selectByFilter(CircleLabelModel circleLabelModel) {
		Example example = new Example(CircleLabelModel.class);
		Example.Criteria criteria = example.createCriteria();

		if(StringUtils.isNotEmpty(circleLabelModel.getSortWithOutOrderBy())) {
			example.setOrderByClause(circleLabelModel.getSortWithOutOrderBy());
		}
		return getMapper().selectByExample(example);
	}

	public Map<Integer, List<CircleLabelModel>> findMapByCircleIds(List<Integer> circleIds) {
		if(CollectionUtils.isEmpty(circleIds)) {
			return Collections.emptyMap();
		}

		List<CircleLabelModel> circleLabelModels = circleLabelMapper.findFullInfoByCircleIds(circleIds);
		if(CollectionUtils.isEmpty(circleLabelModels)) {
			return Collections.emptyMap();
		}

		Map<Integer, List<CircleLabelModel>> circleLabelMap = new HashMap<Integer, List<CircleLabelModel>>();
		for(CircleLabelModel circleLabelModel : circleLabelModels) {
			List<CircleLabelModel> tmpCircleLabels = circleLabelMap.get(circleLabelModel.getCircleId());
			if(CollectionUtils.isEmpty(tmpCircleLabels)) {
				tmpCircleLabels = new ArrayList<CircleLabelModel>();
			}
			tmpCircleLabels.add(circleLabelModel);

			circleLabelMap.put(circleLabelModel.getCircleId(), tmpCircleLabels);
		}

		return circleLabelMap;
	}
}
