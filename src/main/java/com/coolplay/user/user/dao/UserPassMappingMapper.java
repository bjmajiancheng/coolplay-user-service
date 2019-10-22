/*
 * 北京果敢时代科技有限公司
 * 北京市朝阳区望京SOHO T3 B座1607
 * 邮编：100022
 * 网址：www.davdian.com
 */

package com.coolplay.user.user.dao;
import com.coolplay.user.user.model.UserPassMappingModel;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.*;
import com.coolplay.user.user.dao.*;
import com.coolplay.user.user.service.*;

/**
 * @author  davdian
 * @version 1.0
 * @since 1.0
 */

public interface UserPassMappingMapper extends Mapper<UserPassMappingModel> {

	public List<UserPassMappingModel> find(Map<String, Object> param);

	public UserPassMappingModel findById(@Param("id") Integer id);

	public UserPassMappingModel findByPassword(@Param("password")String password);

	public UserPassMappingModel findByPasswordEncode(@Param("passwordEncode")String passwordEncode);

	public int insert(UserPassMappingModel userPassMappingModel);
}
