package com.coolplay.company.company.api.company;

import com.coolplay.company.common.utils.PageConvertUtil;
import com.coolplay.company.common.utils.ResponseUtil;
import com.coolplay.company.common.utils.Result;
import com.coolplay.company.company.model.*;
import com.coolplay.company.company.service.ICircleLabelService;
import com.coolplay.company.company.service.ICirclePublicService;
import com.coolplay.company.company.service.ICircleService;
import com.coolplay.company.company.service.ICompanyCircleService;
import com.coolplay.company.core.model.UserRoleModel;
import com.coolplay.company.security.security.CoolplayUserCache;
import com.coolplay.company.security.service.IRoleService;
import com.coolplay.company.security.service.IUserService;
import com.coolplay.company.security.utils.SecurityUtil;
import com.github.pagehelper.PageInfo;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created by majiancheng on 2019/9/22.
 */
@Controller
@RequestMapping("/api/company/companyCircle")
public class CompanyCircleController {

    @Autowired
    private ICompanyCircleService companyCircleService;

    @Autowired
    private com.coolplay.company.company.service.IUserService appUserService;

    @Autowired
    private ICircleService circleService;

    @Autowired
    private ICirclePublicService circlePublicService;

    @Autowired
    private ICircleLabelService circleLabelService;

    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Map list(CompanyCircleModel companyCircleModel,
            @RequestParam(value = "page", required = false, defaultValue = "1") int pageNum,
            @RequestParam(value = "rows", required = false, defaultValue = "15") int pageSize) {

        companyCircleModel.setCompanyId(SecurityUtil.getCurrentCompanyId());
        PageInfo<CompanyCircleModel> pageInfo = companyCircleService
                .selectByFilterAndPage(companyCircleModel, pageNum, pageSize);
        if (CollectionUtils.isNotEmpty(pageInfo.getList())) {
            List<Integer> applicationUserIds = new ArrayList<Integer>(pageInfo.getList().size());
            List<Integer> circleIds = new ArrayList<Integer>(pageInfo.getList().size());
            for (CompanyCircleModel tmpCompanyCircle : pageInfo.getList()) {
                if (!applicationUserIds.contains(tmpCompanyCircle.getApplicationUserId())) {
                    applicationUserIds.add(tmpCompanyCircle.getApplicationUserId());
                }

                if(!circleIds.contains(tmpCompanyCircle.getCircleId())) {
                    circleIds.add(tmpCompanyCircle.getCircleId());
                }
            }

            Map<Integer, UserModel> userModelMap = appUserService.findMapByUserIds(applicationUserIds);
            Map<Integer, CircleModel> circleModelMap = circleService.findMapByIds(circleIds);
            Map<Integer, List<CircleLabelModel>> circleLabelMap = circleLabelService.findMapByCircleIds(circleIds);

            for (CompanyCircleModel tmpCompanyCircle : pageInfo.getList()) {
                int circleId = tmpCompanyCircle.getCircleId();

                UserModel userModel = userModelMap.get(tmpCompanyCircle.getApplicationUserId());
                CircleModel circleModel = circleModelMap.get(circleId);
                if (userModel != null) {
                    tmpCompanyCircle.setApplicationUserName(userModel.getNickName());
                }
                if(circleModel != null) {
                    tmpCompanyCircle.setCircleName(circleModel.getCircleName());
                    tmpCompanyCircle.setMemberCnt(circleModel.getMemberCnt());
                }
                List<CircleLabelModel> circleLabels = circleLabelMap.get(circleId);
                if(CollectionUtils.isNotEmpty(circleLabels)) {
                    StringBuffer sb = new StringBuffer();
                    for(CircleLabelModel circleLabelModel : circleLabels) {
                        if(sb.length() > 0) {
                            sb.append("、");
                        }
                        sb.append(circleLabelModel.getLabelName());
                    }
                    tmpCompanyCircle.setCircleLabels(sb.toString());
                }

                CirclePublicModel circlePublicModel = circlePublicService.findLastPublicByCircleId(circleId);
                if(circlePublicModel != null) {
                    tmpCompanyCircle.setPublicContent(circlePublicModel.getPublicContent());
                }
            }
        }

        return PageConvertUtil.grid(pageInfo);
    }

    @ResponseBody
    @RequestMapping(value = "/getCompanyCircle", method = RequestMethod.GET)
    public Result getUser(@RequestParam("id") int id) {
        CompanyCircleModel companyCircleModel = companyCircleService.findById(id);
        Integer circleId = companyCircleModel.getCircleId();
        CircleModel circleModel = circleService.findById(circleId);
        CirclePublicModel circlePublicModel = circlePublicService.findLastPublicByCircleId(circleId);
        List<CircleLabelModel> circleLabels = circleLabelService.find(Collections.singletonMap("circleId", circleId));

        UserModel userModel = appUserService.findById(companyCircleModel.getApplicationUserId());

        if(circleModel != null) {
            companyCircleModel.setCircleName(circleModel.getCircleName());
            companyCircleModel.setMemberCnt(circleModel.getMemberCnt());
        } else {
            companyCircleModel.setCircleName("");
            companyCircleModel.setMemberCnt(0);
        }

        if(circlePublicModel != null) {
            companyCircleModel.setPublicContent(circlePublicModel.getPublicContent());
        } else {
            companyCircleModel.setPublicContent("");
        }

        if (CollectionUtils.isNotEmpty(circleLabels)) {
            StringBuffer sb = new StringBuffer();
            for(CircleLabelModel circleLabelModel : circleLabels) {
                if(sb.length() > 0) {
                    sb.append("、");
                }
                sb.append(circleLabelModel.getLabelName());
            }
            companyCircleModel.setCircleLabels(sb.toString());
        } else {
            companyCircleModel.setCircleLabels("");
        }

        if (userModel != null) {
            companyCircleModel.setApplicationUserName(userModel.getNickName());
        } else {
            companyCircleModel.setApplicationUserName("");
        }

        return ResponseUtil.success(companyCircleModel);
    }

    @ResponseBody
    @RequestMapping(value = "/updateCompanyCircle", method = RequestMethod.GET)
    public Result updateCompanyCircle(@RequestParam("id") int id, @RequestParam("status") int status,
            @RequestParam("rejectReason") String rejectReason) {
        CompanyCircleModel companyCircleModel = new CompanyCircleModel();
        companyCircleModel.setId(id);
        companyCircleModel.setReviewStatus(1);
        companyCircleModel.setStatus(status);
        companyCircleModel.setRejectReason(rejectReason);

        int updateCnt = companyCircleService.updateNotNull(companyCircleModel);

        return ResponseUtil.success();
    }

    @ResponseBody
    @RequestMapping(value = "/updateCompanyCircleDisable", method = RequestMethod.GET)
    public Result updateCompanyCircleDisable(@RequestParam("id") int id, @RequestParam("isDisable") int isDisable,
            @RequestParam("disableReason") String disableReason) {
        CompanyCircleModel companyCircleModel = new CompanyCircleModel();
        companyCircleModel.setId(id);
        companyCircleModel.setIsDisable(isDisable);
        companyCircleModel.setDisableReason(disableReason);

        int updateCnt = companyCircleService.updateNotNull(companyCircleModel);
        return ResponseUtil.success();
    }

}
