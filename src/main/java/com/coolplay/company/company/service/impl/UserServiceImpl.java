/*
 * 北京果敢时代科技有限公司
 * 北京市朝阳区望京SOHO T3 B座1607
 * 邮编：100022
 * 网址：www.davdian.com
 */

package com.coolplay.company.company.service.impl;

import java.util.List;

import com.coolplay.company.common.baseservice.impl.BaseService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.coolplay.company.company.model.UserModel;
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

@Service("appUserService")
public class UserServiceImpl extends BaseService<UserModel> implements IUserService{
	@Autowired
	private AppUserMapper appUserMapper;
	
	@Override
	public UserModel findById(Integer id) {
		if(id == null) {
			return null;
		}
		return appUserMapper.findById(id);
	}


	public List<UserModel> find(Map<String, Object> param) {
		return appUserMapper.find(param);
	}

	@Override
	public PageInfo<UserModel> selectByFilterAndPage(UserModel userModel, int pageNum,
		int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<UserModel> list = this.selectByFilter(userModel);
		return new PageInfo<>(list);
	}

	@Override
	public List<UserModel> selectByFilter(UserModel userModel) {
		Example example = new Example(UserModel.class);
		Example.Criteria criteria = example.createCriteria();

		if(StringUtils.isNotEmpty(userModel.getSortWithOutOrderBy())) {
			example.setOrderByClause(userModel.getSortWithOutOrderBy());
		}
		return getMapper().selectByExample(example);
	}

	@Override
	public Map<Integer, UserModel> findMapByUserIds(List<Integer> userIds) {
		if(CollectionUtils.isEmpty(userIds)) {
			return Collections.emptyMap();
		}

		List<UserModel> userModels = this.find(Collections.singletonMap("userIds", userIds));
		Map<Integer, UserModel> userModelMap = new HashMap<Integer, UserModel>();
		if(CollectionUtils.isNotEmpty(userModels)) {
			for(UserModel userModel : userModels) {
				userModelMap.put(userModel.getId(), userModel);
			}
		}

		return userModelMap;
	}
}
