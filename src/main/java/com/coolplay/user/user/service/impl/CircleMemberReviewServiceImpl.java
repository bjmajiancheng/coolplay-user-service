/*
 * 北京果敢时代科技有限公司
 * 北京市朝阳区望京SOHO T3 B座1607
 * 邮编：100022
 * 网址：www.davdian.com
 */

package com.coolplay.user.user.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.coolplay.user.user.model.CircleMemberReviewModel;
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

@Service("circleMemberReviewService")
public class CircleMemberReviewServiceImpl extends BaseService<CircleMemberReviewModel> implements ICircleMemberReviewService{
	@Autowired
	private CircleMemberReviewMapper circleMemberReviewMapper;
	
	@Override
	public CircleMemberReviewModel findById(Integer id) {
		if(id == null) {
			return null;
		}
		return circleMemberReviewMapper.findById(id);
	}


	public List<CircleMemberReviewModel> find(Map<String, Object> param) {
		return circleMemberReviewMapper.find(param);
	}

	@Override
	public PageInfo<CircleMemberReviewModel> selectByFilterAndPage(CircleMemberReviewModel circleMemberReviewModel, int pageNum,
		int pageSize) {
		PageHelper.startPage(pageNum, pageSize, true, false, null);
		List<CircleMemberReviewModel> list = this.selectByFilter(circleMemberReviewModel);
		return new PageInfo<>(list);
	}

	@Override
	public List<CircleMemberReviewModel> selectByFilter(CircleMemberReviewModel circleMemberReviewModel) {
		Example example = new Example(CircleMemberReviewModel.class);
		Example.Criteria criteria = example.createCriteria();

		if(StringUtils.isNotEmpty(circleMemberReviewModel.getSortWithOutOrderBy())) {
			example.setOrderByClause(circleMemberReviewModel.getSortWithOutOrderBy());
		}
		return getMapper().selectByExample(example);
	}

	/**
	 * 修改酷玩圈成员审核表
	 *
	 * @param circleId
	 * @param inviteUserId
	 * @param memberUserId
	 * @param reviewStatus
	 * @return
	 */
	public int updateByCircleIdAndInviteMemberUserId(Integer circleId, Integer inviteUserId, Integer memberUserId, Integer reviewStatus) {
		if(circleId == null || inviteUserId == null || memberUserId == null || reviewStatus == null) {
			return 0;
		}

		return circleMemberReviewMapper.updateByCircleIdAndInviteMemberUserId(circleId, inviteUserId, memberUserId, reviewStatus);
	}
}
