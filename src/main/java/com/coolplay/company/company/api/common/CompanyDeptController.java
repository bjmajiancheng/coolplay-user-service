package com.coolplay.company.company.api.common;

import com.coolplay.company.common.utils.PageConvertUtil;
import com.coolplay.company.common.utils.ResponseUtil;
import com.coolplay.company.common.utils.Result;
import com.coolplay.company.company.model.CompanyDeptModel;
import com.coolplay.company.company.service.ICompanyDeptService;
import com.coolplay.company.security.utils.SecurityUtil;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * Created by majiancheng on 2019/9/19.
 */
@Controller
@RequestMapping("/api/common/companyDept")
public class CompanyDeptController {

    @Autowired
    private ICompanyDeptService companyDeptService;

    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Map list(CompanyDeptModel companyDeptModel,
            @RequestParam(value = "page", required = false, defaultValue = "1") int pageNum,
            @RequestParam(value = "rows", required = false, defaultValue = "15") int pageSize) {
        companyDeptModel.setStatus(1);
        companyDeptModel.setCompanyId(SecurityUtil.getCurrentCompanyId());
        PageInfo<CompanyDeptModel> pageInfo = companyDeptService
                .selectByFilterAndPage(companyDeptModel, pageNum, pageSize);

        return PageConvertUtil.grid(pageInfo);
    }

    /**
     * 获取公司部门信息
     *
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getCompanyDept", method = RequestMethod.GET)
    public Result getCompanyDept(@RequestParam("id") int id) {
        CompanyDeptModel companyDeptModel = companyDeptService.selectById(id);

        return ResponseUtil.success(companyDeptModel);
    }

    /**
     * 禁用或启用公司部门
     *
     * @param id
     * @param status
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/disableCompanyDept", method = RequestMethod.GET)
    public Result disableCompanyDept(@RequestParam("id") int id, @RequestParam("status") int status) {
        CompanyDeptModel companyDeptModel = new CompanyDeptModel();
        companyDeptModel.setId(id);
        companyDeptModel.setStatus(status);
        int updateCnt = companyDeptService.updateNotNull(companyDeptModel);

        return ResponseUtil.success();
    }

    /**
     * 添加公司部门信息
     *
     * @param companyDeptModel
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/saveCompanyDept", method = RequestMethod.POST)
    public Result saveCompanyDept(CompanyDeptModel companyDeptModel) {
        companyDeptModel.setCompanyId(SecurityUtil.getCurrentCompanyId());
        companyDeptModel.setStatus(1);
        int addCnt = companyDeptService.save(companyDeptModel);

        return ResponseUtil.success();
    }

    /**
     * 修改公司部门信息
     *
     * @param companyDeptModel
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/updateCompanyDept", method = RequestMethod.POST)
    public Result updateCompanyDept(CompanyDeptModel companyDeptModel) {
        companyDeptModel.setCompanyId(SecurityUtil.getCurrentCompanyId());
        int updateCnt = companyDeptService.updateNotNull(companyDeptModel);

        return ResponseUtil.success();
    }
}
