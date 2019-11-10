/*
 * 北京果敢时代科技有限公司
 * 北京市朝阳区望京SOHO T3 B座1607
 * 邮编：100022
 * 网址：www.davdian.com
 */

package com.coolplay.user.user.service.impl;

import java.util.List;

import com.coolplay.user.common.baseservice.impl.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.coolplay.user.user.model.VerifyCodeModel;
import tk.mybatis.mapper.entity.Example;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;

import java.util.*;
import com.coolplay.user.user.dao.*;
import com.coolplay.user.user.service.*;

/**
 * @author  davdian
 * @version 1.0
 * @since 1.0
 */

@Service("verifyCodeService")
public class VerifyCodeServiceImpl extends BaseService<VerifyCodeModel> implements IVerifyCodeService{
	@Autowired
	private VerifyCodeMapper verifyCodeMapper;
	
	@Override
	public VerifyCodeModel findById(Integer id) {
		if(id == null) {
			return null;
		}
		return verifyCodeMapper.findById(id);
	}


	public List<VerifyCodeModel> find(Map<String, Object> param) {
		return verifyCodeMapper.find(param);
	}

	@Override
	public PageInfo<VerifyCodeModel> selectByFilterAndPage(VerifyCodeModel verifyCodeModel, int pageNum,
		int pageSize) {
		PageHelper.startPage(pageNum, pageSize, true, false, null);
		List<VerifyCodeModel> list = this.selectByFilter(verifyCodeModel);
		return new PageInfo<>(list);
	}

	@Override
	public List<VerifyCodeModel> selectByFilter(VerifyCodeModel verifyCodeModel) {
		Example example = new Example(VerifyCodeModel.class);
		Example.Criteria criteria = example.createCriteria();

		if(StringUtils.isNotEmpty(verifyCodeModel.getSortWithOutOrderBy())) {
			example.setOrderByClause(verifyCodeModel.getSortWithOutOrderBy());
		}
		return getMapper().selectByExample(example);
	}
}
