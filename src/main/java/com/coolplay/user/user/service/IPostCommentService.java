/*
 * 北京果敢时代科技有限公司
 * 北京市朝阳区望京SOHO T3 B座1607
 * 邮编：100022
 * 网址：www.davdian.com
 */

package com.coolplay.user.user.service;

import com.coolplay.user.common.baseservice.IBaseService;
import com.coolplay.user.user.model.PostCommentModel;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import java.util.*;
import com.coolplay.user.user.dao.*;
import com.coolplay.user.user.service.*;

/**
 * @author  davdian
 * @version 1.0
 * @since 1.0
 */

public interface IPostCommentService extends IBaseService<PostCommentModel> {

	public PostCommentModel findById(Integer id);

	public List<PostCommentModel> find(Map<String, Object> param);

	public PageInfo<PostCommentModel> selectByFilterAndPage(PostCommentModel postCommentModel, int pageNum,
			int pageSize);

	public List<PostCommentModel> selectByFilter(PostCommentModel postCommentModel);

	public List<PostCommentModel> findByPostId(Integer postId);
}
