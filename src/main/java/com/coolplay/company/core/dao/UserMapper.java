package com.coolplay.company.core.dao;

import com.coolplay.company.core.model.UserModel;
import com.coolplay.company.security.dto.FunctionDto;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by majiancheng on 2019/9/16.
 */
public interface UserMapper extends Mapper<UserModel> {

    UserModel findUserByLoginName(@Param("loginName") String loginName);

    List<Integer> findUserRoleByUserId(@Param("userId")int userId);

    /**
     * 根据登录名获取用户权限信息
     *
     * @param loginName
     * @return
     */
    public List<FunctionDto> findUserFunctionByLoginName(@Param("loginName")String loginName);

    /**
     * 根据用户ID获取登录名
     *
     * @param userId
     * @return
     */
    public String findLoginNameByUserId(@Param("userId")Integer userId);

    int updateLastLoginInfoByUserName(@Param("userName") String username, @Param("lastLoginDate") Date lastLoginDate,
            @Param("remoteAddr") String remoteAddr);

    public UserModel findUserByUserId(@Param("userId")int userId);

    public List<UserModel> find(Map<String, Object> param);
}
