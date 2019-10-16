package com.coolplay.user.security.service;

import com.coolplay.user.security.security.SecurityUser;
import org.springframework.security.access.ConfigAttribute;

import java.util.Collection;
import java.util.Map;

/**
 * Created by majiancheng on 2019/9/16.
 */
public interface ISecurityService {

    /**
     * 根据登录名称获取用户信息
     *
     * @param loginName
     * @return
     */
    public SecurityUser loadSecurityUserByLoginName(String loginName);

    public Map<String, Collection<ConfigAttribute>> getCacheResourceMap();

    public Map<String, Collection<ConfigAttribute>> getDbResourceMap();
}
