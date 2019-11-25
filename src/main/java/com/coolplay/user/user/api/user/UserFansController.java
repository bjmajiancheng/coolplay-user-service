package com.coolplay.user.user.api.user;

import com.coolplay.user.common.utils.PageConvertUtil;
import com.coolplay.user.common.utils.ResponseUtil;
import com.coolplay.user.common.utils.Result;
import com.coolplay.user.core.model.UserModel;
import com.coolplay.user.security.service.IUserService;
import com.coolplay.user.security.utils.SecurityUtil;
import com.coolplay.user.user.model.CircleModel;
import com.coolplay.user.user.model.UserFansModel;
import com.coolplay.user.user.service.ICircleService;
import com.coolplay.user.user.service.IUserFansService;
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
@RequestMapping("/api/user/userFans")
public class UserFansController {

    @Autowired
    private IUserFansService userFansService;

    @Autowired
    private IUserService userService;

    @Autowired
    private ICircleService circleService;

    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public Result list(@RequestBody UserFansModel userFansModel) {
        userFansModel.initPageInfo();

        try {
            PageInfo<UserFansModel> pageInfo = userFansService
                    .selectByFilterAndPage(userFansModel, userFansModel.getPageNum(), userFansModel.getPageSize());

            List<UserModel> fansUserModels = new ArrayList<UserModel>();
            if (CollectionUtils.isNotEmpty(pageInfo.getList())) {
                List<Integer> fansUserIds = new ArrayList<Integer>();

                for (UserFansModel tmpUserFans : pageInfo.getList()) {
                    fansUserIds.add(tmpUserFans.getFansUserId());
                }

                Map<Integer, UserModel> userMap = userService.findUserMapByUserIds(fansUserIds);
                for (UserFansModel tmpUserFans : pageInfo.getList()) {
                    UserModel userModel = userMap.get(tmpUserFans.getFansUserId());
                    if (userModel != null) {
                        fansUserModels.add(userModel);
                    }
                }
            }

            if (CollectionUtils.isNotEmpty(fansUserModels)) {

                Map<Integer, List<Integer>> followMap = userFansService
                        .getFollowMapByFansUserIds(Collections.singletonList(SecurityUtil.getCurrentUserId()));

                List<Integer> followUserIds = followMap.get(SecurityUtil.getCurrentUserId());
                for (UserModel fansUserModel : fansUserModels) {
                    if (followUserIds.contains(fansUserModel.getId())) {
                        fansUserModel.setIsFans(1);
                    }
                }
            }

            PageInfo<UserModel> fansUserPageInfo = new PageInfo<UserModel>(fansUserModels);
            return ResponseUtil.success(PageConvertUtil.grid(fansUserPageInfo));

        } catch(Exception e) {
            e.printStackTrace();

            return ResponseUtil.error("系统异常, 请稍后重试。");
        }
    }

    /**
     * 圈子加入邀请 粉丝列表
     *
     * @param userFansModel
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/circleInviteList", method = RequestMethod.POST)
    public Result circleInviteList(@RequestBody UserFansModel userFansModel) {
        userFansModel.initPageInfo();

        Integer circleId = userFansModel.getCircleId();

        try {
            PageInfo<UserFansModel> pageInfo = userFansService
                    .selectByFilterAndPage(userFansModel, userFansModel.getPageNum(), userFansModel.getPageSize());

            List<UserModel> fansUserModels = new ArrayList<UserModel>();

            Map<Integer, List<Integer>> circleMemberMap = circleService.getAdminOrMemberByIds(Collections.singletonList(circleId));
            List<Integer> circleMemberIds = circleMemberMap.get(circleId);

            if (CollectionUtils.isNotEmpty(pageInfo.getList())) {
                List<Integer> fansUserIds = new ArrayList<Integer>();

                for (UserFansModel tmpUserFans : pageInfo.getList()) {
                    fansUserIds.add(tmpUserFans.getFansUserId());
                }

                Map<Integer, UserModel> userMap = userService.findUserMapByUserIds(fansUserIds);
                for (UserFansModel tmpUserFans : pageInfo.getList()) {
                    UserModel userModel = userMap.get(tmpUserFans.getFansUserId());
                    if (userModel != null) {
                        fansUserModels.add(userModel);
                    }
                }
            }

            if (CollectionUtils.isNotEmpty(fansUserModels)) {

                Map<Integer, List<Integer>> followMap = userFansService
                        .getFollowMapByFansUserIds(Collections.singletonList(SecurityUtil.getCurrentUserId()));

                List<Integer> followUserIds = followMap.get(SecurityUtil.getCurrentUserId());
                for (UserModel fansUserModel : fansUserModels) {
                    if (followUserIds.contains(fansUserModel.getId())) {
                        fansUserModel.setIsFans(1);
                    }

                    if(circleMemberIds.contains(fansUserModel.getId())) {
                        fansUserModel.setIsCircleMember(1);
                    }
                }
            }

            PageInfo<UserModel> fansUserPageInfo = new PageInfo<UserModel>(fansUserModels);
            return ResponseUtil.success(PageConvertUtil.grid(fansUserPageInfo));

        } catch(Exception e) {
            e.printStackTrace();

            return ResponseUtil.error("系统异常, 请稍后重试。");
        }
    }

    /**
     * 移除粉丝信息
     *
     * @param userId
     * @param fansUserId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/removeFans", method = RequestMethod.POST)
    public Result removeFans(@RequestParam("userId") Integer userId, @RequestParam("fansUserId") Integer fansUserId) {
        if (userId != SecurityUtil.getCurrentUserId()) {
            return ResponseUtil.error("无权限修改其他用户信息");
        }

        try {
            int delCnt = userFansService.delByUserIdAndFansUserId(userId, fansUserId);

            return ResponseUtil.success();

        } catch(Exception e) {
            e.printStackTrace();

            return ResponseUtil.error("系统异常, 请稍后重试。");
        }
    }

    /**
     * 关注账号信息
     *
     * @param userId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/followUser", method = RequestMethod.POST)
    public Result followUser(@RequestParam("userId") Integer userId) {
        if (userId == SecurityUtil.getCurrentUserId()) {
            return ResponseUtil.error("无法关注自己账号");
        }

        try {
            UserFansModel userFansModel = new UserFansModel();
            userFansModel.setUserId(userId);
            userFansModel.setFansUserId(SecurityUtil.getCurrentUserId());

            int saveCnt = userFansService.saveNotNull(userFansModel);

            return ResponseUtil.success();

        } catch(Exception e) {
            e.printStackTrace();

            return ResponseUtil.error("系统异常, 请稍后重试。");
        }
    }

    /**
     * 关注账号信息
     *
     * @param userId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/disableFollowUser", method = RequestMethod.POST)
    public Result disableFollowUser(@RequestParam("userId") Integer userId) {

        try {
            int delCnt = userFansService.delByUserIdAndFansUserId(userId, SecurityUtil.getCurrentUserId());

            return ResponseUtil.success();

        } catch(Exception e) {
            e.printStackTrace();

            return ResponseUtil.error("系统异常, 请稍后重试。");
        }
    }

    /**
     * （我的关注/TA的关注）列表页
     *
     * @param userFansModel
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/followList", method = RequestMethod.POST)
    public Result followList(@RequestBody UserFansModel userFansModel) {

        try {
            userFansModel.initPageInfo();
            PageInfo<UserFansModel> pageInfo = userFansService
                    .selectByFilterAndPage(userFansModel, userFansModel.getPageNum(), userFansModel.getPageSize());

            List<UserModel> userModels = new ArrayList<UserModel>();
            if (CollectionUtils.isNotEmpty(pageInfo.getList())) {
                List<Integer> userIds = new ArrayList<Integer>();

                for (UserFansModel tmpUserFans : pageInfo.getList()) {
                    userIds.add(tmpUserFans.getUserId());
                }

                Map<Integer, UserModel> userMap = userService.findUserMapByUserIds(userIds);
                for (UserFansModel tmpUserFans : pageInfo.getList()) {
                    UserModel userModel = userMap.get(tmpUserFans.getUserId());
                    if (userModel != null) {
                        userModels.add(userModel);
                    }
                }
            }

            if (CollectionUtils.isNotEmpty(userModels)) {

                Map<Integer, List<Integer>> followMap = userFansService
                        .getFollowMapByFansUserIds(Collections.singletonList(SecurityUtil.getCurrentUserId()));

                List<Integer> followUserIds = followMap.get(SecurityUtil.getCurrentUserId());
                for (UserModel userModel : userModels) {
                    if (followUserIds.contains(userModel.getId())) {
                        userModel.setIsFans(1);
                    }
                }
            }

            PageInfo<UserModel> userPageInfo = new PageInfo<UserModel>(userModels);
            return ResponseUtil.success(PageConvertUtil.grid(userPageInfo));

        } catch(Exception e) {
            e.printStackTrace();

            return ResponseUtil.error("系统异常, 请稍后重试。");
        }
    }
}
