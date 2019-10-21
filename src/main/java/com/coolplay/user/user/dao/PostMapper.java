/*
 * 北京果敢时代科技有限公司
 * 北京市朝阳区望京SOHO T3 B座1607
 * 邮编：100022
 * 网址：www.davdian.com
 */

package com.coolplay.user.user.dao;
import com.coolplay.user.user.model.PostModel;
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

public interface PostMapper extends Mapper<PostModel> {

	public List<PostModel> find(Map<String, Object> param);

	public PostModel findById(@Param("id") Integer id);

	public int columnPlusNumber(@Param("id")Integer id, @Param("columnName")String columnName, @Param("num")Integer number);
}
