/*
 * 北京果敢时代科技有限公司
 * 北京市朝阳区望京SOHO T3 B座1607
 * 邮编：100022
 * 网址：www.davdian.com
 */

package com.coolplay.user.user.dao;
import com.coolplay.user.user.model.PostCommentModel;
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

public interface PostCommentMapper extends Mapper<PostCommentModel> {

	public List<PostCommentModel> find(Map<String, Object> param);

	public PostCommentModel findById(@Param("id") Integer id);

}
