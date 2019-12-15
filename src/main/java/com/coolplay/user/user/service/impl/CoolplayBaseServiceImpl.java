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
import com.coolplay.user.user.model.CoolplayBaseModel;
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
        PageHelper.startPage(pageNum, pageSize, true, false, null);
        List<CoolplayBaseModel> list = this.selectByFilter(coolplayBaseModel);
        return new PageInfo<>(list);
    }

    @Override
    public List<CoolplayBaseModel> selectByFilter(CoolplayBaseModel coolplayBaseModel) {
        Example example = new Example(CoolplayBaseModel.class);
        Example.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotEmpty(coolplayBaseModel.getQueryStr())) {
            criteria.andLike("baseName", "%" + coolplayBaseModel.getQueryStr() + "%");
        }

        if (coolplayBaseModel.getIsClose() != null) {
            criteria.andEqualTo("isClose", coolplayBaseModel.getIsClose());
        }

        if(coolplayBaseModel.getIsDel() != null) {
            criteria.andEqualTo("isDel", coolplayBaseModel.getIsDel());
        }

        if(coolplayBaseModel.getCompanyId() != null && coolplayBaseModel.getCompanyId() != 0) {
            criteria.andEqualTo("companyId", coolplayBaseModel.getCompanyId());
        }

        if(coolplayBaseModel.getId() != null && coolplayBaseModel.getId() != 0) {
            criteria.andEqualTo("id", coolplayBaseModel.getId());
        }

        if (StringUtils.isNotEmpty(coolplayBaseModel.getSortWithOutOrderBy())) {
            example.setOrderByClause(coolplayBaseModel.getSortWithOutOrderBy());
        }
        return getMapper().selectByExample(example);
    }

    /**
     * 获取基地信息
     *
     * @param ids
     * @return
     */
    public Map<Integer, CoolplayBaseModel> findMapByIds(List<Integer> ids) {
        if(CollectionUtils.isEmpty(ids)) {
            return Collections.emptyMap();
        }
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("ids", ids);
        param.put("isClose", 0);
        param.put("isDel", 0);

        List<CoolplayBaseModel> coolplayBaseModels = this.find(param);

        if(CollectionUtils.isEmpty(coolplayBaseModels)) {
            return Collections.emptyMap();
        }

        Map<Integer, CoolplayBaseModel> coolplayBaseMap = new HashMap<Integer, CoolplayBaseModel>();
        for(CoolplayBaseModel coolplayBaseModel : coolplayBaseModels) {
            coolplayBaseMap.put(coolplayBaseModel.getId(), coolplayBaseModel);
        }

        return coolplayBaseMap;
    }

    public List<CoolplayBaseModel> findOptionDatas() {
        return coolplayBaseMapper.findOptionDatas();
    }


    public List<CoolplayBaseModel> findByLastUpdatetime(String updateTime) {
        if(StringUtils.isEmpty(updateTime)) {
            return Collections.emptyList();
        }

        return coolplayBaseMapper.findByLastUpdatetime(updateTime);
    }
}
