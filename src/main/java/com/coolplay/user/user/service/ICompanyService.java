package com.coolplay.user.user.service;

import com.coolplay.user.common.baseservice.IBaseService;
import com.coolplay.user.user.model.CirclePublicModel;
import com.coolplay.user.user.model.CompanyModel;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

/**
 * Created by majiancheng on 2019/9/19.
 */
public interface ICompanyService extends IBaseService<CompanyModel> {

    public CompanyModel findCompanyById(Integer id);

    public PageInfo<CompanyModel> selectByFilterAndPage(CompanyModel companyModel, int pageNum,
            int pageSize);

    public List<CompanyModel> selectByFilter(CompanyModel companyModel);

    public Map<Integer, CompanyModel> findMapByCompanyIds(List<Integer> companyIds);

    public List<CompanyModel> find(Map<String, Object> param);

    public List<CompanyModel> findOptionDatas();
}
