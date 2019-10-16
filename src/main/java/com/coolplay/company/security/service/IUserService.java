package com.coolplay.company.security.service;

import com.coolplay.company.common.baseservice.IBaseService;
import com.coolplay.company.core.model.UserModel;
import com.coolplay.company.core.model.UserRoleModel;
import com.coolplay.company.security.dto.FunctionDto;
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
     * 根据用户ID获取用户角色
     *
     * @param userId
     * @return
     */
    List<Integer> findUserRoleByUserId(int userId);

    /**
     * 根据登录名获取用户权限信息
     *
     * @param loginName
     * @return
     */
    public List<FunctionDto> findUserFunctionByLoginName(String loginName);

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

    public List<UserRoleModel> selectUserRoleByUserId(int userId);

    public Map<Integer, UserModel> findUserMapByUserIds(List<Integer> userIds);
}
