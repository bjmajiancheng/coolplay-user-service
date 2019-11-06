package com.coolplay.user.user.api.circle;

import com.alibaba.fastjson.JSON;
import com.coolplay.user.common.utils.PageConvertUtil;
import com.coolplay.user.common.utils.ResponseUtil;
import com.coolplay.user.common.utils.Result;
import com.coolplay.user.core.model.UserModel;
import com.coolplay.user.security.utils.SecurityUtil;
import com.coolplay.user.user.dto.CircleUserDto;
import com.coolplay.user.user.model.*;
import com.coolplay.user.user.service.*;
import com.coolplay.user.security.service.IUserService;
import com.github.pagehelper.PageInfo;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;

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

    @Autowired
    private ICircleLabelService circleLabelService;

    @Autowired
    private ICirclePublicService circlePublicService;

    @Autowired
    private ICirclePostService circlePostService;

    @Autowired
    private ICompanyCircleService companyCircleService;

    @Autowired
    private IMessageService messageService;

    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public Result list(@RequestBody CircleModel circleModel) {

        try {
            circleModel.setDisabled(0);
            circleModel.setReviewStatus(1);
            circleModel.setStatus(1);
            circleModel.setCircleType(1);
            circleModel.initPageInfo();
            PageInfo<CircleModel> pageInfo = circleService
                    .selectByFilterAndPage(circleModel, circleModel.getPageNum(), circleModel.getPageSize());

            List<CircleModel> circleModels = pageInfo.getList();
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
                Map<Integer, List<Integer>> circleMemberUserIdMap = circleMemberService.findMapByCircleIds(circleIds);
                List<Integer> circleMemberCircleIds = circleMemberService
                        .findCircleIdsByUserIdAndCircleIds(SecurityUtil.getCurrentUserId(), circleIds);
                Map<Integer, Integer> reviewMemberCntMap = circleMemberService
                        .findReviewMemberCntByCircleIds(circleIds);

                List<Integer> userCollectCircleIds = userCollectService
                        .findCircleIdsByUserIdAndCircleIds(SecurityUtil.getCurrentUserId(), circleIds);

                for (CircleModel tmpCircleModel : circleModels) {
                    UserModel userModel = userMap.get(tmpCircleModel.getUserId());
                    if (userModel != null) {
                        tmpCircleModel.setNickName(userModel.getNickName());
                        tmpCircleModel.setHeadImage(userModel.getHeadImage());
                    }

                    if (CollectionUtils.isNotEmpty(labelMap.get(tmpCircleModel.getId()))) {
                        tmpCircleModel.setLabelList(labelMap.get(tmpCircleModel.getId()));
                    }

                    if (circleMemberCircleIds.contains(tmpCircleModel.getId())) {
                        tmpCircleModel.setIsMember(1);
                    }

                    if (userCollectCircleIds.contains(tmpCircleModel.getId())) {
                        tmpCircleModel.setIsCollect(1);
                    }

                    Integer reviewMemberCnt = reviewMemberCntMap.get(tmpCircleModel.getId());
                    if (reviewMemberCnt != null) {
                        tmpCircleModel.setReviewMemberCnt(reviewMemberCnt);
                    }
                }
            }

            return ResponseUtil.success(PageConvertUtil.grid(pageInfo));

        } catch (Exception e) {
            e.printStackTrace();

            return ResponseUtil.error("系统异常, 请稍后重试。");
        }
    }

    /**
     * 收藏/取消收藏
     *
     * @param id
     * @param type
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/collectCircle", method = RequestMethod.POST)
    public Result collectCircle(@RequestParam("id") Integer id, @RequestParam("type") Integer type) {

        try {
            if (type == 1) {
                UserCollectModel userCollectModel = new UserCollectModel();
                userCollectModel.setCollectType(1);
                userCollectModel.setCollectTypeId(id);
                userCollectModel.setUserId(SecurityUtil.getCurrentUserId());
                userCollectModel.setIsDel(0);

                if (CollectionUtils.isEmpty(userCollectService.selectByFilter(userCollectModel))) {
                    int saveCnt = userCollectService.saveNotNull(userCollectModel);
                }
            } else if (type == 2) {
                int delCnt = userCollectService.delByUserIdAndCollectTypeInfo(SecurityUtil.getCurrentUserId(), 1, id);
            }

            Integer collectCnt = userCollectService.findCntByCollectTypeAndCollectTypeId(1, id);

            return ResponseUtil.success(Collections.singletonMap("collectCnt", collectCnt));

        } catch (Exception e) {
            e.printStackTrace();

            return ResponseUtil.error("系统异常, 请稍后重试。");
        }
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
    public Result applicationCircle(@RequestParam("id") Integer id,
            @RequestParam("applicationReason") String applicationReason) {

        try {
            CircleModel circleModel = circleService.findById(id);
            if (circleModel.getCircleType() == 2) {
                return ResponseUtil.error("圈子为私密圈, 不能申请加入, 只能被成员邀请");
            }

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

        } catch (Exception e) {
            e.printStackTrace();

            return ResponseUtil.error("系统异常, 请稍后重试。");
        }
    }

    /**
     * 建立圈子基础信息
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getBaseInfo", method = RequestMethod.POST)
    public Result getBaseInfo() {

        try {

            LabelModel labelModel = new LabelModel();
            labelModel.setStatus(1);
            labelModel.setIsDel(0);
            List<LabelModel> labelList = labelService.selectByFilter(labelModel);

            return ResponseUtil.success(Collections.singletonMap("labelList", labelList));

        } catch (Exception e) {
            e.printStackTrace();

            return ResponseUtil.error("系统异常, 请稍后重试。");
        }
    }

    @ResponseBody
    @RequestMapping(value = "/saveCircle", method = RequestMethod.POST)
    public Result saveCircle(@RequestBody CircleModel circleModel) {

        try {
            if (circleModel == null) {
                return ResponseUtil.error("系统错误, 请确认是否传递参数信息");
            }

            circleModel.setUserId(SecurityUtil.getCurrentUserId());
            circleModel.setApplicationTime(new Date());
            circleModel.setReviewStatus(0);
            circleModel.setStatus(0);

            int saveCnt = circleService.saveNotNull(circleModel);

            if (CollectionUtils.isNotEmpty(circleModel.getLabelIds())) {
                List<Integer> labelIds = circleModel.getLabelIds();

                for (Integer labelId : labelIds) {
                    CircleLabelModel circleLabelModel = new CircleLabelModel();
                    circleLabelModel.setLabelId(labelId);
                    circleLabelModel.setCircleId(circleModel.getId());

                    circleLabelService.saveNotNull(circleLabelModel);
                }
            }

            return ResponseUtil.success();

        } catch (Exception e) {
            e.printStackTrace();

            return ResponseUtil.error("系统异常, 请稍后重试。");
        }
    }

    @ResponseBody
    @RequestMapping(value = "/circleInfo", method = RequestMethod.POST)
    public Result circleInfo(@RequestParam("id") Integer id) {

        try {

            CircleModel circleModel = circleService.findById(id);

            Map<Integer, List<LabelModel>> circleLabelMap = labelService
                    .findMapByCircleIds(Collections.singletonList(circleModel.getId()));

            if (CollectionUtils.isNotEmpty(circleLabelMap.get(circleModel.getId()))) {
                circleModel.setLabelList(circleLabelMap.get(circleModel.getId()));
            }

            return ResponseUtil.success(circleModel);

        } catch (Exception e) {
            e.printStackTrace();

            return ResponseUtil.error("系统异常, 请稍后重试。");
        }
    }

    /**
     * 更新圈子信息
     *
     * @param updateCircleModel
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/updateCircle", method = RequestMethod.POST)
    public Result updateCircle(@RequestBody CircleModel updateCircleModel) {

        try {
            CircleModel circleModel = circleService.findById(updateCircleModel.getId());
            if (circleModel.getUserId() != SecurityUtil.getCurrentUserId()) {
                return ResponseUtil.error("当前用户不是圈主, 无法修改圈子信息");
            }

            if (StringUtils.isNotEmpty(updateCircleModel.getPublicContent())) {
                CirclePublicModel circlePublicModel = new CirclePublicModel();

                circlePublicModel.setCircleId(updateCircleModel.getId());
                circlePublicModel.setPublicContent(updateCircleModel.getPublicContent());
                int saveCnt = circlePublicService.saveNotNull(circlePublicModel);
            }

            int updateCnt = circleService.updateNotNull(updateCircleModel);

            int delCnt = circleLabelService.delByCircleId(updateCircleModel.getId());
            if (CollectionUtils.isNotEmpty(updateCircleModel.getLabelIds())) {
                List<Integer> labelIds = updateCircleModel.getLabelIds();

                for (Integer labelId : labelIds) {
                    CircleLabelModel circleLabelModel = new CircleLabelModel();
                    circleLabelModel.setLabelId(labelId);
                    circleLabelModel.setCircleId(circleModel.getId());

                    circleLabelService.saveNotNull(circleLabelModel);
                }
            }

            return ResponseUtil.success();

        } catch (Exception e) {
            e.printStackTrace();

            return ResponseUtil.error("系统异常, 请稍后重试。");
        }
    }

    /**
     * 圈子明细信息
     *
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/circleDetail", method = RequestMethod.POST)
    public Result circleDetail(@RequestParam("id") Integer id) {

        try {
            CircleModel circleModel = circleService.findById(id);

            Map<Integer, List<LabelModel>> circleLabelMap = labelService
                    .findMapByCircleIds(Collections.singletonList(circleModel.getId()));

            if (CollectionUtils.isNotEmpty(circleLabelMap.get(circleModel.getId()))) {
                circleModel.setLabelList(circleLabelMap.get(circleModel.getId()));
            }

            List<CirclePublicModel> circlePublics = circlePublicService.find(Collections.singletonMap("circleId", id));
            if (CollectionUtils.isNotEmpty(circlePublics)) {
                circleModel.setCirclePublics(circlePublics);
            }

            Integer postCnt = circlePostService.findPostCntByCircleId(id);
            circleModel.setPostCnt(postCnt);

            List<PostModel> circlePosts = postService.findByCircleId(id);
            if (CollectionUtils.isNotEmpty(circlePosts)) {
                circleModel.setCirclePosts(circlePosts);
            }

            return ResponseUtil.success(circleModel);

        } catch (Exception e) {
            e.printStackTrace();

            return ResponseUtil.error("系统异常, 请稍后重试。");
        }
    }

    /**
     * 当前圈子用户信息, 分为三个角色, 圈主、管理员和成员
     *
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/circleUsers", method = RequestMethod.POST)
    public Result circleUsers(@RequestParam("id") Integer id) {

        try {
            //获取圈子信息
            CircleModel circleModel = circleService.findById(id);
            //获取圈子管理员信息
            List<CircleAdminModel> circleAdminModels = circleAdminService
                    .find(Collections.singletonMap("circleId", id));
            //获取圈子成员信息
            CircleMemberModel circleMemberModel = new CircleMemberModel();
            circleMemberModel.setCircleId(id);
            circleMemberModel.setReviewStatus(1);
            circleMemberModel.setStatus(1);

            List<CircleMemberModel> circleMemberModels = circleMemberService.selectByFilter(circleMemberModel);
            List<Integer> userIds = new ArrayList<Integer>();

            List<CircleUserDto> circleUserDtos = new ArrayList<CircleUserDto>();
            circleUserDtos.add(new CircleUserDto(id, circleModel.getUserId(), 1, 0));
            userIds.add(circleModel.getUserId());

            if (CollectionUtils.isNotEmpty(circleAdminModels)) {
                for (CircleAdminModel circleAdminModel : circleAdminModels) {
                    if (!userIds.contains(circleAdminModel.getAdminUserId())) {
                        userIds.add(circleAdminModel.getAdminUserId());
                        circleUserDtos.add(new CircleUserDto(id, circleAdminModel.getAdminUserId(), 0, 1));
                    }
                }
            }

            if (CollectionUtils.isNotEmpty(circleMemberModels)) {
                for (CircleMemberModel tmpCircleMember : circleMemberModels) {
                    if (!userIds.contains(tmpCircleMember.getMemberUserId())) {
                        userIds.add(tmpCircleMember.getMemberUserId());
                        circleUserDtos.add(new CircleUserDto(id, tmpCircleMember.getMemberUserId(), 0, 0));
                    }
                }
            }

            Map<Integer, UserModel> userMap = userService.findUserMapByUserIds(userIds);
            if (CollectionUtils.isNotEmpty(circleUserDtos)) {
                for (CircleUserDto circleUserDto : circleUserDtos) {
                    UserModel userModel = userMap.get(circleUserDto.getUserId());
                    if (userModel != null) {
                        circleUserDto.setNickName(userModel.getNickName());
                        circleUserDto.setHeadImage(userModel.getHeadImage());
                    }
                }
            }

            return ResponseUtil.success(Collections.singletonMap("circleUsers", circleUserDtos));

        } catch (Exception e) {
            e.printStackTrace();

            return ResponseUtil.error("系统异常, 请稍后重试。");
        }
    }

    /**
     * 圈子转让提交
     *
     * @param id
     * @param userId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/transferUser", method = RequestMethod.POST)
    public Result transferUser(@RequestParam("id") Integer id, @RequestParam("userId") Integer userId) {

        try {
            CircleModel circleModel = new CircleModel();
            circleModel.setId(id);
            circleModel.setUserId(userId);

            int updateCnt = circleService.updateNotNull(circleModel);

            return ResponseUtil.success();

        } catch (Exception e) {
            e.printStackTrace();

            return ResponseUtil.error("系统异常, 请稍后重试。");
        }
    }

    /**
     * 管理员设置
     *
     * @param id
     * @param adminUserIds
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/transferAdminUser", method = RequestMethod.POST)
    public Result transferAdminUser(@RequestParam("id") Integer id,
            @RequestParam("adminUserIds") List<Integer> adminUserIds) {

        try {
            int delCnt = circleAdminService.delByCircleId(id);

            if (CollectionUtils.isNotEmpty(adminUserIds)) {
                for (Integer adminUserId : adminUserIds) {
                    CircleAdminModel circleAdminModel = new CircleAdminModel();
                    circleAdminModel.setCircleId(id);
                    circleAdminModel.setAdminUserId(adminUserId);

                    circleAdminService.saveNotNull(circleAdminModel);
                }
            }

            return ResponseUtil.success();

        } catch (Exception e) {
            e.printStackTrace();

            return ResponseUtil.error("系统异常, 请稍后重试。");
        }
    }

    /**
     * 邀请加入圈子
     *
     * @param id
     * @param userId
     * @param applicationReason
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/inviteCircle", method = RequestMethod.POST)
    public Result inviteCircle(@RequestParam("id") Integer id, @RequestParam("userId") Integer userId,
            @RequestParam("applicationReason") String applicationReason) {

        try {
            CircleModel circleModel = circleService.findById(id);

            CircleMemberReviewModel circleMemberReviewModel = new CircleMemberReviewModel();
            circleMemberReviewModel.setCircleId(id);
            circleMemberReviewModel.setInviteUserId(SecurityUtil.getCurrentUserId());
            circleMemberReviewModel.setMemberUserId(userId);
            circleMemberReviewModel.setReviewStatus(0);
            circleMemberReviewModel.setApplicationReason(applicationReason);

            List<CircleAdminModel> circleAdmins = circleAdminService.findByCircleId(id);
            List<Integer> circleAdminUserIds = new ArrayList<Integer>();
            for (CircleAdminModel circleAdmin : circleAdmins) {
                if (!circleAdminUserIds.contains(circleAdmin.getAdminUserId())) {
                    circleAdminUserIds.add(circleAdmin.getAdminUserId());
                }
            }
            if (!circleAdminUserIds.contains(circleModel.getUserId())) {
                circleAdminUserIds.add(circleModel.getUserId());
            }

            Map<Integer, UserModel> userModelMap = userService
                    .findUserMapByUserIds(Arrays.asList(new Integer[] { SecurityUtil.getCurrentUserId(), userId }));

            UserModel inviteUserModel = userModelMap.get(SecurityUtil.getCurrentUserId());
            UserModel memberUserModel = userModelMap.get(userId);
            String inviteUserNickName = inviteUserModel == null ? "" : inviteUserModel.getNickName();
            String memberUserNickName = memberUserModel == null ? "" : memberUserModel.getNickName();

            for (Integer adminUserId : circleAdminUserIds) {
                MessageModel messageModel = new MessageModel();
                messageModel.setUserId(adminUserId);
                messageModel.setMessageName("加入" + circleModel.getCircleName() + "圈申请");
                messageModel.setMessageContent(inviteUserNickName + "邀请"+ memberUserNickName +"加入" + circleModel.getCircleName() + "圈，是否同意");
                messageModel.setMessageType(1);

                int saveCnt = messageService.saveNotNull(messageModel);
            }

            int saveCnt = circleMemberReviewService.saveNotNull(circleMemberReviewModel);

            return ResponseUtil.success();

        } catch (Exception e) {
            e.printStackTrace();

            return ResponseUtil.error("系统异常, 请稍后重试。");
        }
    }

    /**
     * 关联企业提交
     *
     * @param id
     * @param companyId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/updateCircleCompany", method = RequestMethod.POST)
    public Result updateCircleCompany(@RequestParam("id") Integer id, @RequestParam("companyId") Integer companyId) {

        try {
            CompanyCircleModel companyCircleModel = new CompanyCircleModel();
            companyCircleModel.setCircleId(id);
            companyCircleModel.setCompanyId(companyId);
            companyCircleModel.setApplicationTime(new Date());
            companyCircleModel.setApplicationUserId(SecurityUtil.getCurrentUserId());
            companyCircleModel.setReviewStatus(0);
            companyCircleModel.setStatus(0);

            int saveCnt = companyCircleService.saveNotNull(companyCircleModel);

            return ResponseUtil.success();

        } catch (Exception e) {
            e.printStackTrace();

            return ResponseUtil.error("系统异常, 请稍后重试。");
        }
    }

    /**
     * 圈子历史公告列表
     *
     * @param circlePublicModel
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/circlePublics", method = RequestMethod.POST)
    public Result circlePublics(@RequestBody CirclePublicModel circlePublicModel) {

        try {
            circlePublicModel.setSort_("c_time_desc");
            circlePublicModel.initPageInfo();
            PageInfo<CirclePublicModel> pageInfo = circlePublicService
                    .selectByFilterAndPage(circlePublicModel, circlePublicModel.getPageNum(),
                            circlePublicModel.getPageSize());

            return ResponseUtil.success(PageConvertUtil.grid(pageInfo));

        } catch (Exception e) {
            e.printStackTrace();

            return ResponseUtil.error("系统异常, 请稍后重试。");
        }
    }

    @ResponseBody
    @RequestMapping(value = "/myCircleList", method = RequestMethod.POST)
    public Result myCircleList(@RequestBody CircleModel circleModel) {

        try {
            Integer currUserId = SecurityUtil.getCurrentUserId();

            circleModel.setReviewStatus(1);
            circleModel.setStatus(1);
            circleModel.setDisabled(0);
            PageInfo<CircleModel> pageInfo = new PageInfo<CircleModel>();
            circleModel.initPageInfo();
            if (circleModel.getType() == 1) {
                List<Integer> memberUserIds = new ArrayList<Integer>();
                List<Integer> circleIds = circleMemberService.findByMemberUserId(currUserId);
                if (CollectionUtils.isEmpty(circleIds)) {
                    circleIds = Collections.singletonList(0);
                }
                circleModel.setIds(circleIds);

                pageInfo = circleService
                        .selectByFilterAndPage(circleModel, circleModel.getPageNum(), circleModel.getPageSize());

            } else if (circleModel.getType() == 2) {
                circleModel.setUserId(currUserId);
                pageInfo = circleService
                        .selectByFilterAndPage(circleModel, circleModel.getPageNum(), circleModel.getPageSize());
            }

            List<CircleModel> circleModels = pageInfo.getList();
            if (CollectionUtils.isNotEmpty(circleModels)) {
                List<Integer> userIds = new ArrayList<Integer>();
                List<Integer> circleIds = new ArrayList<Integer>();

                for (CircleModel tmpCircle : circleModels) {
                    if (!userIds.contains(tmpCircle.getUserId())) {
                        userIds.add(tmpCircle.getUserId());
                    }
                    circleIds.add(tmpCircle.getUserId());
                }

                Map<Integer, UserModel> userMap = userService.findUserMapByUserIds(userIds);
                Map<Integer, List<LabelModel>> labelMap = labelService.findMapByCircleIds(circleIds);
                for (CircleModel tmpCircle : circleModels) {
                    UserModel userModel = userMap.get(tmpCircle.getUserId());
                    if (userModel != null) {
                        tmpCircle.setNickName(userModel.getNickName());
                        tmpCircle.setHeadImage(userModel.getHeadImage());
                    }
                    if (tmpCircle.getUserId() == currUserId) {
                        tmpCircle.setIsOwner(1);
                    }
                    if (CollectionUtils.isNotEmpty(labelMap.get(tmpCircle.getId()))) {
                        tmpCircle.setLabelList(labelMap.get(tmpCircle.getId()));
                    }
                }
            }

            return ResponseUtil.success(PageConvertUtil.grid(pageInfo));

        } catch (Exception e) {
            e.printStackTrace();

            return ResponseUtil.error("系统异常, 请稍后重试。");
        }
    }

}
