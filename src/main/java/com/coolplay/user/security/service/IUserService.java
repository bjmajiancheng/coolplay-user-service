package com.coolplay.user.security.service;

import com.coolplay.user.common.baseservice.IBaseService;
import com.coolplay.user.core.model.UserModel;
import com.coolplay.user.security.dto.FunctionDto;
import com.github.pagehelper.PageInfo;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by majiancheng on 2019/9/16.
 */
public interface IUserService extends IBaseService<UserModel> {

    /**
     * 根据登录名获取用户信息
     *
     * @param loginName
     * @return
     */
    public UserModel findUserByLoginName(String loginName);

    /**
     * 根据用户ID获取登录名
     *
     * @param userId
     * @return
     */
    public String findLoginNameByUserId(Integer userId);

    void updateLastLoginInfoByUserName(String username, Date date, String remoteAddr);

    PageInfo<UserModel> selectByFilterAndPage(UserModel userModel, int pageNum,
            int pageSize);

    List<UserModel> selectByFilter(UserModel userModel);

    public UserModel findUserByUserId(int userId);

    public Map<Integer, UserModel> findUserMapByUserIds(List<Integer> userIds);

}
