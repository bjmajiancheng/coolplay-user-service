package com.coolplay.company.company.service;

import com.coolplay.company.common.baseservice.IBaseService;
import com.coolplay.company.company.model.CompanyModel;

/**
 * Created by majiancheng on 2019/9/19.
 */
public interface ICompanyService extends IBaseService<CompanyModel> {

    public CompanyModel findCompanyById(Integer id);

}
