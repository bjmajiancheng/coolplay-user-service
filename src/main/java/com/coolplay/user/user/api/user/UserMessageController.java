package com.coolplay.user.user.api.user;

import com.coolplay.user.common.utils.PageConvertUtil;
import com.coolplay.user.common.utils.ResponseUtil;
import com.coolplay.user.common.utils.Result;
import com.coolplay.user.security.utils.SecurityUtil;
import com.coolplay.user.user.model.MessageModel;
import com.coolplay.user.user.service.IMessageService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Created by majiancheng on 2019/10/31.
 */
@Controller
@RequestMapping("/api/user/message")
public class UserMessageController {

    @Autowired
    private IMessageService messageService;

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


}
