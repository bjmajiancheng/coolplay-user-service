package com.coolplay.company.company.service;

import com.coolplay.company.common.baseservice.IBaseService;
import com.coolplay.company.company.model.CompanyDeptModel;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * Created by majiancheng on 2019/9/19.
 */
public interface ICompanyDeptService extends IBaseService<CompanyDeptModel> {

    public PageInfo<CompanyDeptModel> selectByFilterAndPage(CompanyDeptModel companyDeptModel, int pageNum,
            int pageSize);

    public List<CompanyDeptModel> selectByFilter(CompanyDeptModel companyDeptModel);

    public CompanyDeptModel selectById(int id);
}
