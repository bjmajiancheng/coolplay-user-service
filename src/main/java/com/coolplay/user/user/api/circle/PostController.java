package com.coolplay.user.user.api.circle;

import com.alibaba.fastjson.JSON;
import com.coolplay.user.common.utils.PageConvertUtil;
import com.coolplay.user.common.utils.ResponseUtil;
import com.coolplay.user.common.utils.Result;
import com.coolplay.user.core.model.UserModel;
import com.coolplay.user.security.security.SecurityUser;
import com.coolplay.user.security.service.IUserService;
import com.coolplay.user.security.utils.SecurityUtil;
import com.coolplay.user.user.model.*;
import com.coolplay.user.user.service.*;
import com.github.pagehelper.PageInfo;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * Created by majiancheng on 2019/10/21.
 */
@Controller
@RequestMapping("/api/circle/post")
public class PostController {

    @Autowired
    private IPostService postService;

    @Autowired
    private IPostLabelService postLabelService;

    @Autowired
    private ILabelService labelService;

    @Autowired
    private IUserCollectService userCollectService;

    @Autowired
    private IUserService userService;

    @Autowired
    private IUserLikeService userLikeService;

    @Autowired
    private ICircleService circleService;

    @Autowired
    private ICircleMemberService circleMemberService;

    @Autowired
    private ICircleAdminService circleAdminService;

    @Autowired
    private ICirclePostService circlePostService;

    @Autowired
    private IPostCommentService postCommentService;

    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public Result list(@RequestBody PostModel postModel) {
        postModel.initPageInfo();

        try {
            PageInfo<PostModel> pageInfo = this.postService.selectByFilterAndPage(postModel, postModel.getPageNum(), postModel.getPageSize());
            if (CollectionUtils.isNotEmpty(pageInfo.getList())) {
                List<Integer> postIds = new ArrayList<Integer>();
                List<Integer> userIds = new ArrayList<Integer>();
                for (PostModel tmpPostModel : pageInfo.getList()) {
                    postIds.add(tmpPostModel.getId());
                    userIds.add(tmpPostModel.getUserId());
                }

                Map<Integer, List<LabelModel>> labelMap = labelService.findMapByPostIds(postIds);
                Map<Integer, UserModel> userMap = userService.findUserMapByUserIds(userIds);
                List<Integer> userLikePostIds = userLikeService
                        .findPostIdsByUserIdAndPostIds(SecurityUtil.getCurrentUserId(), postIds);
                List<Integer> userCollectPostIds = userCollectService
                        .findPostIdsByUserIdAndPostIds(SecurityUtil.getCurrentUserId(), postIds);

                for (PostModel tmpPostModel : pageInfo.getList()) {
                    if(CollectionUtils.isNotEmpty(labelMap.get(tmpPostModel.getId()))) {
                        tmpPostModel.setLabelList(labelMap.get(tmpPostModel.getId()));
                    }
                    UserModel userModel = userMap.get(tmpPostModel.getUserId());
                    if (userModel != null) {
                        tmpPostModel.setNickName(userModel.getNickName());
                        tmpPostModel.setHeadImage(userModel.getHeadImage());
                    }
                    if (userLikePostIds.contains(tmpPostModel.getId())) {
                        tmpPostModel.setIsLike(1);
                    }
                    if (userCollectPostIds.contains(tmpPostModel.getId())) {
                        tmpPostModel.setIsCollect(1);
                    }
                }
            }

            return ResponseUtil.success(PageConvertUtil.grid(pageInfo));

        } catch(Exception e) {
            e.printStackTrace();

            return ResponseUtil.error("系统异常, 请稍后重试。");
        }
    }

    @ResponseBody
    @RequestMapping(value = "/collectPost", method = RequestMethod.POST)
    public Result collectPost(@RequestParam("id") Integer id, @RequestParam("type") Integer type) {

        try {
            if(type == 1) {
                UserCollectModel userCollectModel = new UserCollectModel();
                userCollectModel.setCollectType(2);
                userCollectModel.setCollectTypeId(id);
                userCollectModel.setUserId(SecurityUtil.getCurrentUserId());
                userCollectModel.setIsDel(0);


                if(CollectionUtils.isEmpty(userCollectService.selectByFilter(userCollectModel))) {
                    int saveCnt = userCollectService.saveNotNull(userCollectModel);
                }
            } else if(type == 2) {
                int delCnt = userCollectService.delByUserIdAndCollectTypeInfo(SecurityUtil.getCurrentUserId(), 2, id);
            }

            int collectCnt = userCollectService.findCntByCollectTypeAndCollectTypeId(2, id);
            PostModel postModel = new PostModel();
            postModel.setCollectCnt(collectCnt);

            postService.updateNotNull(postModel);

            return ResponseUtil.success(Collections.singletonMap("collectCnt", collectCnt));

        } catch(Exception e) {
            e.printStackTrace();

            return ResponseUtil.error("系统异常, 请稍后重试。");
        }
    }

    @ResponseBody
    @RequestMapping(value = "/postDetail", method = RequestMethod.POST)
    public Result postDetail(@RequestParam("id") Integer id) {

        try {
            return this.detail(id);
        } catch(Exception e) {
            e.printStackTrace();

            return ResponseUtil.error("系统异常, 请稍后重试。");
        }
    }

    @ResponseBody
    @RequestMapping(value = "/likePost", method = RequestMethod.POST)
    public Result likePost(@RequestParam("id") Integer id, @RequestParam("type")Integer type) {

        try {
            UserLikeModel userLikeModel = new UserLikeModel();
            userLikeModel.setCollectType(2);
            userLikeModel.setCollectTypeId(id);
            userLikeModel.setUserId(SecurityUtil.getCurrentUserId());
            userLikeModel.setIsDel(0);

            if(type == 1) {
                int updateCnt = postService.columnPlusNumber(id, "like_cnt", 1);

                if(CollectionUtils.isEmpty(userLikeService.selectByFilter(userLikeModel))) {
                    int saveCnt = userLikeService.saveNotNull(userLikeModel);
                }
            } else if(type == 2){
                int updateCnt = postService.columnPlusNumber(id, "like_cnt", -1);
                List<UserLikeModel> userLikeModels = userLikeService.selectByFilter(userLikeModel);
                if(CollectionUtils.isNotEmpty(userLikeModels)) {
                    for(UserLikeModel tmpUserLikeModel : userLikeModels) {
                        tmpUserLikeModel.setIsDel(1);
                        userLikeService.updateNotNull(tmpUserLikeModel);
                    }
                }
            }

            PostModel postModel = postService.findById(id);
            return ResponseUtil.success(Collections.singletonMap("likeCnt", postModel.getLikeCnt()));

        } catch(Exception e) {
            e.printStackTrace();

            return ResponseUtil.error("系统异常, 请稍后重试。");
        }
    }

    /**
     * 帖子页基础信息
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/postBaseInfo", method = RequestMethod.POST)
    public Result postBaseInfo() {

        try {
            List<Integer> allCircleIds = new ArrayList<Integer>();
            List<Integer> circleIds = circleMemberService.findByMemberUserId(SecurityUtil.getCurrentUserId());
            if (CollectionUtils.isNotEmpty(circleIds)) {
                allCircleIds.addAll(circleIds);
            }

            circleIds = circleAdminService.findByAdminUserId(SecurityUtil.getCurrentUserId());
            if (CollectionUtils.isNotEmpty(circleIds)) {
                allCircleIds.addAll(circleIds);
            }

            List<CircleModel> circleModels = circleService.findByIds(allCircleIds);

            List<LabelModel> labelModels = labelService.findUserAvailableLabel(SecurityUtil.getCurrentUserId());
            //List<LabelModel> labelModels = labelService.find(Collections.singletonMap("isDel", 0));
            Map<String, Object> result = new HashMap<String, Object>();
            result.put("circleList", circleModels);
            result.put("labelList", labelModels);

            return ResponseUtil.success(result);

        } catch(Exception e) {
            e.printStackTrace();

            return ResponseUtil.error("系统异常, 请稍后重试。");
        }
    }

    @ResponseBody
    @RequestMapping(value = "/savePost", method = RequestMethod.POST)
    public Result savePost(@RequestBody PostModel postModel) {

        try {
            if (CollectionUtils.isNotEmpty(postModel.getImgUrlList())) {
                StringBuffer sb = new StringBuffer();
                for (String imageUrl : postModel.getImgUrlList()) {
                    if (sb.length() > 0) {
                        sb.append(",");
                    }
                    sb.append(imageUrl);
                }

                postModel.setImgUrls(sb.toString());
            }
            postModel.setUserId(SecurityUtil.getCurrentUserId());
            int saveCnt = postService.saveNotNull(postModel);

            if (CollectionUtils.isNotEmpty(postModel.getCircleIds())) {
                for (Integer circleId : postModel.getCircleIds()) {
                    CirclePostModel circlePostModel = new CirclePostModel();
                    circlePostModel.setCircleId(circleId);
                    circlePostModel.setPostId(postModel.getId());
                    circlePostService.saveNotNull(circlePostModel);
                }
            }

            if(CollectionUtils.isNotEmpty(postModel.getLabelList())) {
                for(LabelModel labelModel : postModel.getLabelList()) {
                    Integer labelId = labelModel.getId();
                    if(labelId == null || labelId == 0) {
                        SecurityUser securityUser = SecurityUtil.getCurrentSecurityUser();

                        LabelModel saveLabelModel = new LabelModel();
                        saveLabelModel.setLabelName(labelModel.getLabelName());
                        saveLabelModel.setCreator(securityUser.getDisplayName());
                        saveLabelModel.setUserId(securityUser.getId());
                        saveLabelModel.setType(2);
                        saveLabelModel.setStatus(1);
                        saveLabelModel.setIsDel(0);
                        saveLabelModel.setCatId(0);
                        labelService.saveNotNull(saveLabelModel);

                        labelId = saveLabelModel.getId();

                    } else {
                        labelId = labelModel.getId();
                    }

                    PostLabelModel postLabelModel = new PostLabelModel();
                    postLabelModel.setLabelId(labelId);
                    postLabelModel.setPostId(postModel.getId());
                    postLabelService.saveNotNull(postLabelModel);
                }
            }

            /*if (CollectionUtils.isNotEmpty(postModel.getLabelIds())) {
                for (Integer labelId : postModel.getLabelIds()) {
                    PostLabelModel postLabelModel = new PostLabelModel();
                    postLabelModel.setLabelId(labelId);
                    postLabelModel.setPostId(postModel.getId());
                    postLabelService.saveNotNull(postLabelModel);
                }
            }*/

            return ResponseUtil.success();

        } catch(Exception e) {
            e.printStackTrace();

            return ResponseUtil.error("系统异常, 请稍后重试。");
        }
    }

    @ResponseBody
    @RequestMapping(value = "/detail", method = RequestMethod.POST)
    public Result detail(@RequestParam("id") Integer id) {

        try {
            PostModel postModel = postService.findById(id);
            if (postModel != null) {
                UserModel userModel = userService.findById(postModel.getUserId());
                if (userModel != null) {
                    postModel.setNickName(userModel.getNickName());
                    postModel.setHeadImage(userModel.getHeadImage());
                }

                Map<Integer, List<LabelModel>> labelMap = this.labelService.findMapByPostIds(Collections.singletonList(id));
                List<LabelModel> labelModels = labelMap.get(id);
                if (CollectionUtils.isNotEmpty(labelModels)) {
                    List<String> labelNames = new ArrayList<String>();
                    for (LabelModel labelModel : labelModels) {
                        labelNames.add(labelModel.getLabelName());
                    }

                    postModel.setLabelNames(labelNames);

                    postModel.setLabelList(labelModels);
                }

                List<Integer> userLikePostIds = userLikeService
                        .findPostIdsByUserIdAndPostIds(SecurityUtil.getCurrentUserId(),
                                Collections.singletonList(postModel.getId()));
                List<Integer> userCollectPostIds = userCollectService
                        .findPostIdsByUserIdAndPostIds(SecurityUtil.getCurrentUserId(),
                                Collections.singletonList(postModel.getId()));
                if(userLikePostIds.contains(postModel.getId())) {
                    postModel.setIsLike(1);
                }
                if(userCollectPostIds.contains(postModel.getId())) {
                    postModel.setIsCollect(1);
                }

                List<PostCommentModel> postComments = postCommentService.findByPostId(postModel.getId());
                if(CollectionUtils.isNotEmpty(postComments)) {
                    postModel.setCommentList(postComments);
                }
            }

            return ResponseUtil.success(postModel);

        } catch(Exception e) {
            e.printStackTrace();

            return ResponseUtil.error("系统异常, 请稍后重试。");
        }
    }

    @ResponseBody
    @RequestMapping(value = "/sharePost", method = RequestMethod.POST)
    public Result sharePost(@RequestParam("id") Integer id) {

        try {
            int updateCnt = postService.columnPlusNumber(id, "share_cnt", 1);

            return this.detail(id);

        } catch(Exception e) {
            e.printStackTrace();

            return ResponseUtil.error("系统异常, 请稍后重试。");
        }
    }

    @ResponseBody
    @RequestMapping(value = "/commentPost", method = RequestMethod.POST)
    public Result commentPost(@RequestBody PostCommentModel postCommentModel) {

        try {
            postCommentModel.setCommentUserId(SecurityUtil.getCurrentUserId());
            postCommentService.saveNotNull(postCommentModel);

            return ResponseUtil.success();

        } catch(Exception e) {
            return ResponseUtil.error("系统异常, 请稍后重试。");
        }
    }

    @ResponseBody
    @RequestMapping(value = "/delPost", method = RequestMethod.POST)
    public Result delPost(@RequestParam("id") Integer id) {

        try {
            PostModel postModel = new PostModel();
            postModel.setId(id);
            postModel.setIsDel(1);

            int updateCnt = postService.updateNotNull(postModel);

            return ResponseUtil.success();

        } catch(Exception e) {
            e.printStackTrace();

            return ResponseUtil.error("系统异常, 请稍后重试。");
        }
    }

    @ResponseBody
    @RequestMapping(value = "/updateTopPost", method = RequestMethod.POST)
    public Result updateTopPost(@RequestParam("id")Integer id, @RequestParam("isTop")Integer isTop) {

        try {
            PostModel postModel = new PostModel();
            postModel.setId(id);
            postModel.setIsTop(isTop);

            int updateCnt = postService.updateNotNull(postModel);

            return ResponseUtil.success();

        } catch(Exception e) {
            e.printStackTrace();

            return ResponseUtil.error("系统异常, 请稍后重试。");
        }
    }

}
