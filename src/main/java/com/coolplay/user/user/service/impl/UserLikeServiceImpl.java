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
import com.coolplay.user.user.model.UserLikeModel;
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

@Service("userLikeService")
public class UserLikeServiceImpl extends BaseService<UserLikeModel> implements IUserLikeService{
	@Autowired
	private UserLikeMapper userLikeMapper;
	
	@Override
	public UserLikeModel findById(Integer id) {
		if(id == null) {
			return null;
		}
		return userLikeMapper.findById(id);
	}


	public List<UserLikeModel> find(Map<String, Object> param) {
		return userLikeMapper.find(param);
	}

	@Override
	public PageInfo<UserLikeModel> selectByFilterAndPage(UserLikeModel userLikeModel, int pageNum,
		int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<UserLikeModel> list = this.selectByFilter(userLikeModel);
		return new PageInfo<>(list);
	}

	@Override
	public List<UserLikeModel> selectByFilter(UserLikeModel userLikeModel) {
		Example example = new Example(UserLikeModel.class);
		Example.Criteria criteria = example.createCriteria();

		if(StringUtils.isNotEmpty(userLikeModel.getSortWithOutOrderBy())) {
			example.setOrderByClause(userLikeModel.getSortWithOutOrderBy());
		}
		return getMapper().selectByExample(example);
	}

	public List<Integer> findPostIdsByUserIdAndPostIds(Integer userId, List<Integer> postIds) {
		if(CollectionUtils.isEmpty(postIds)) {
			return Collections.emptyList();
		}

		return userLikeMapper.findPostIdsByUserIdAndPostIds(userId, postIds);
	}
}
