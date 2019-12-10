/*
 * 北京果敢时代科技有限公司
 * 北京市朝阳区望京SOHO T3 B座1607
 * 邮编：100022
 * 网址：www.davdian.com
 */

package com.coolplay.user.user.service.impl;

import java.util.List;

import com.coolplay.user.user.model.CircleAdminModel;
import com.coolplay.user.user.model.CircleMemberModel;
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

    @Autowired
    private CircleMemberMapper circleMemberMapper;

    @Autowired
    private CircleAdminMapper circleAdminMapper;

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

        if(circleModel.getUserId() != null) {
            criteria.andEqualTo("userId", circleModel.getUserId());
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
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("ids", ids);
        param.put("reviewStatus", 1);
        param.put("status", 1);
        param.put("disabled", 0);

        return this.find(param);
    }

    /**
     * 根据圈子ID获取管理员和成员ID集合
     *
     * @param circleIds
     * @return
     */
    public Map<Integer, List<Integer>> getAdminOrMemberByIds(List<Integer> circleIds) {
        if(CollectionUtils.isEmpty(circleIds)) {
            return Collections.emptyMap();
        }

        Map<Integer, List<Integer>> circleMemberUserIdMap = new HashMap<Integer, List<Integer>>();

        List<CircleMemberModel> circleMembers = circleMemberMapper.findByCircleIds(circleIds);
        if(CollectionUtils.isNotEmpty(circleMembers)) {
            for(CircleMemberModel circleMember : circleMembers) {
                List<Integer> tmpUserIds = circleMemberUserIdMap.get(circleMember.getCircleId());
                if(CollectionUtils.isEmpty(tmpUserIds)) {
                    tmpUserIds = new ArrayList<Integer>();
                }

                if(!tmpUserIds.contains(circleMember.getMemberUserId())) {
                    tmpUserIds.add(circleMember.getMemberUserId());
                }

                circleMemberUserIdMap.put(circleMember.getCircleId(), tmpUserIds);
            }
        }
        List<CircleAdminModel> circleAdmins = circleAdminMapper.findByCircleIds(circleIds);
        if(CollectionUtils.isNotEmpty(circleAdmins)) {
            for(CircleAdminModel circleAdmin : circleAdmins) {
                List<Integer> tmpUserIds = circleMemberUserIdMap.get(circleAdmin.getCircleId());

                if(CollectionUtils.isEmpty(tmpUserIds)) {
                    tmpUserIds = new ArrayList<Integer>();
                }

                if(!tmpUserIds.contains(circleAdmin.getAdminUserId())) {
                    tmpUserIds.add(circleAdmin.getAdminUserId());
                }

                circleMemberUserIdMap.put(circleAdmin.getCircleId(), tmpUserIds);
            }
        }
        List<CircleModel> circles = this.findByIds(circleIds);
        if(CollectionUtils.isNotEmpty(circles)) {
            for(CircleModel circleModel : circles) {
                List<Integer> tmpUserIds = circleMemberUserIdMap.get(circleModel.getId());

                if(CollectionUtils.isEmpty(tmpUserIds)) {
                    tmpUserIds = new ArrayList<Integer>();
                }

                if(!tmpUserIds.contains(circleModel.getUserId())) {
                    tmpUserIds.add(circleModel.getUserId());
                }

                circleMemberUserIdMap.put(circleModel.getId(), tmpUserIds);
            }
        }

        return circleMemberUserIdMap;
    }

    /**
     *
     * 根据圈主ID获取圈子集合
     *
     * @param userId
     * @return
     */
    public List<Integer> findByUserId(Integer userId) {
        if(userId == null || userId == 0) {
            return Collections.emptyList();
        }

        return circleMapper.findByUserId(userId);
    }
}
