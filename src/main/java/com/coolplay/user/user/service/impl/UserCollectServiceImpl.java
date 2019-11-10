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
import com.coolplay.user.user.model.UserCollectModel;
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

@Service("userCollectService")
public class UserCollectServiceImpl extends BaseService<UserCollectModel> implements IUserCollectService{
	@Autowired
	private UserCollectMapper userCollectMapper;
	
	@Override
	public UserCollectModel findById(Integer id) {
		if(id == null) {
			return null;
		}
		return userCollectMapper.findById(id);
	}


	public List<UserCollectModel> find(Map<String, Object> param) {
		return userCollectMapper.find(param);
	}

	@Override
	public PageInfo<UserCollectModel> selectByFilterAndPage(UserCollectModel userCollectModel, int pageNum,
		int pageSize) {
		PageHelper.startPage(pageNum, pageSize, true, false, null);
		List<UserCollectModel> list = this.selectByFilter(userCollectModel);
		return new PageInfo<>(list);
	}

	@Override
	public List<UserCollectModel> selectByFilter(UserCollectModel userCollectModel) {
		Example example = new Example(UserCollectModel.class);
		Example.Criteria criteria = example.createCriteria();

		if(userCollectModel.getCollectType() != null) {
			criteria.andEqualTo("collectType", userCollectModel.getCollectType());
		}

		if(userCollectModel.getCollectTypeId() != null) {
			criteria.andEqualTo("collectTypeId", userCollectModel.getCollectTypeId());
		}

		if(userCollectModel.getUserId() != null) {
			criteria.andEqualTo("userId", userCollectModel.getUserId());
		}

		if(userCollectModel.getIsDel() != null) {
			criteria.andEqualTo("isDel", userCollectModel.getIsDel());
		}

		if(StringUtils.isNotEmpty(userCollectModel.getSortWithOutOrderBy())) {
			example.setOrderByClause(userCollectModel.getSortWithOutOrderBy());
		}
		return getMapper().selectByExample(example);
	}

	public List<Integer> findPostIdsByUserIdAndPostIds(Integer userId, List<Integer> postIds) {
		if(CollectionUtils.isEmpty(postIds)) {
			return Collections.emptyList();
		}

		return userCollectMapper.findPostIdsByUserIdAndPostIds(userId, postIds);
	}

	/**
	 * 根据用户ID和圈子集合查看 用户收藏的圈子
	 *
	 * @param userId
	 * @param circleIds
	 * @return
	 */
	public List<Integer> findCircleIdsByUserIdAndCircleIds(Integer userId, List<Integer> circleIds) {
		if(CollectionUtils.isEmpty(circleIds)) {
			return Collections.emptyList();
		}

		return userCollectMapper.findCircleIdsByUserIdAndCircleIds(userId, circleIds);
	}

	/**
	 * 根据用户ID和俱乐部集合查看 用户收藏的俱乐部
	 *
	 * @param userId
	 * @param companyIds
	 * @return
	 */
	public List<Integer> findCompanyIdsByUserIdAndCompanyIds(Integer userId, List<Integer> companyIds) {
		if(CollectionUtils.isEmpty(companyIds)) {
			return Collections.emptyList();
		}

		return userCollectMapper.findCompanyIdsByUserIdAndCompanyIds(userId, companyIds);
	}

	/**
	 * 根据收藏类型和收藏类型业务ID 获取收藏数
	 *
	 * @param collectType
	 * @param collectTypeId
	 * @return
	 */
	public Integer findCntByCollectTypeAndCollectTypeId(Integer collectType, Integer collectTypeId) {
		if(collectType == null || collectTypeId == null) {
			return 0;
		}

		return userCollectMapper.findCntByCollectTypeAndCollectTypeId(collectType, collectTypeId);
	}

	/**
	 * 根据用户ID和收藏类型信息删除用户收藏信息
	 *
	 * @param userId
	 * @param collectType
	 * @param collectTypeId
	 * @return
	 */
	public Integer delByUserIdAndCollectTypeInfo(Integer userId, Integer collectType, Integer collectTypeId) {
		if(userId == null || collectType == null || collectTypeId == null) {
			return 0;
		}

		return userCollectMapper.delByUserIdAndCollectTypeInfo(userId, collectType, collectTypeId);
	}

	/**
	 * 插入用户收藏信息
	 *
	 * @param userCollectModel
	 * @return
	 */
	public int insertIgnore(UserCollectModel userCollectModel) {
		if(userCollectModel == null) {
			return 0;
		}

		return userCollectMapper.insertIgnore(userCollectModel);
	}
}
