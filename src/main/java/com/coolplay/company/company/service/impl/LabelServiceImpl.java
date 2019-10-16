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
import com.coolplay.company.company.model.LabelModel;
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

@Service("labelService")
public class LabelServiceImpl extends BaseService<LabelModel> implements ILabelService{
	@Autowired
	private LabelMapper labelMapper;
	
	@Override
	public LabelModel findById(Integer id) {
		if(id == null) {
			return null;
		}
		return labelMapper.findById(id);
	}


	public List<LabelModel> find(Map<String, Object> param) {
		return labelMapper.find(param);
	}

	@Override
	public PageInfo<LabelModel> selectByFilterAndPage(LabelModel labelModel, int pageNum,
		int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<LabelModel> list = this.selectByFilter(labelModel);
		return new PageInfo<>(list);
	}

	@Override
	public List<LabelModel> selectByFilter(LabelModel labelModel) {
		Example example = new Example(LabelModel.class);
		Example.Criteria criteria = example.createCriteria();

		if(StringUtils.isNotEmpty(labelModel.getSortWithOutOrderBy())) {
			example.setOrderByClause(labelModel.getSortWithOutOrderBy());
		}
		return getMapper().selectByExample(example);
	}
}
