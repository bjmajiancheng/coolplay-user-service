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
import com.coolplay.company.company.model.CircleMemberModel;
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

@Service("circleMemberService")
public class CircleMemberServiceImpl extends BaseService<CircleMemberModel> implements ICircleMemberService{
	@Autowired
	private CircleMemberMapper circleMemberMapper;
	
	@Override
	public CircleMemberModel findById(Integer id) {
		if(id == null) {
			return null;
		}
		return circleMemberMapper.findById(id);
	}


	public List<CircleMemberModel> find(Map<String, Object> param) {
		return circleMemberMapper.find(param);
	}

	@Override
	public PageInfo<CircleMemberModel> selectByFilterAndPage(CircleMemberModel circleMemberModel, int pageNum,
		int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<CircleMemberModel> list = this.selectByFilter(circleMemberModel);
		return new PageInfo<>(list);
	}

	@Override
	public List<CircleMemberModel> selectByFilter(CircleMemberModel circleMemberModel) {
		Example example = new Example(CircleMemberModel.class);
		Example.Criteria criteria = example.createCriteria();

		if(StringUtils.isNotEmpty(circleMemberModel.getSortWithOutOrderBy())) {
			example.setOrderByClause(circleMemberModel.getSortWithOutOrderBy());
		}
		return getMapper().selectByExample(example);
	}
}
