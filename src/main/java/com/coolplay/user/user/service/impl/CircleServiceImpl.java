/*
 * 北京果敢时代科技有限公司
 * 北京市朝阳区望京SOHO T3 B座1607
 * 邮编：100022
 * 网址：www.davdian.com
 */

package com.coolplay.user.user.service.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.coolplay.user.user.model.CircleModel;
import tk.mybatis.mapper.entity.Example;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;

import java.util.*;

import com.coolplay.user.user.dao.*;
import com.coolplay.user.user.service.*;
import com.coolplay.user.common.baseservice.impl.BaseService;

/**
 * @author davdian
 * @version 1.0
 * @since 1.0
 */

@Service("circleService")
public class CircleServiceImpl extends BaseService<CircleModel> implements ICircleService {

    @Autowired
    private CircleMapper circleMapper;

    @Override
    public CircleModel findById(Integer id) {
        if (id == null) {
            return null;
        }
        return circleMapper.findById(id);
    }

    public List<CircleModel> find(Map<String, Object> param) {
        return circleMapper.find(param);
    }

    @Override
    public PageInfo<CircleModel> selectByFilterAndPage(CircleModel circleModel, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize, true, false, null);

        List<CircleModel> list = this.selectByFilter(circleModel);
        return new PageInfo<>(list);
    }

    @Override
    public List<CircleModel> selectByFilter(CircleModel circleModel) {
        Example example = new Example(CircleModel.class);
        Example.Criteria criteria = example.createCriteria();

        if(StringUtils.isNotEmpty(circleModel.getQueryStr())) {
            criteria.andLike("circleName", "%"+ circleModel.getQueryStr() +"%");
        }

        if(circleModel.getDisabled() != null) {
            criteria.andEqualTo("disabled", circleModel.getDisabled());
        }

        if(circleModel.getReviewStatus() != null) {
            criteria.andEqualTo("reviewStatus", circleModel.getReviewStatus());
        }

        if(circleModel.getStatus() != null) {
            criteria.andEqualTo("status", circleModel.getStatus());
        }

        if(circleModel.getCircleType() != null) {
            criteria.andEqualTo("circleType", circleModel.getCircleType());
        }

        if(CollectionUtils.isNotEmpty(circleModel.getIds())) {
            criteria.andIn("id", circleModel.getIds());
        }

        if (StringUtils.isNotEmpty(circleModel.getSortWithOutOrderBy())) {
            example.setOrderByClause(circleModel.getSortWithOutOrderBy());
        }
        return getMapper().selectByExample(example);
    }

    public Map<Integer, CircleModel> findMapByIds(List<Integer> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return Collections.emptyMap();
        }

        List<CircleModel> circleModels = this.findByIds(ids);

        Map<Integer, CircleModel> circleModelMap = new HashMap<Integer, CircleModel>();
        if(CollectionUtils.isNotEmpty(circleModels)) {
            for(CircleModel circleModel: circleModels) {
                circleModelMap.put(circleModel.getId(), circleModel);
            }
        }

        return circleModelMap;
    }

    public List<CircleModel> findByIds(List<Integer> ids) {
        if(CollectionUtils.isEmpty(ids)) {
            return Collections.emptyList();
        }
        return this.find(Collections.singletonMap("ids", ids));
    }
}
