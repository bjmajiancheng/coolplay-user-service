/*
 * 北京果敢时代科技有限公司
 * 北京市朝阳区望京SOHO T3 B座1607
 * 邮编：100022
 * 网址：www.davdian.com
 */

package com.coolplay.user.user.dao;
import com.coolplay.user.user.model.CoolplayBaseModel;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.*;

/**
 * @author  davdian
 * @version 1.0
 * @since 1.0
 */

public interface CoolplayBaseMapper extends Mapper<CoolplayBaseModel> {

	public List<CoolplayBaseModel> find(Map<String, Object> param);

	public CoolplayBaseModel findById(@Param("id") Integer id);

	public List<CoolplayBaseModel> findOptionDatas();

	public List<CoolplayBaseModel> findByLastUpdatetime(@Param("updateTime")String updateTime);
}
