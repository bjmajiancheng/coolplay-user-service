package com.coolplay.company.security.service.impl;

import com.coolplay.company.common.baseservice.impl.BaseService;
import com.coolplay.company.core.dao.UserMapper;
import com.coolplay.company.core.dao.UserRoleMapper;
import com.coolplay.company.core.model.UserModel;
import com.coolplay.company.core.model.UserRoleModel;
import com.coolplay.company.security.dto.FunctionDto;
import com.coolplay.company.security.service.IUserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.*;

/**
 * Created by majiancheng on 2019/9/16.
 */
@Service("userService")
public class UserServiceImpl extends BaseService<UserModel> implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    /**
     * 根据登录名获取用户信息
     *
     * @param loginName
     * @return
     */
    public UserModel findUserByLoginName(String loginName) {
        return userMapper.findUserByLoginName(loginName);
    }

    @Override
    public List<Integer> findUserRoleByUserId(int userId) {
        return userMapper.findUserRoleByUserId(userId);
    }

    /**
     * 根据登录名获取用户权限信息
     *
     * @param loginName
     * @return
     */
    public List<FunctionDto> findUserFunctionByLoginName(String loginName) {
        return userMapper.findUserFunctionByLoginName(loginName);
    }

    /**
     * 根据用户ID获取登录名
     *
     * @param userId
     * @return
     */
    public String findLoginNameByUserId(Integer userId) {
        return userMapper.findLoginNameByUserId(userId);
    }

    public void updateLastLoginInfoByUserName(String username, Date date, String remoteAddr) {
        userMapper.updateLastLoginInfoByUserName(username, date, remoteAddr);
    }

    @Override
    public PageInfo<UserModel> selectByFilterAndPage(UserModel userModel, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<UserModel> list = selectByFilter(userModel);
        if(CollectionUtils.isNotEmpty(list)) {
            for (UserModel user : list) {
                user.setPassword("");
            }
        }
        return new PageInfo<>(list);
    }

    @Override
    public List<UserModel> selectByFilter(UserModel userModel) {
        Example example = new Example(UserModel.class);
        Example.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotEmpty(userModel.getContactPhone())) {
            criteria.andEqualTo("contactPhone", userModel.getContactPhone());
        }
        if (StringUtils.isNotEmpty(userModel.getDisplayName())) {
            criteria.andLike("displayName", "%" + userModel.getDisplayName() + "%");
        }
        if (StringUtils.isNotEmpty(userModel.getUserName())) {
            criteria.andLike("userName", "%" + userModel.getUserName() + "%");
        }
        if(userModel.getCompanyId() != null) {
            criteria.andEqualTo("companyId", userModel.getCompanyId());
        }
        if (StringUtils.isNotEmpty(userModel.getSortWithOutOrderBy())) {
            example.setOrderByClause(userModel.getSortWithOutOrderBy());
        }
        return getMapper().selectByExample(example);
    }

    @Override
    public UserModel findUserByUserId(int userId) {
        UserModel userModel = userMapper.findUserByUserId(userId);
        userModel.setPassword("");
        return userModel;
    }


    @Override
    public List<UserRoleModel> selectUserRoleByUserId(int userId) {
        return userRoleMapper.find(Collections.singletonMap("userId", userId));
    }

    @Override
    public Map<Integer, UserModel> findUserMapByUserIds(List<Integer> userIds) {
        if(CollectionUtils.isEmpty(userIds)) {
            return Collections.emptyMap();
        }
        List<UserModel> userModels = userMapper.find(Collections.singletonMap("userIds", userIds));
        if(CollectionUtils.isEmpty(userModels)) {
            return Collections.emptyMap();
        }

        Map<Integer, UserModel> userModelMap = new HashMap<Integer, UserModel>();
        for(UserModel userModel : userModels) {
            userModelMap.put(userModel.getId(), userModel);
        }

        return userModelMap;
    }
}
