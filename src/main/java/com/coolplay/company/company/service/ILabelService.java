/*
 * 北京果敢时代科技有限公司
 * 北京市朝阳区望京SOHO T3 B座1607
 * 邮编：100022
 * 网址：www.davdian.com
 */

package com.coolplay.company.company.service;

import com.coolplay.company.common.baseservice.IBaseService;
import com.coolplay.company.company.model.LabelModel;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import java.util.*;
import com.coolplay.company.company.dao.*;
import com.coolplay.company.company.service.*;

/**
 * @author  davdian
 * @version 1.0
 * @since 1.0
 */

public interface ILabelService extends IBaseService<LabelModel> {

	public LabelModel findById(Integer id);

	public List<LabelModel> find(Map<String, Object> param);

	public PageInfo<LabelModel> selectByFilterAndPage(LabelModel labelModel, int pageNum, int pageSize);

	public List<LabelModel> selectByFilter(LabelModel labelModel);

}
