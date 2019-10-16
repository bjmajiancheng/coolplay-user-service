package com.coolplay.user.user.service.impl;

import com.coolplay.user.common.baseservice.impl.BaseService;
import com.coolplay.user.user.dao.CompanyMapper;
import com.coolplay.user.user.model.CompanyModel;
import com.coolplay.user.user.service.ICompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
