package com.coolplay.user.user.api.user;

import com.alibaba.fastjson.JSON;
import com.coolplay.user.common.utils.PageConvertUtil;
import com.coolplay.user.common.utils.ResponseUtil;
import com.coolplay.user.common.utils.Result;
import com.coolplay.user.core.model.UserModel;
import com.coolplay.user.security.service.IUserService;
import com.coolplay.user.security.utils.SecurityUtil;
import com.coolplay.user.user.dto.ReviewMessageDto;
import com.coolplay.user.user.model.CircleModel;
import com.coolplay.user.user.model.MessageModel;
import com.coolplay.user.user.service.ICircleMemberReviewService;
import com.coolplay.user.user.service.ICircleMemberService;
import com.coolplay.user.user.service.ICircleService;
import com.coolplay.user.user.service.IMessageService;
import com.github.pagehelper.PageInfo;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by majiancheng on 2019/10/31.
 */
@Controller
@RequestMapping("/api/user/message")
public class UserMessageController {

    @Autowired
    private IMessageService messageService;

    @Autowired
    private ICircleMemberReviewService circleMemberReviewService;

    @Autowired
    private ICircleMemberService circleMemberService;

    @Autowired
    private ICircleService circleService;

    @Autowired
    private IUserService userService;

    /**
     * 消息列表
     *
     * @param messageModel
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public Result list(@RequestBody MessageModel messageModel) {

        try {
            messageModel.initPageInfo();
            messageModel.setUserId(SecurityUtil.getCurrentUserId());
            messageModel.setSort_("c_time_desc");

            PageInfo<MessageModel> pageInfo = messageService
                    .selectByFilterAndPage(messageModel, messageModel.getPageNum(), messageModel.getPageSize());

            /*if (CollectionUtils.isNotEmpty(pageInfo.getList())) {
                for(MessageModel tmpMessage : pageInfo.getList()) {
                    if(tmpMessage.getMessageType() == 2 && StringUtils.isNotEmpty(tmpMessage.getMessageUrl())) {
                        ReviewMessageDto reviewMessageDto = JSON.parseObject(tmpMessage.getMessageUrl(), ReviewMessageDto.class);

                        if(ReviewMessageDto.APPLICATION_CIRCLE.equals(reviewMessageDto.getType())) {
                            tmpMessage.setYesBtn("通过");
                            tmpMessage.setNoBtn("驳回");
                        } else if(ReviewMessageDto.INVITE_CIRCLE.equals(reviewMessageDto.getType())) {
                            tmpMessage.setYesBtn("接受");
                            tmpMessage.setNoBtn("拒绝");
                        }
                    }
                }
            }*/

            return ResponseUtil.success(PageConvertUtil.grid(pageInfo));

        } catch(Exception e) {
            e.printStackTrace();

            return ResponseUtil.error("系统异常, 请稍后重试。");
        }
    }

    /**
     * 处理消息
     *
     * @param id 消息ID
     * @param isAgree 是否同意（1：同意，2：不同意）
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/dealMessage", method = RequestMethod.POST)
    public Result dealMessage(@RequestParam("id") Integer id, @RequestParam("isAgree") Integer isAgree) {

        try {
            MessageModel messageModel = messageService.findById(id);

            if(messageModel.getUserId() != SecurityUtil.getCurrentUserId()) {
                return ResponseUtil.error("处理消息失败, 用户无权限操作其他消息.");
            }

            MessageModel updateMessage = new MessageModel();
            updateMessage.setId(id);
            updateMessage.setIsAgree(isAgree);
            updateMessage.setIsRead(1);
            int updateCnt = messageService.updateNotNull(updateMessage);

            if(StringUtils.isNotEmpty(messageModel.getMessageUrl())) {
                ReviewMessageDto reviewMessageDto = JSON.parseObject(messageModel.getMessageUrl(), ReviewMessageDto.class);

                //申请加圈
                if(ReviewMessageDto.APPLICATION_CIRCLE.equals(reviewMessageDto.getType())) {
                    Integer reviewStatus = 1;
                    if(isAgree == 1) {
                        reviewStatus = 2;
                    }
                    updateCnt = circleMemberReviewService.updateByCircleIdAndInviteMemberUserId(reviewMessageDto.getTypeId(),
                            reviewMessageDto.getUserId(), reviewMessageDto.getApplicationUserId(), reviewStatus);
                    Integer status = 0;
                    if(isAgree == 1) {
                        status = isAgree;
                    }

                    updateCnt = circleMemberService.updateByCircleIdMemberUserId(reviewMessageDto.getTypeId(), reviewMessageDto.getApplicationUserId(), 1, status);

                    CircleModel circleModel = circleService.findById(reviewMessageDto.getTypeId());
                    MessageModel tmpMessage = new MessageModel();
                    tmpMessage.setMessageName("加入圈子结果");
                    tmpMessage.setMessageContent(String.format("您申请加入的圈子-%s, 申请%s~", circleModel, (isAgree == 1) ? "已同意": "已拒绝"));
                    tmpMessage.setMessageType(2);
                    tmpMessage.setUserId(reviewMessageDto.getApplicationUserId());

                    int saveCnt = messageService.updateNotNull(tmpMessage);
                } else if(ReviewMessageDto.INVITE_CIRCLE.equals(reviewMessageDto.getType())) {
                    Integer reviewStatus = 1;
                    if(isAgree == 1) {
                        reviewStatus = 2;
                    }

                    updateCnt = circleMemberReviewService.updateByCircleIdAndInviteMemberUserId(reviewMessageDto.getTypeId(),
                            reviewMessageDto.getApplicationUserId(), reviewMessageDto.getUserId(), reviewStatus);
                    Integer status = 0;
                    if(isAgree == 1) {
                        status = isAgree;
                    }

                    updateCnt = circleMemberService.updateByCircleIdMemberUserId(reviewMessageDto.getTypeId(), reviewMessageDto.getUserId(), 1, status);

                    UserModel userModel = userService.findById(reviewMessageDto.getUserId());

                    CircleModel circleModel = circleService.findById(reviewMessageDto.getTypeId());
                    MessageModel tmpMessage = new MessageModel();
                    tmpMessage.setMessageName("邀请加入圈子结果");
                    tmpMessage.setMessageContent(String.format("您邀请 %s 加入的圈子-%s, 申请%s~", userModel.getNickName(), circleModel, (isAgree == 1) ? "已同意": "已拒绝"));
                    tmpMessage.setMessageType(2);
                    tmpMessage.setUserId(reviewMessageDto.getApplicationUserId());

                    int saveCnt = messageService.updateNotNull(tmpMessage);
                }
            }

            return ResponseUtil.success();

        } catch(Exception e) {
            e.printStackTrace();

            return ResponseUtil.error("系统异常, 请稍后重试。");
        }
    }

    /**
     * 消息详情信息
     *
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/detail", method = RequestMethod.POST)
    public Result detail(@RequestParam("id") Integer id) {

        try {
            MessageModel updateMessage = new MessageModel();
            updateMessage.setId(id);
            updateMessage.setIsRead(1);
            int updateCnt = messageService.updateNotNull(updateMessage);

            MessageModel messageModel = messageService.findById(id);

            return ResponseUtil.success(messageModel);

        } catch(Exception e) {
            e.printStackTrace();

            return ResponseUtil.error("系统异常, 请稍后重试。");
        }
    }

    /**
     * 用户当前是否有未读消息
     *
     * @param userId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/userMessageInfo", method = RequestMethod.POST)
    public Result userMessageInfo(@RequestParam("userId") Integer userId) {
        try {
            Map<String, Object> param = new HashMap<String, Object>();
            param.put("userId", userId);
            param.put("isRead", 0);
            List<MessageModel> messageModels = messageService.find(param);
            int hasUnreadMsg = 0;
            if(CollectionUtils.isNotEmpty(messageModels)) {
                hasUnreadMsg = 1;
            }


            return ResponseUtil.success(Collections.singletonMap("hasUnreadMsg", hasUnreadMsg));
        } catch(Exception e) {
            e.printStackTrace();
            return ResponseUtil.error("系统异常, 请稍后重试。");
        }
    }

}
