/*
 * 北京果敢时代科技有限公司
 * 北京市朝阳区望京SOHO T3 B座1607
 * 邮编：100022
 * 网址：www.davdian.com
 */

package com.coolplay.user.user.dao;
import com.coolplay.user.user.model.MessageModel;
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

public interface MessageMapper extends Mapper<MessageModel> {

	public List<MessageModel> find(Map<String, Object> param);

	public MessageModel findById(@Param("id") Integer id);

	public int updateIsRead(@Param("userId")Integer userId, @Param("isRead")int isRead);
}
