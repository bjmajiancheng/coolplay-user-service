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
import com.coolplay.user.user.model.UserFansModel;
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

@Service("userFansService")
public class UserFansServiceImpl extends BaseService<UserFansModel> implements IUserFansService{
	@Autowired
	private UserFansMapper userFansMapper;
	
	@Override
	public UserFansModel findById(Integer id) {
		if(id == null) {
			return null;
		}
		return userFansMapper.findById(id);
	}


	public List<UserFansModel> find(Map<String, Object> param) {
		return userFansMapper.find(param);
	}

	@Override
	public PageInfo<UserFansModel> selectByFilterAndPage(UserFansModel userFansModel, int pageNum,
		int pageSize) {
		PageHelper.startPage(pageNum, pageSize, true, false, null);
		List<UserFansModel> list = this.selectByFilter(userFansModel);
		return new PageInfo<>(list);
	}

	@Override
	public List<UserFansModel> selectByFilter(UserFansModel userFansModel) {
		Example example = new Example(UserFansModel.class);
		Example.Criteria criteria = example.createCriteria();

		if(userFansModel.getUserId() != null) {
			criteria.andEqualTo("userId", userFansModel.getUserId());
		}

		if(userFansModel.getFansUserId() != null) {
			criteria.andEqualTo("fansUserId", userFansModel.getFansUserId());
		}

		if(StringUtils.isNotEmpty(userFansModel.getSortWithOutOrderBy())) {
			example.setOrderByClause(userFansModel.getSortWithOutOrderBy());
		}
		return getMapper().selectByExample(example);
	}

	/**
	 * 获取粉丝人数
	 *
	 * @param userId
	 * @return
	 */
	public int findCntByUserId(Integer userId) {
		if(userId == null) {
			return 0;
		}

		return userFansMapper.findCntByUserId(userId);
	}

	/**
	 * 获取关注人数
	 *
	 * @param fansUserId
	 * @return
	 */
	public int findCntByFansUserId(Integer fansUserId) {
		if(fansUserId == null) {
			return 0;
		}

		return userFansMapper.findCntByFansUserId(fansUserId);
	}

	/**
	 * 获取关注用户信息map
	 *
	 * @param fansUserIds
	 * @return
	 */
	public Map<Integer, List<Integer>> getFollowMapByFansUserIds(List<Integer> fansUserIds) {
		if(CollectionUtils.isEmpty(fansUserIds)) {
			return Collections.emptyMap();
		}

		List<UserFansModel> userFansModels = userFansMapper.find(Collections.singletonMap("fansUserIds", fansUserIds));
		if(CollectionUtils.isEmpty(userFansModels)) {
			return Collections.emptyMap();
		}

		Map<Integer, List<Integer>> followUserMap = new HashMap<Integer, List<Integer>>();
		for(UserFansModel userFansModel : userFansModels) {
			List<Integer> followUsers = followUserMap.get(userFansModel.getFansUserId());
			if(CollectionUtils.isEmpty(followUsers)) {
				followUsers = new ArrayList<Integer>();
			}

			followUsers.add(userFansModel.getUserId());

			followUserMap.put(userFansModel.getFansUserId(), followUsers);
		}

		return followUserMap;
	}

	/**
	 * 根据用户ID和粉丝ID删除关系
	 *
	 * @param userId
	 * @param fansUserId
	 * @return
	 */
	public int delByUserIdAndFansUserId(Integer userId, Integer fansUserId) {
		if(userId == null || fansUserId == null) {
			return 0;
		}

		return userFansMapper.delByUserIdAndFansUserId(userId, fansUserId);
	}
}
