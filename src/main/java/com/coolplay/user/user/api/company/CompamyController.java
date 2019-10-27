package com.coolplay.user.user.api.company;

import com.coolplay.user.common.utils.ResponseUtil;
import com.coolplay.user.common.utils.Result;
import com.coolplay.user.security.utils.SecurityUtil;
import com.coolplay.user.user.model.CompanyModel;
import com.coolplay.user.user.model.UserCollectModel;
import com.coolplay.user.user.service.ICompanyService;
import com.coolplay.user.user.service.IUserCollectService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Collections;
import java.util.List;

/**
 * Created by majiancheng on 2019/10/25.
 */
@Controller
@RequestMapping("/api/company/company")
public class CompamyController {

    @Autowired
    private ICompanyService companyService;

    @Autowired
    private IUserCollectService userCollectService;

    @ResponseBody
    @RequestMapping(value = "/options", method = RequestMethod.POST)
    public Result companyOptions(@RequestParam("companyName")String companyName) {
        CompanyModel companyModel = new CompanyModel();
        companyModel.setCompanyName(companyName);
        companyModel.setStatus(1);
        companyModel.setReviewStatus(1);
        companyModel.setIsDel(0);
        List<CompanyModel> companyModels = companyService.selectByFilter(companyModel);

        return ResponseUtil.success(Collections.singletonMap("companyList", companyModels));
    }

    /**
     * 俱乐部详情信息
     *
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/detail", method = RequestMethod.POST)
    public Result companyDetail(@RequestParam("id") Integer id) {
        CompanyModel companyModel = companyService.findCompanyById(id);

        List<Integer> companyIds = userCollectService.findCompanyIdsByUserIdAndCompanyIds(
                SecurityUtil.getCurrentUserId(), Collections.singletonList(id));
        if(companyIds.contains(companyModel.getId())) {
            companyModel.setIsCollect(1);
        }

        return ResponseUtil.success(companyModel);
    }

    /**
     * 俱乐部收藏
     *
     * @param id
     * @param type
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/collectCompany", method = RequestMethod.POST)
    public Result collectCompany(@RequestParam("id")Integer id, @RequestParam("type")Integer type) {

        int currUserId = SecurityUtil.getCurrentUserId();
        //收藏
        if(type == 1) {
            UserCollectModel userCollectModel = new UserCollectModel();
            userCollectModel.setUserId(currUserId);
            userCollectModel.setCollectType(3);
            userCollectModel.setCollectTypeId(id);
            userCollectModel.setIsDel(0);

            if(CollectionUtils.isEmpty(userCollectService.selectByFilter(userCollectModel))) {
                int saveCnt = userCollectService.saveNotNull(userCollectModel);
            }
        //取消收藏
        } else if(type == 2) {
            int delCnt = userCollectService.delByUserIdAndCollectTypeInfo(currUserId, 3, id);
        }

        return ResponseUtil.success(Collections.singletonMap("collectCnt", userCollectService.findCntByCollectTypeAndCollectTypeId(3, id)));
    }
}
