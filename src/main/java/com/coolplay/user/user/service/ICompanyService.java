package com.coolplay.user.user.service;

import com.coolplay.user.common.baseservice.IBaseService;
import com.coolplay.user.user.model.CompanyModel;

/**
 * Created by majiancheng on 2019/9/19.
 */
public interface ICompanyService extends IBaseService<CompanyModel> {

    public CompanyModel findCompanyById(Integer id);

}
