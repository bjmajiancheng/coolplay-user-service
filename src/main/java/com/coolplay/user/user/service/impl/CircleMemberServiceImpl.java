/*
 * 北京果敢时代科技有限公司
 * 北京市朝阳区望京SOHO T3 B座1607
 * 邮编：100022
 * 网址：www.davdian.com
 */

package com.coolplay.user.user.service.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.coolplay.user.user.model.CircleMemberModel;
import tk.mybatis.mapper.entity.Example;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;

import java.util.*;
import com.coolplay.user.user.dao.*;
import com.coolplay.user.user.service.*;
import com.coolplay.user.common.baseservice.impl.BaseService;

/**
 * @author  davdian
 * @version 1.0
 * @since 1.0
 */

@Service("circleMemberService")
public class CircleMemberServiceImpl extends BaseService<CircleMemberModel> implements ICircleMemberService{
	@Autowired
	private CircleMemberMapper circleMemberMapper;
	
	@Override
	public CircleMemberModel findById(Integer id) {
		if(id == null) {
			return null;
		}
		return circleMemberMapper.findById(id);
	}


	public List<CircleMemberModel> find(Map<String, Object> param) {
		return circleMemberMapper.find(param);
	}

	@Override
	public PageInfo<CircleMemberModel> selectByFilterAndPage(CircleMemberModel circleMemberModel, int pageNum,
		int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<CircleMemberModel> list = this.selectByFilter(circleMemberModel);
		return new PageInfo<>(list);
	}

	@Override
	public List<CircleMemberModel> selectByFilter(CircleMemberModel circleMemberModel) {
		Example example = new Example(CircleMemberModel.class);
		Example.Criteria criteria = example.createCriteria();

		if(StringUtils.isNotEmpty(circleMemberModel.getSortWithOutOrderBy())) {
			example.setOrderByClause(circleMemberModel.getSortWithOutOrderBy());
		}
		return getMapper().selectByExample(example);
	}


	public List<Integer> findByMemberUserId(Integer memberUserId) {
		return circleMemberMapper.findByMemberUserId(memberUserId);
	}


	public Map<Integer, List<Integer>> findMapByCircleIds(List<Integer> circleIds) {
		if(CollectionUtils.isEmpty(circleIds)) {
			return Collections.emptyMap();
		}

		List<CircleMemberModel> circleMembers = circleMemberMapper.findByCircleIds(circleIds);

		if(CollectionUtils.isEmpty(circleMembers)) {
			return Collections.emptyMap();
		}

		Map<Integer, List<Integer>> circleMemberUserIdMap = new HashMap<Integer, List<Integer>>();
		for(CircleMemberModel circleMember : circleMembers) {
			List<Integer> memberUserIds = circleMemberUserIdMap.get(circleMember.getCircleId());

			if(CollectionUtils.isEmpty(memberUserIds)) {
				memberUserIds = new ArrayList<Integer>();
			}

			memberUserIds.add(circleMember.getMemberUserId());
			circleMemberUserIdMap.put(circleMember.getCircleId(), memberUserIds);
		}

		return circleMemberUserIdMap;
	}


	public List<Integer> findCircleIdsByUserIdAndCircleIds(Integer securityUserId, List<Integer> circleIds) {
		if(CollectionUtils.isEmpty(circleIds)) {
			return Collections.emptyList();
		}

		return circleMemberMapper.findCircleIdsByUserIdAndCircleIds(securityUserId, circleIds);
	}

	/**
	 * 查询圈子待审核成员数量
	 *
	 * @param circleIds
	 * @return
	 */
	public Map<Integer, Integer> findReviewMemberCntByCircleIds(List<Integer> circleIds) {
		if(CollectionUtils.isEmpty(circleIds)) {
			return Collections.emptyMap();
		}

		List<Map<Integer, Integer>> reviewMemberCntList = circleMemberMapper.findReviewMemberCntByCircleIds(circleIds);

		Map<Integer, Integer> reviewMemberCntMap = new HashMap<Integer, Integer>(reviewMemberCntList.size());
		if(CollectionUtils.isEmpty(reviewMemberCntList)) {
			for(Map<Integer, Integer> reviewMemberCnt : reviewMemberCntList) {
				reviewMemberCntMap.putAll(reviewMemberCnt);
			}
		}

		return reviewMemberCntMap;
	}
}
