/*
 * 北京果敢时代科技有限公司
 * 北京市朝阳区望京SOHO T3 B座1607
 * 邮编：100022
 * 网址：www.davdian.com
 */

package com.coolplay.company.company.dao;
import com.coolplay.company.company.model.CircleMemberModel;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.*;
import com.coolplay.company.company.dao.*;
import com.coolplay.company.company.service.*;

/**
 * @author  davdian
 * @version 1.0
 * @since 1.0
 */

public interface CircleMemberMapper extends Mapper<CircleMemberModel> {

	public List<CircleMemberModel> find(Map<String, Object> param);

	public CircleMemberModel findById(@Param("id") Integer id);

}
