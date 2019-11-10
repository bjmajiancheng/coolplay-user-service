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
import com.coolplay.user.user.model.PostLabelModel;
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

@Service("postLabelService")
public class PostLabelServiceImpl extends BaseService<PostLabelModel> implements IPostLabelService{
	@Autowired
	private PostLabelMapper postLabelMapper;
	
	@Override
	public PostLabelModel findById(Integer id) {
		if(id == null) {
			return null;
		}
		return postLabelMapper.findById(id);
	}


	public List<PostLabelModel> find(Map<String, Object> param) {
		return postLabelMapper.find(param);
	}

	@Override
	public PageInfo<PostLabelModel> selectByFilterAndPage(PostLabelModel postLabelModel, int pageNum,
		int pageSize) {
		PageHelper.startPage(pageNum, pageSize, true, false, null);
		List<PostLabelModel> list = this.selectByFilter(postLabelModel);
		return new PageInfo<>(list);
	}

	@Override
	public List<PostLabelModel> selectByFilter(PostLabelModel postLabelModel) {
		Example example = new Example(PostLabelModel.class);
		Example.Criteria criteria = example.createCriteria();

		if(StringUtils.isNotEmpty(postLabelModel.getSortWithOutOrderBy())) {
			example.setOrderByClause(postLabelModel.getSortWithOutOrderBy());
		}
		return getMapper().selectByExample(example);
	}
}
