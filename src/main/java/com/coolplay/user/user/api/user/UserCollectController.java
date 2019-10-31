package com.coolplay.user.user.api.user;

import com.coolplay.user.common.utils.PageConvertUtil;
import com.coolplay.user.common.utils.ResponseUtil;
import com.coolplay.user.common.utils.Result;
import com.coolplay.user.core.model.UserModel;
import com.coolplay.user.security.service.IUserService;
import com.coolplay.user.security.utils.SecurityUtil;
import com.coolplay.user.user.model.*;
import com.coolplay.user.user.service.*;
import com.github.pagehelper.PageInfo;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created by majiancheng on 2019/10/27.
 */
@Controller
@RequestMapping("/api/user/userCollect")
public class UserCollectController {

    @Autowired
    private IUserCollectService userCollectService;

    @Autowired
    private ICircleService circleService;

    @Autowired
    private IPostService postService;

    @Autowired
    private ICompanyService companyService;

    @Autowired
    private ICoolplayBaseService coolplayBaseService;

    @Autowired
    private IUserService userService;

    @Autowired
    private ILabelService labelService;

    @Autowired
    private ICircleMemberService circleMemberService;

    @Autowired
    private IUserFansService userFansService;

    @ResponseBody
    @RequestMapping(value = "/circleList", method = RequestMethod.POST)
    public Result list(@RequestBody CircleModel circleModel) {

        try {
            UserCollectModel userCollectModel = new UserCollectModel();
            userCollectModel.setCollectType(1);
            userCollectModel.setUserId(SecurityUtil.getCurrentUserId());
            circleModel.initPageInfo();
            PageInfo<UserCollectModel> pageInfo = userCollectService
                    .selectByFilterAndPage(userCollectModel, circleModel.getPageNum(), circleModel.getPageSize());

            List<CircleModel> circleModels = new ArrayList<CircleModel>();
            if (CollectionUtils.isNotEmpty(pageInfo.getList())) {
                List<Integer> circleIds = new ArrayList<Integer>();
                for (UserCollectModel tmpUserCollect : pageInfo.getList()) {
                    circleIds.add(tmpUserCollect.getCollectTypeId());
                }

                Map<Integer, CircleModel> circleMap = circleService.findMapByIds(circleIds);
                for (UserCollectModel tmpUserCollect : pageInfo.getList()) {
                    if (circleMap.get(tmpUserCollect.getCollectTypeId()) != null) {
                        circleModels.add(circleMap.get(tmpUserCollect.getCollectTypeId()));
                    }
                }
            }

            if (CollectionUtils.isNotEmpty(circleModels)) {
                List<Integer> userIds = new ArrayList<Integer>();
                List<Integer> circleIds = new ArrayList<Integer>();
                for (CircleModel tmpCircleModel : circleModels) {
                    if (userIds.contains(tmpCircleModel.getUserId())) {
                        userIds.add(tmpCircleModel.getUserId());
                    }

                    circleIds.add(tmpCircleModel.getId());
                }

                Map<Integer, UserModel> userMap = userService.findUserMapByUserIds(userIds);
                Map<Integer, List<LabelModel>> labelMap = labelService.findMapByCircleIds(circleIds);
                List<Integer> circleMemberCircleIds = circleMemberService
                        .findCircleIdsByUserIdAndCircleIds(SecurityUtil.getCurrentUserId(), circleIds);

                List<Integer> userCollectCircleIds = userCollectService
                        .findCircleIdsByUserIdAndCircleIds(SecurityUtil.getCurrentUserId(), circleIds);

                for (CircleModel tmpCircleModel : circleModels) {
                    UserModel userModel = userMap.get(tmpCircleModel.getUserId());
                    if (userModel != null) {
                        tmpCircleModel.setNickName(userModel.getNickName());
                        tmpCircleModel.setHeadImage(userModel.getHeadImage());
                    }

                    if(CollectionUtils.isNotEmpty(labelMap.get(tmpCircleModel.getId()))) {
                        tmpCircleModel.setLabelList(labelMap.get(tmpCircleModel.getId()));
                    }

                    if (circleMemberCircleIds.contains(tmpCircleModel.getId())) {
                        tmpCircleModel.setIsMember(1);
                    }

                    if (userCollectCircleIds.contains(tmpCircleModel.getId())) {
                        tmpCircleModel.setIsCollect(1);
                    }
                }
            }

            PageInfo<CircleModel> circlePageInfo = new PageInfo<CircleModel>(circleModels);

            return ResponseUtil.success(PageConvertUtil.grid(circlePageInfo));

        } catch(Exception e) {
            e.printStackTrace();

            return ResponseUtil.error("系统异常, 请稍后重试。");
        }
    }

    @ResponseBody
    @RequestMapping(value = "/postList", method = RequestMethod.POST)
    public Result list(@RequestBody PostModel postModel) {

        try {
            UserCollectModel userCollectModel = new UserCollectModel();
            userCollectModel.setCollectType(2);
            userCollectModel.setUserId(SecurityUtil.getCurrentUserId());
            postModel.initPageInfo();
            PageInfo<UserCollectModel> pageInfo = userCollectService
                    .selectByFilterAndPage(userCollectModel, postModel.getPageNum(), postModel.getPageSize());

            List<PostModel> postModels = new ArrayList<PostModel>();
            if (CollectionUtils.isNotEmpty(pageInfo.getList())) {
                List<Integer> postIds = new ArrayList<Integer>();
                for (UserCollectModel tmpUserCollect : pageInfo.getList()) {
                    postIds.add(tmpUserCollect.getCollectTypeId());
                }

                Map<Integer, PostModel> postMap = postService.findMapByIds(postIds);
                for (UserCollectModel tmpUserCollect : pageInfo.getList()) {
                    if (postMap.get(tmpUserCollect.getCollectTypeId()) != null) {
                        postModels.add(postMap.get(tmpUserCollect.getCollectTypeId()));
                    }
                }
            }

            PageInfo<PostModel> postPageInfo = new PageInfo<PostModel>(postModels);

            return ResponseUtil.success(PageConvertUtil.grid(postPageInfo));

        } catch(Exception e) {
            e.printStackTrace();

            return ResponseUtil.error("系统异常, 请稍后重试。");
        }
    }

    @ResponseBody
    @RequestMapping(value = "/companyList", method = RequestMethod.POST)
    public Result list(@RequestBody CompanyModel companyModel) {

        try {
            UserCollectModel userCollectModel = new UserCollectModel();
            userCollectModel.setCollectType(3);
            userCollectModel.setUserId(SecurityUtil.getCurrentUserId());
            companyModel.initPageInfo();
            PageInfo<UserCollectModel> pageInfo = userCollectService
                    .selectByFilterAndPage(userCollectModel, companyModel.getPageNum(), companyModel.getPageSize());

            List<CompanyModel> companyModels = new ArrayList<CompanyModel>();

            if (CollectionUtils.isNotEmpty(pageInfo.getList())) {
                List<Integer> companyIds = new ArrayList<Integer>();
                for (UserCollectModel tmpUserCollect : pageInfo.getList()) {
                    companyIds.add(tmpUserCollect.getCollectTypeId());
                }

                Map<Integer, CompanyModel> companyMap = companyService.findMapByCompanyIds(companyIds);
                for (UserCollectModel tmpUserCollect : pageInfo.getList()) {
                    if (companyMap.get(tmpUserCollect.getCollectTypeId()) != null) {
                        companyModels.add(companyMap.get(tmpUserCollect.getCollectTypeId()));
                    }
                }
            }

            PageInfo<CompanyModel> companyPageInfo = new PageInfo<CompanyModel>(companyModels);

            return ResponseUtil.success(PageConvertUtil.grid(companyPageInfo));

        } catch(Exception e) {
            e.printStackTrace();

            return ResponseUtil.error("系统异常, 请稍后重试。");
        }
    }

    @ResponseBody
    @RequestMapping(value = "/baseList", method = RequestMethod.POST)
    public Result list(@RequestBody CoolplayBaseModel coolplayBaseModel) {

        try {
            UserCollectModel userCollectModel = new UserCollectModel();
            userCollectModel.setCollectType(4);
            userCollectModel.setUserId(SecurityUtil.getCurrentUserId());
            coolplayBaseModel.initPageInfo();
            PageInfo<UserCollectModel> pageInfo = userCollectService
                    .selectByFilterAndPage(userCollectModel, coolplayBaseModel.getPageNum(), coolplayBaseModel.getPageSize());

            List<CoolplayBaseModel> baseModels = new ArrayList<CoolplayBaseModel>();

            if (CollectionUtils.isNotEmpty(pageInfo.getList())) {
                List<Integer> baseIds = new ArrayList<Integer>();
                for (UserCollectModel tmpUserCollect : pageInfo.getList()) {
                    baseIds.add(tmpUserCollect.getCollectTypeId());
                }

                Map<Integer, CoolplayBaseModel> baseMap = coolplayBaseService.findMapByIds(baseIds);
                for (UserCollectModel tmpUserCollect : pageInfo.getList()) {
                    if (baseMap.get(tmpUserCollect.getCollectTypeId()) != null) {
                        baseModels.add(baseMap.get(tmpUserCollect.getCollectTypeId()));
                    }
                }
            }

            PageInfo<CoolplayBaseModel> basePageInfo = new PageInfo<CoolplayBaseModel>(baseModels);

            return ResponseUtil.success(PageConvertUtil.grid(basePageInfo));

        } catch(Exception e) {
            e.printStackTrace();

            return ResponseUtil.error("系统异常, 请稍后重试。");
        }
    }

    /**
     * 移除收藏
     *
     * @param collectTypeId
     * @param collectType
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/removeCollect", method = RequestMethod.POST)
    public Result removeCollect(@RequestParam("collectTypeId") Integer collectTypeId,
            @RequestParam("collectType") Integer collectType) {

        try {
            Integer currUserId = SecurityUtil.getCurrentUserId();
            int delCnt = userCollectService.delByUserIdAndCollectTypeInfo(currUserId, collectType, collectTypeId);

            return ResponseUtil.success();

        } catch(Exception e) {
            e.printStackTrace();

            return ResponseUtil.error("系统异常, 请稍后重试。");
        }
    }
}
