/*
 * 北京果敢时代科技有限公司
 * 北京市朝阳区望京SOHO T3 B座1607
 * 邮编：100022
 * 网址：www.davdian.com
 */

package com.coolplay.user.user.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.coolplay.user.common.baseservice.impl.BaseService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.coolplay.user.user.model.UserLabelModel;
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

@Service("userLabelService")
public class UserLabelServiceImpl extends BaseService<UserLabelModel> implements IUserLabelService{
	@Autowired
	private UserLabelMapper userLabelMapper;
	
	@Override
	public UserLabelModel findById(Integer id) {
		if(id == null) {
			return null;
		}
		return userLabelMapper.findById(id);
	}


	public List<UserLabelModel> find(Map<String, Object> param) {
		return userLabelMapper.find(param);
	}

	@Override
	public PageInfo<UserLabelModel> selectByFilterAndPage(UserLabelModel userLabelModel, int pageNum,
		int pageSize) {
		PageHelper.startPage(pageNum, pageSize, true, false, null);
		List<UserLabelModel> list = this.selectByFilter(userLabelModel);
		return new PageInfo<>(list);
	}

	@Override
	public List<UserLabelModel> selectByFilter(UserLabelModel userLabelModel) {
		Example example = new Example(UserLabelModel.class);
		Example.Criteria criteria = example.createCriteria();

		if(StringUtils.isNotEmpty(userLabelModel.getSortWithOutOrderBy())) {
			example.setOrderByClause(userLabelModel.getSortWithOutOrderBy());
		}
		return getMapper().selectByExample(example);
	}

	/**
	 * 根据用户ID删除用户信息
	 *
	 * @param userId
	 * @return
	 */
	public int delByUserId(Integer userId) {
		if(userId == null) {
			return 0;
		}

		return userLabelMapper.delByUserId(userId);
	}
}
