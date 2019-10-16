package com.coolplay.company.security.security;

import com.coolplay.company.security.service.ISecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * Created by majiancheng on 2019/9/16.
 */
public class UserServiceImpl implements UserDetailsService {

    @Autowired
    private ISecurityService securityService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SecurityUser securityUser = securityService.loadSecurityUserByLoginName(username);
        if (securityUser == null) {
            throw new UsernameNotFoundException("用户名不存在");
        }
        return securityUser;
    }
}
