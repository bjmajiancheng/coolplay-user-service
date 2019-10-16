/*
 * 北京果敢时代科技有限公司
 * 北京市朝阳区望京SOHO T3 B座1607
 * 邮编：100022
 * 网址：www.davdian.com
 */

package com.coolplay.company.company.dao;
import com.coolplay.company.company.model.CompanyModel;
import com.coolplay.company.core.model.RoleFunctionModel;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.*;

/**
 * @author  davdian
 * @version 1.0
 * @since 1.0
 */

public interface CompanyMapper extends Mapper<CompanyModel> {

	public List<CompanyModel> find(Map<String, Object> param);

	public CompanyModel findCompanyById(@Param("id")Integer id);

}
