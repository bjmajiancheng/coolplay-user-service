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
import com.coolplay.company.company.model.CompanyCircleModel;
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

@Service("companyCircleService")
public class CompanyCircleServiceImpl extends BaseService<CompanyCircleModel> implements ICompanyCircleService{
	@Autowired
	private CompanyCircleMapper companyCircleMapper;
	
	@Override
	public CompanyCircleModel findById(Integer id) {
		if(id == null) {
			return null;
		}
		return companyCircleMapper.findById(id);
	}


	public List<CompanyCircleModel> find(Map<String, Object> param) {
		return companyCircleMapper.find(param);
	}

	@Override
	public PageInfo<CompanyCircleModel> selectByFilterAndPage(CompanyCircleModel companyCircleModel, int pageNum,
		int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<CompanyCircleModel> list = this.selectByFilter(companyCircleModel);
		return new PageInfo<>(list);
	}

	@Override
	public List<CompanyCircleModel> selectByFilter(CompanyCircleModel companyCircleModel) {
		Example example = new Example(CompanyCircleModel.class);
		Example.Criteria criteria = example.createCriteria();

		if(StringUtils.isNotEmpty(companyCircleModel.getApplicationStartTime())) {
			criteria.andGreaterThanOrEqualTo("applicationTime", companyCircleModel.getApplicationStartTime());
		}

		if(StringUtils.isNotEmpty(companyCircleModel.getApplicationEndTime())) {
			criteria.andLessThanOrEqualTo("applicationTime", companyCircleModel.getApplicationEndTime());
		}

		if(StringUtils.isNotEmpty(companyCircleModel.getCircleName())) {
			criteria.andLike("cricleName", "%" + companyCircleModel.getCircleName() +"%");
		}

		if(companyCircleModel.getReviewStatus() != null) {
			criteria.andEqualTo("reviewStatus", companyCircleModel.getReviewStatus());
		}

		if(companyCircleModel.getCompanyId() != null) {
			criteria.andEqualTo("companyId", companyCircleModel.getCompanyId());
		}

		if(StringUtils.isNotEmpty(companyCircleModel.getSortWithOutOrderBy())) {
			example.setOrderByClause(companyCircleModel.getSortWithOutOrderBy());
		}
		return getMapper().selectByExample(example);
	}
}
