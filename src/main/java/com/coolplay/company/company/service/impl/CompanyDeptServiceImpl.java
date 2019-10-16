package com.coolplay.company.company.service.impl;

import com.coolplay.company.common.baseservice.impl.BaseService;
import com.coolplay.company.company.dao.CompanyDeptMapper;
import com.coolplay.company.company.model.CompanyDeptModel;
import com.coolplay.company.company.service.ICompanyDeptService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * Created by majiancheng on 2019/9/19.
 */
@Service("companyDeptService")
public class CompanyDeptServiceImpl extends BaseService<CompanyDeptModel> implements ICompanyDeptService {

    @Autowired
    private CompanyDeptMapper companyDeptMapper;

    @Override
    public PageInfo<CompanyDeptModel> selectByFilterAndPage(CompanyDeptModel companyDeptModel, int pageNum,
            int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<CompanyDeptModel> list = this.selectByFilter(companyDeptModel);
        return new PageInfo<>(list);
    }

    @Override
    public List<CompanyDeptModel> selectByFilter(CompanyDeptModel companyDeptModel) {
        Example example = new Example(CompanyDeptModel.class);
        Example.Criteria criteria = example.createCriteria();

        if (companyDeptModel.getStartDate() != null) {
            criteria.andGreaterThanOrEqualTo("ctime", companyDeptModel.getStartDate());
        }
        if (companyDeptModel.getEndDate() != null) {
            criteria.andLessThanOrEqualTo("ctime", companyDeptModel.getEndDate());
        }
        if (companyDeptModel.getCompanyId() != null) {
            criteria.andEqualTo("companyId", companyDeptModel.getCompanyId());
        }
        if (StringUtils.isNotEmpty(companyDeptModel.getDeptName())) {
            criteria.andLike("deptName", "%" + companyDeptModel.getDeptName() + "%");
        }
        if (companyDeptModel.getStatus() != null) {
            criteria.andEqualTo("status", companyDeptModel.getStatus());
        }
        if(companyDeptModel.getCompanyId() != null) {
            criteria.andEqualTo("companyId", companyDeptModel.getCompanyId());
        }
        if (StringUtils.isNotEmpty(companyDeptModel.getSortWithOutOrderBy())) {
            example.setOrderByClause(companyDeptModel.getSortWithOutOrderBy());
        }
        return getMapper().selectByExample(example);
    }

    @Override
    public CompanyDeptModel selectById(int id) {
        return companyDeptMapper.findById(id);
    }
}
