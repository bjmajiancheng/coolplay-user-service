/*
 * 北京果敢时代科技有限公司
 * 北京市朝阳区望京SOHO T3 B座1607
 * 邮编：100022
 * 网址：www.davdian.com
 */

package com.coolplay.company.company.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.coolplay.company.company.model.CoolplayBaseModel;
import tk.mybatis.mapper.entity.Example;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;

import java.util.*;

import com.coolplay.company.company.dao.*;
import com.coolplay.company.company.service.*;
import com.coolplay.company.common.baseservice.impl.BaseService;

/**
 * @author davdian
 * @version 1.0
 * @since 1.0
 */

@Service("coolplayBaseService")
public class CoolplayBaseServiceImpl extends BaseService<CoolplayBaseModel> implements ICoolplayBaseService {

    @Autowired
    private CoolplayBaseMapper coolplayBaseMapper;

    @Override
    public CoolplayBaseModel findById(Integer id) {
        if (id == null) {
            return null;
        }
        return coolplayBaseMapper.findById(id);
    }

    public List<CoolplayBaseModel> find(Map<String, Object> param) {
        return coolplayBaseMapper.find(param);
    }

    @Override
    public PageInfo<CoolplayBaseModel> selectByFilterAndPage(CoolplayBaseModel coolplayBaseModel, int pageNum,
            int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<CoolplayBaseModel> list = this.selectByFilter(coolplayBaseModel);
        return new PageInfo<>(list);
    }

    @Override
    public List<CoolplayBaseModel> selectByFilter(CoolplayBaseModel coolplayBaseModel) {
        Example example = new Example(CoolplayBaseModel.class);
        Example.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotEmpty(coolplayBaseModel.getBaseName())) {
            criteria.andLike("baseName", "%" + coolplayBaseModel.getBaseName() + "%");
        }

        if (coolplayBaseModel.getIsClose() != null) {
            criteria.andEqualTo("isClose", coolplayBaseModel.getIsClose());
        }

        if(coolplayBaseModel.getIsDel() != null) {
            criteria.andEqualTo("isDel", coolplayBaseModel.getIsDel());
        }

        if (StringUtils.isNotEmpty(coolplayBaseModel.getSortWithOutOrderBy())) {
            example.setOrderByClause(coolplayBaseModel.getSortWithOutOrderBy());
        }
        return getMapper().selectByExample(example);
    }
}
