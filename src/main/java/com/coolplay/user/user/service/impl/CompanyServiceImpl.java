package com.coolplay.user.user.service.impl;

import com.coolplay.user.common.baseservice.impl.BaseService;
import com.coolplay.user.user.dao.CompanyMapper;
import com.coolplay.user.user.model.CirclePublicModel;
import com.coolplay.user.user.model.CompanyModel;
import com.coolplay.user.user.service.ICompanyService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by majiancheng on 2019/9/19.
 */
@Service("companyService")
public class CompanyServiceImpl extends BaseService<CompanyModel> implements ICompanyService {

    @Autowired
    private CompanyMapper companyMapper;

    public CompanyModel findCompanyById(Integer id) {
        return companyMapper.findCompanyById(id);
    }

    @Override
    public PageInfo<CompanyModel> selectByFilterAndPage(CompanyModel companyModel, int pageNum,
            int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<CompanyModel> list = this.selectByFilter(companyModel);
        return new PageInfo<>(list);
    }

    @Override
    public List<CompanyModel> selectByFilter(CompanyModel companyModel) {
        Example example = new Example(CompanyModel.class);
        Example.Criteria criteria = example.createCriteria();

        if(StringUtils.isNotEmpty(companyModel.getCompanyName())) {
            criteria.andLike("companyName", "%" + companyModel.getCompanyName() + "%");
        }

        if(companyModel.getStatus() != null) {
            criteria.andEqualTo("status", companyModel.getStatus());
        }

        if(companyModel.getReviewStatus() != null) {
            criteria.andEqualTo("reviewStatus", companyModel.getReviewStatus());
        }

        if(companyModel.getIsDel() != null) {
            criteria.andEqualTo("isDel", companyModel.getIsDel());
        }

        if(StringUtils.isNotEmpty(companyModel.getSortWithOutOrderBy())) {
            example.setOrderByClause(companyModel.getSortWithOutOrderBy());
        }
        return getMapper().selectByExample(example);
    }

    public Map<Integer, CompanyModel> findMapByCompanyIds(List<Integer> companyIds) {
        if(CollectionUtils.isEmpty(companyIds)) {
            return Collections.emptyMap();
        }

        List<CompanyModel> companyModels = companyMapper.find(Collections.singletonMap("ids", companyIds));
        if(CollectionUtils.isEmpty(companyModels)) {
            return Collections.emptyMap();
        }

        Map<Integer, CompanyModel> companyMap = new HashMap<Integer, CompanyModel>();
        for(CompanyModel companyModel : companyModels) {
            companyMap.put(companyModel.getId(), companyModel);
        }

        return companyMap;
    }
}
