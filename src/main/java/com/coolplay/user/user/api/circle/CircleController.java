package com.coolplay.user.user.api.circle;

import com.coolplay.user.common.utils.PageConvertUtil;
import com.coolplay.user.common.utils.ResponseUtil;
import com.coolplay.user.common.utils.Result;
import com.coolplay.user.core.model.UserModel;
import com.coolplay.user.security.utils.SecurityUtil;
import com.coolplay.user.user.model.*;
import com.coolplay.user.user.service.*;
import com.coolplay.user.security.service.IUserService;
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
 * Created by majiancheng on 2019/10/23.
 */
@Controller
@RequestMapping("/api/circle/circle")
public class CircleController {

    @Autowired
    private ICircleService circleService;

    @Autowired
    private IPostService postService;

    @Autowired
    private IUserService userService;

    @Autowired
    private ILabelService labelService;

    @Autowired
    private ICircleMemberService circleMemberService;

    @Autowired
    private ICircleAdminService circleAdminService;

    @Autowired
    private IUserCollectService userCollectService;

    @Autowired
    private ICircleMemberReviewService circleMemberReviewService;

    @ResponseBody
    @RequestMapping(value = "/list", method= RequestMethod.POST)
    public Result list(CircleModel circleModel,
            @RequestParam(value = "pageNum", required = false, defaultValue = "1") int pageNum,
            @RequestParam(value = "pageSize", required = false, defaultValue = "15") int pageSize) {
        circleModel.setDisabled(0);
        circleModel.setReviewStatus(1);
        circleModel.setStatus(1);
        circleModel.setCircleType(1);
        PageInfo<CircleModel> pageInfo = circleService.selectByFilterAndPage(circleModel, pageNum, pageSize);

        List<CircleModel> circleModels = pageInfo.getList();
        if(CollectionUtils.isNotEmpty(circleModels)) {
            List<Integer> userIds = new ArrayList<Integer>();
            List<Integer> circleIds = new ArrayList<Integer>();
            for(CircleModel tmpCircleModel : circleModels) {
                if(userIds.contains(tmpCircleModel.getUserId())) {
                    userIds.add(tmpCircleModel.getUserId());
                }

                circleIds.add(tmpCircleModel.getId());
            }

            Map<Integer, UserModel> userMap = userService.findUserMapByUserIds(userIds);
            Map<Integer, List<LabelModel>> labelMap = labelService.findMapByCircleIds(circleIds);
            Map<Integer, List<Integer>> circleMemberUserIdMap = circleMemberService.findMapByCircleIds(circleIds);
            List<Integer> circleMemberCircleIds = circleMemberService.findCircleIdsByUserIdAndCircleIds(SecurityUtil.getCurrentUserId(), circleIds);
            Map<Integer, Integer> reviewMemberCntMap = circleMemberService.findReviewMemberCntByCircleIds(circleIds);

            List<Integer> userCollectCircleIds = userCollectService
                    .findCircleIdsByUserIdAndCircleIds(SecurityUtil.getCurrentUserId(), circleIds);


            for(CircleModel tmpCircleModel : circleModels) {
                UserModel userModel = userMap.get(tmpCircleModel.getUserId());
                if(userModel != null) {
                    tmpCircleModel.setNickName(userModel.getNickName());
                    tmpCircleModel.setHeadImage(userModel.getHeadImage());
                }

                tmpCircleModel.setLabelList(labelMap.get(tmpCircleModel.getId()));

                if(circleMemberCircleIds.contains(tmpCircleModel.getId())) {
                    tmpCircleModel.setIsMember(1);
                }

                if(userCollectCircleIds.contains(tmpCircleModel.getId())) {
                    tmpCircleModel.setIsCollect(1);
                }

                Integer reviewMemberCnt = reviewMemberCntMap.get(tmpCircleModel.getId());
                if(reviewMemberCnt != null) {
                    tmpCircleModel.setReviewMemberCnt(reviewMemberCnt);
                }
            }
        }

        return ResponseUtil.success(PageConvertUtil.grid(pageInfo));
    }

    /**
     * 收藏/取消收藏
     *
     * @param id
     * @param type
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/collectCircle", method=RequestMethod.POST)
    public Result collectCircle(@RequestParam("id")Integer id, @RequestParam("type")Integer type) {

        if(type == 1) {
            UserCollectModel userCollectModel = new UserCollectModel();
            userCollectModel.setCollectType(1);
            userCollectModel.setCollectTypeId(id);
            userCollectModel.setUserId(SecurityUtil.getCurrentUserId());
            userCollectModel.setIsDel(0);

            if(CollectionUtils.isEmpty(userCollectService.selectByFilter(userCollectModel))) {
                int saveCnt = userCollectService.saveNotNull(userCollectModel);
            }
        } else if(type == 2) {
            int delCnt = userCollectService.delByUserIdAndCollectTypeInfo(SecurityUtil.getCurrentUserId(), 1, id);
        }


        Integer collectCnt = userCollectService.findCntByCollectTypeAndCollectTypeId(1, id);

        return ResponseUtil.success(Collections.singletonMap("collectCnt", collectCnt));
    }

    /**
     * 申请加入圈子
     *
     * @param id
     * @param applicationReason
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/applicationCircle", method = RequestMethod.POST)
    public Result applicationCircle(@RequestParam("id") Integer id, @RequestParam("applicationReason") String applicationReason) {
        Integer currUserId = SecurityUtil.getCurrentUserId();

        CircleMemberModel circleMemberModel = new CircleMemberModel();
        circleMemberModel.setCircleId(id);
        circleMemberModel.setMemberUserId(currUserId);
        circleMemberModel.setReviewStatus(0);
        circleMemberModel.setStatus(0);

        Integer saveCnt = circleMemberService.saveNotNull(circleMemberModel);

        CircleMemberReviewModel circleMemberReviewModel = new CircleMemberReviewModel();
        circleMemberReviewModel.setCircleId(id);
        circleMemberReviewModel.setApplicationReason(applicationReason);
        circleMemberReviewModel.setMemberUserId(currUserId);
        circleMemberReviewModel.setReviewStatus(0);

        saveCnt = circleMemberReviewService.saveNotNull(circleMemberReviewModel);

        return ResponseUtil.success("申请加入圈子成功");
    }
}
