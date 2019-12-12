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
import com.coolplay.user.core.model.UserModel;
import com.coolplay.user.security.service.IUserService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.coolplay.user.user.model.PostCommentModel;
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

@Service("postCommentService")
public class PostCommentServiceImpl extends BaseService<PostCommentModel> implements IPostCommentService{
	@Autowired
	private PostCommentMapper postCommentMapper;

	@Autowired
	private IUserService userService;
	
	@Override
	public PostCommentModel findById(Integer id) {
		if(id == null) {
			return null;
		}
		return postCommentMapper.findById(id);
	}


	public List<PostCommentModel> find(Map<String, Object> param) {
		return postCommentMapper.find(param);
	}

	@Override
	public PageInfo<PostCommentModel> selectByFilterAndPage(PostCommentModel postCommentModel, int pageNum,
		int pageSize) {
		PageHelper.startPage(pageNum, pageSize, true, false, null);
		List<PostCommentModel> list = this.selectByFilter(postCommentModel);
		return new PageInfo<>(list);
	}

	@Override
	public List<PostCommentModel> selectByFilter(PostCommentModel postCommentModel) {
		Example example = new Example(PostCommentModel.class);
		Example.Criteria criteria = example.createCriteria();

		if(StringUtils.isNotEmpty(postCommentModel.getSortWithOutOrderBy())) {
			example.setOrderByClause(postCommentModel.getSortWithOutOrderBy());
		}
		return getMapper().selectByExample(example);
	}


	public List<PostCommentModel> findByPostId(Integer postId) {
		if(postId == null) {
			return Collections.emptyList();
		}
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("postId", postId);
		param.put("isDel", 0);
		List<PostCommentModel> postComments = this.find(param);

		if(CollectionUtils.isEmpty(postComments)) {
			return Collections.emptyList();
		}

		List<Integer> commentUserIds = new ArrayList<Integer>();
		for(PostCommentModel postCommentModel : postComments) {
			if(!commentUserIds.contains(postCommentModel.getCommentUserId())) {
				commentUserIds.add(postCommentModel.getCommentUserId());
			}
		}

		Map<Integer, UserModel> userMap = userService.findUserMapByUserIds(commentUserIds);

		List<PostCommentModel> rootPostComments = new ArrayList<PostCommentModel>();
		Map<Integer, List<PostCommentModel>> childPostCommentMap = new HashMap<Integer, List<PostCommentModel>>();
		for(PostCommentModel postComment : postComments) {
			UserModel commentUserModel = userMap.get(postComment.getCommentUserId());
			if(commentUserModel != null) {
				postComment.setCommentNickName(commentUserModel.getNickName());
				postComment.setCommentHeadImage(commentUserModel.getHeadImage());
			}

			if(postComment.getCommentLevel() == 1) {
				rootPostComments.add(postComment);
			} else {
				List<PostCommentModel> childPostComments = childPostCommentMap.get(postComment.getPostCommentId());
				if(CollectionUtils.isEmpty(childPostComments)) {
					childPostComments = new ArrayList<PostCommentModel>();
				}

				childPostComments.add(postComment);
				childPostCommentMap.put(postComment.getPostCommentId(), childPostComments);
			}

		}

		for(PostCommentModel rootPostComment : rootPostComments) {
			List<PostCommentModel> secondComments = childPostCommentMap.get(rootPostComment.getId());
			if(CollectionUtils.isNotEmpty(secondComments)) {
				for(PostCommentModel secondComment : secondComments) {
					secondComment.setCommentList(childPostCommentMap.get(secondComment.getId()));
				}
			}
			rootPostComment.setCommentList(secondComments);
		}

		return rootPostComments;
	}
}
