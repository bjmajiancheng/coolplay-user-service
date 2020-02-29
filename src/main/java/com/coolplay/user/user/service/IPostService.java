/*
 * 北京果敢时代科技有限公司
 * 北京市朝阳区望京SOHO T3 B座1607
 * 邮编：100022
 * 网址：www.davdian.com
 */

package com.coolplay.user.user.service;

import com.coolplay.user.common.baseservice.IBaseService;
import com.coolplay.user.user.model.PostModel;
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

public interface IPostService extends IBaseService<PostModel> {

	public PostModel findById(Integer id);

	public List<PostModel> find(Map<String, Object> param);

	public PageInfo<PostModel> selectByFilterAndPage(PostModel postModel, int pageNum, int pageSize);

	public List<PostModel> selectByFilter(PostModel postModel);

	public int columnPlusNumber(Integer id, String columnName, Integer number);

	/**
	 * 根据酷玩圈ID获取帖子集合
	 *
	 * @param circleId
	 * @return
     */
	public List<PostModel> findByCircleId(Integer circleId);

	/**
	 * 根据帖子集合获取帖子map信息
	 *
	 * @param ids
	 * @return
     */
	public Map<Integer, PostModel> findMapByIds(List<Integer> ids);

	public List<Integer> findPostIdsByLabelName(String labelName);

	/**
	 * 获取帖子集合信息
	 *
	 * @param postModel
	 * @param pageNum
	 * @param pageSize
     * @return
     */
	public PageInfo<PostModel> findPageByPostModel(PostModel postModel, Integer pageNum, Integer pageSize);
}
