package com.coolplay.user.user.api.common;

import com.coolplay.user.common.utils.PageConvertUtil;
import com.coolplay.user.common.utils.ResponseUtil;
import com.coolplay.user.common.utils.Result;
import com.coolplay.user.user.model.CompanyDeptModel;
import com.coolplay.user.user.model.CompanyModel;
import com.coolplay.user.user.service.ICompanyService;
import com.coolplay.user.security.security.SecurityUser;
import com.coolplay.user.security.utils.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by majiancheng on 2019/9/19.
 */
@Controller
@RequestMapping("/api/common/company")
public class CompanyController {

    @Autowired
    private ICompanyService companyService;

    @ResponseBody
    @RequestMapping(value="/companyInfo", method = RequestMethod.GET)
    public Result companyInfo(HttpServletRequest request) {
        SecurityUser securityUser = SecurityUtil.getCurrentSecurityUser();

        CompanyModel companyModel = companyService.findCompanyById(securityUser.getCompanyId());

        return ResponseUtil.success(companyModel);
    }

    @ResponseBody
    @RequestMapping(value = "/updateCompany", method = RequestMethod.POST)
    public Result updateCompany(CompanyModel companyModel) {
        companyModel.setReviewStatus(0);
        companyModel.setStatus(0);
        companyModel.setRejectReason("");
        int cnt = companyService.updateNotNull(companyModel);

        return ResponseUtil.success();
    }


}
