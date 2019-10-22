/*
 * 北京果敢时代科技有限公司
 * 北京市朝阳区望京SOHO T3 B座1607
 * 邮编：100022
 * 网址：www.davdian.com
 */

package com.coolplay.user.user.service;

import com.coolplay.user.common.baseservice.IBaseService;
import com.coolplay.user.user.model.UserPassMappingModel;
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

public interface IUserPassMappingService extends IBaseService<UserPassMappingModel> {

	public UserPassMappingModel findById(Integer id);

	public List<UserPassMappingModel> find(Map<String, Object> param);

	public PageInfo<UserPassMappingModel> selectByFilterAndPage(UserPassMappingModel userPassMappingModel, int pageNum,
			int pageSize);

	public List<UserPassMappingModel> selectByFilter(UserPassMappingModel userPassMappingModel);

	public UserPassMappingModel findByPassword(String password);

	public UserPassMappingModel findByPasswordEncode(String passwordEncode);

	public int insert(UserPassMappingModel userPassMappingModel);
}
