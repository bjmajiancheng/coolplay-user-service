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
import com.coolplay.user.user.model.PostModel;
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

@Service("postService")
public class PostServiceImpl extends BaseService<PostModel> implements IPostService{
	@Autowired
	private PostMapper postMapper;
	
	@Override
	public PostModel findById(Integer id) {
		if(id == null) {
			return null;
		}
		return postMapper.findById(id);
	}


	public List<PostModel> find(Map<String, Object> param) {
		return postMapper.find(param);
	}

	@Override
	public PageInfo<PostModel> selectByFilterAndPage(PostModel postModel, int pageNum,
		int pageSize) {
		PageHelper.startPage(pageNum, pageSize, true, false, null);
		List<PostModel> list = this.selectByFilter(postModel);
		return new PageInfo<>(list);
	}

	@Override
	public List<PostModel> selectByFilter(PostModel postModel) {
		Example example = new Example(PostModel.class);
		Example.Criteria criteria = example.createCriteria();

		if(StringUtils.isNotEmpty(postModel.getSearchKeyword())) {
			criteria.andLike("postContent", "%"+ postModel.getSearchKeyword() +"%");
		}

		if(postModel.getUserId() != null) {
			criteria.andEqualTo("userId", postModel.getUserId());
		}

		criteria.andEqualTo("isDel", 0);

		if(StringUtils.isNotEmpty(postModel.getSortWithOutOrderBy())) {
			example.setOrderByClause(postModel.getSortWithOutOrderBy());
		}
		return getMapper().selectByExample(example);
	}


	@Override
	public int columnPlusNumber(Integer id, String columnName, Integer number) {
		return postMapper.columnPlusNumber(id, columnName, number);
	}

	/**
	 * 根据酷玩圈ID获取帖子集合
	 *
	 * @param circleId
	 * @return
	 */
	public List<PostModel> findByCircleId(Integer circleId) {
		return postMapper.findByCircleId(circleId);
	}

	/**
	 * 根据帖子集合获取帖子map信息
	 *
	 * @param ids
	 * @return
	 */
	public Map<Integer, PostModel> findMapByIds(List<Integer> ids) {
		if(CollectionUtils.isEmpty(ids)) {
			return Collections.emptyMap();
		}
		List<PostModel> postModels = postMapper.findByIds(ids);

		Map<Integer, PostModel> postMap = new HashMap<Integer, PostModel>();
		if(CollectionUtils.isEmpty(postModels)) {
			return Collections.emptyMap();
		}

		for(PostModel postModel : postModels) {
			postMap.put(postModel.getId(), postModel);
		}

		return postMap;
	}
}
