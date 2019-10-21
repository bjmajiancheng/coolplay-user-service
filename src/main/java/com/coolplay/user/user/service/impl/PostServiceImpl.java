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
		PageHelper.startPage(pageNum, pageSize);
		List<PostModel> list = this.selectByFilter(postModel);
		return new PageInfo<>(list);
	}

	@Override
	public List<PostModel> selectByFilter(PostModel postModel) {
		Example example = new Example(PostModel.class);
		Example.Criteria criteria = example.createCriteria();

		if(StringUtils.isNotEmpty(postModel.getSearchKeyword())) {
			criteria.andLike("postTitle", "%"+ postModel.getSearchKeyword() +"%");
		}

		criteria.andEqualTo("idDel", 0);

		if(StringUtils.isNotEmpty(postModel.getSortWithOutOrderBy())) {
			example.setOrderByClause(postModel.getSortWithOutOrderBy());
		}
		return getMapper().selectByExample(example);
	}


	@Override
	public int columnPlusNumber(Integer id, String columnName, Integer number) {
		return postMapper.columnPlusNumber(id, columnName, number);
	}
}
