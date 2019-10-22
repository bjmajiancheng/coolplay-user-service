package com.coolplay.user.user.api.circle;

import com.coolplay.user.common.utils.PageConvertUtil;
import com.coolplay.user.common.utils.ResponseUtil;
import com.coolplay.user.common.utils.Result;
import com.coolplay.user.core.model.UserModel;
import com.coolplay.user.security.security.SecurityUser;
import com.coolplay.user.security.utils.SecurityUtil;
import com.coolplay.user.user.model.*;
import com.coolplay.user.user.service.*;
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
    public Result list(PostModel postModel,
            @RequestParam(value = "pageNum", required = false, defaultValue = "1") int pageNum,
            @RequestParam(value = "pageSize", required = false, defaultValue = "15") int pageSize) {

        PageInfo<PostModel> pageInfo = this.postService.selectByFilterAndPage(postModel, pageNum, pageSize);
        if (CollectionUtils.isNotEmpty(pageInfo.getList())) {
            List<Integer> postIds = new ArrayList<Integer>();
            List<Integer> userIds = new ArrayList<Integer>();
            for (PostModel tmpPostModel : pageInfo.getList()) {
                postIds.add(tmpPostModel.getId());
                userIds.add(tmpPostModel.getUserId());
            }

            Map<Integer, List<LabelModel>> labelMap = labelService.findMapByPostIds(postIds);
            Map<Integer, UserModel> userMap = userService.findMapByUserIds(userIds);
            List<Integer> userLikePostIds = userLikeService
                    .findPostIdsByUserIdAndPostIds(SecurityUtil.getCurrentUserId(), postIds);
            List<Integer> userCollectPostIds = userCollectService
                    .findPostIdsByUserIdAndPostIds(SecurityUtil.getCurrentUserId(), postIds);

            for (PostModel tmpPostModel : pageInfo.getList()) {
                tmpPostModel.setLabelList(labelMap.get(tmpPostModel.getId()));
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
    }

    @ResponseBody
    @RequestMapping(value = "/collectPost", method = RequestMethod.POST)
    public Result collectPost(@RequestParam("id") Integer id) {

        int updateCnt = postService.columnPlusNumber(id, "collect_cnt", 1);

        UserCollectModel userCollectModel = new UserCollectModel();
        userCollectModel.setCollectType(2);
        userCollectModel.setCollectTypeId(id);
        userCollectModel.setUserId(SecurityUtil.getCurrentUserId());
        userCollectModel.setIsDel(0);


        if(CollectionUtils.isEmpty(userCollectService.selectByFilter(userCollectModel))) {
            int saveCnt = userCollectService.saveNotNull(userCollectModel);
        }

        PostModel postModel = postService.findById(id);

        return ResponseUtil.success(Collections.singletonMap("commentCnt", postModel.getCollectCnt()));
    }

    @ResponseBody
    @RequestMapping(value = "/postDetail", method = RequestMethod.POST)
    public Result postDetail(@RequestParam("id") Integer id) {
        /*PostModel postModel = postService.findById(id);
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
        }*/

        return this.detail(id);
    }

    @ResponseBody
    @RequestMapping(value = "/likePost", method = RequestMethod.POST)
    public Result likePost(@RequestParam("id") Integer id, @RequestParam("type")Integer type) {


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
    }

    /**
     * 帖子页基础信息
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/postBaseInfo", method = RequestMethod.POST)
    public Result postBaseInfo() {
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

        return ResponseUtil.success(Collections.singletonMap("circleList", circleModels));
    }

    @ResponseBody
    @RequestMapping(value = "/savePost", method = RequestMethod.POST)
    public Result savePost(PostModel postModel) {

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

        if (CollectionUtils.isNotEmpty(postModel.getLabelIds())) {
            for (Integer labelId : postModel.getLabelIds()) {
                PostLabelModel postLabelModel = new PostLabelModel();
                postLabelModel.setLabelId(labelId);
                postLabelModel.setPostId(postModel.getId());
                postLabelService.saveNotNull(postLabelModel);
            }
        }

        return ResponseUtil.success();
    }

    @ResponseBody
    @RequestMapping(value = "/detail", method = RequestMethod.POST)
    public Result detail(@RequestParam("id") Integer id) {
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
    }

    @ResponseBody
    @RequestMapping(value = "/sharePost", method = RequestMethod.POST)
    public Result sharePost(@RequestParam("id") Integer id) {

        int updateCnt = postService.columnPlusNumber(id, "share_cnt", 1);

        return this.detail(id);
    }

    @ResponseBody
    @RequestMapping(value = "/commentPost", method = RequestMethod.POST)
    public Result commentPost(PostCommentModel postCommentModel) {
        postCommentModel.setCommentUserId(SecurityUtil.getCurrentUserId());
        postCommentService.saveNotNull(postCommentModel);

        return ResponseUtil.success();
    }

    @ResponseBody
    @RequestMapping(value = "/delPost", method = RequestMethod.POST)
    public Result delPost(@RequestParam("id") Integer id) {
        PostModel postModel = new PostModel();
        postModel.setId(id);
        postModel.setIsDel(1);

        int updateCnt = postService.updateNotNull(postModel);

        return ResponseUtil.success();
    }

    @ResponseBody
    @RequestMapping(value = "/updateTopPost", method = RequestMethod.POST)
    public Result updateTopPost(@RequestParam("id")Integer id, @RequestParam("isTop")Integer isTop) {
        PostModel postModel = new PostModel();
        postModel.setId(id);
        postModel.setIsTop(isTop);

        int updateCnt = postService.updateNotNull(postModel);

        return ResponseUtil.success();
    }

}
