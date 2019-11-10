/*
 * 北京果敢时代科技有限公司
 * 北京市朝阳区望京SOHO T3 B座1607
 * 邮编：100022
 * 网址：www.davdian.com
 */

package com.coolplay.user.user.service.impl;

import java.util.List;

import com.coolplay.user.common.baseservice.impl.BaseService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.coolplay.user.user.model.LabelModel;
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

@Service("labelService")
public class LabelServiceImpl extends BaseService<LabelModel> implements ILabelService{
	@Autowired
	private LabelMapper labelMapper;
	
	@Override
	public LabelModel findById(Integer id) {
		if(id == null) {
			return null;
		}
		return labelMapper.findById(id);
	}


	public List<LabelModel> find(Map<String, Object> param) {
		return labelMapper.find(param);
	}

	@Override
	public PageInfo<LabelModel> selectByFilterAndPage(LabelModel labelModel, int pageNum,
		int pageSize) {
		PageHelper.startPage(pageNum, pageSize, true, false, null);
		List<LabelModel> list = this.selectByFilter(labelModel);
		return new PageInfo<>(list);
	}

	@Override
	public List<LabelModel> selectByFilter(LabelModel labelModel) {
		Example example = new Example(LabelModel.class);
		Example.Criteria criteria = example.createCriteria();

		if(labelModel.getStatus() != null) {
			criteria.andEqualTo("status", labelModel.getStatus());
		}

		if(labelModel.getIsDel() != null) {
			criteria.andEqualTo("isDel", labelModel.getIsDel());
		}

		if(StringUtils.isNotEmpty(labelModel.getSortWithOutOrderBy())) {
			example.setOrderByClause(labelModel.getSortWithOutOrderBy());
		}
		return getMapper().selectByExample(example);
	}

	@Override
	public Map<Integer, List<LabelModel>> findMapByPostIds(List<Integer> postIds) {
		if(CollectionUtils.isEmpty(postIds)) {
			return Collections.emptyMap();
		}

		List<LabelModel> labelModels = labelMapper.findByPostIds(postIds);
		if(CollectionUtils.isEmpty(labelModels)) {
			return Collections.emptyMap();
		}

		Map<Integer, List<LabelModel>> labelMap = new HashMap<Integer, List<LabelModel>>(postIds.size());

		for(LabelModel labelModel : labelModels) {
			List<LabelModel> tmpLabelModels = labelMap.get(labelModel.getPostId());
			if(CollectionUtils.isEmpty(tmpLabelModels)) {
				tmpLabelModels = new ArrayList<LabelModel>();
			}
			tmpLabelModels.add(labelModel);

			labelMap.put(labelModel.getPostId(), tmpLabelModels);
		}

		return labelMap;
	}


	@Override
	public Map<Integer, List<LabelModel>> findMapByCircleIds(List<Integer> circleIds) {
		if(CollectionUtils.isEmpty(circleIds)) {
			return Collections.emptyMap();
		}

		List<LabelModel> labelModels = labelMapper.findByCircleIds(circleIds);
		if(CollectionUtils.isEmpty(labelModels)) {
			return Collections.emptyMap();
		}

		Map<Integer, List<LabelModel>> labelMap = new HashMap<Integer, List<LabelModel>>();

		for(LabelModel labelModel : labelModels) {
			List<LabelModel> tmpLabelModels = labelMap.get(labelModel.getCircleId());
			if(CollectionUtils.isEmpty(tmpLabelModels)) {
				tmpLabelModels = new ArrayList<LabelModel>();
			}
			tmpLabelModels.add(labelModel);

			labelMap.put(labelModel.getCircleId(), tmpLabelModels);
		}

		return labelMap;
	}

	/**
	 * 根据用户获取标签信息
	 *
	 * @param userIds
	 * @return
	 */
	public Map<Integer, List<LabelModel>> findMapByUserIds(List<Integer> userIds) {
		if(CollectionUtils.isEmpty(userIds)) {
			return Collections.emptyMap();
		}

		List<LabelModel> labelModels = labelMapper.findByUserIds(userIds);
		if(CollectionUtils.isEmpty(labelModels)) {
			return Collections.emptyMap();
		}

		Map<Integer, List<LabelModel>> labelMap = new HashMap<Integer, List<LabelModel>>();

		for(LabelModel labelModel : labelModels) {
			List<LabelModel> tmpLabelModels = labelMap.get(labelModel.getUserId());
			if(CollectionUtils.isEmpty(tmpLabelModels)) {
				tmpLabelModels = new ArrayList<LabelModel>();
			}
			tmpLabelModels.add(labelModel);

			labelMap.put(labelModel.getUserId(), tmpLabelModels);
		}

		return labelMap;
	}

	/**
	 * 根据基地获取标签信息
	 *
	 * @param baseIds
	 * @return
	 */
	public Map<Integer, List<LabelModel>> findMapByBaseIds(List<Integer> baseIds) {
		if(CollectionUtils.isEmpty(baseIds)) {
			return Collections.emptyMap();
		}

		List<LabelModel> labelModels = labelMapper.findByBaseIds(baseIds);
		if(CollectionUtils.isEmpty(labelModels)) {
			return Collections.emptyMap();
		}

		Map<Integer, List<LabelModel>> labelMap = new HashMap<Integer, List<LabelModel>>();

		for(LabelModel labelModel : labelModels) {
			List<LabelModel> tmpLabelModels = labelMap.get(labelModel.getBaseId());
			if(CollectionUtils.isEmpty(tmpLabelModels)) {
				tmpLabelModels = new ArrayList<LabelModel>();
			}
			tmpLabelModels.add(labelModel);

			labelMap.put(labelModel.getBaseId(), tmpLabelModels);
		}

		return labelMap;
	}
}
