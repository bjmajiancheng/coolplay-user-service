package com.coolplay.company.security.security;

import com.coolplay.company.core.model.UserModel;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.Date;

/**
 * Created by majiancheng on 2019/9/15.
 */
public class SecurityUser extends User {

    private static final long serialVersionUID = -8968441075823153801L;

    private Integer id;

    private String userName;

    private String displayName;

    private String contactPhone;

    private Integer isAdmin;

    private Date lastPasswordReset;

    private Integer companyId;

    public SecurityUser(UserModel user,
            Collection<GrantedAuthority> userGrantedAuthorities) {
        super(user.getUserName(), user.getPassword(), user.getEnabled() == 1, user.getAccountNonExpired(),
                user.getCredentialsNonExpired(), user.getAccountNonLocked(), userGrantedAuthorities);
        if (user != null) {
            setId(user.getId());
            setUserName(user.getUserName());
            setDisplayName(user.getDisplayName());
            setContactPhone(user.getContactPhone());
            setLastPasswordReset(user.getLastPasswordReset());
            setCompanyId(user.getCompanyId());
        }
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public Integer getIsAdmin() {
        return this.isAdmin;
    }

    public void setIsAdmin(Integer isAdmin) {
        this.isAdmin = isAdmin;
    }

    public Date getLastPasswordReset() {
        return lastPasswordReset;
    }

    public void setLastPasswordReset(Date lastPasswordReset) {
        this.lastPasswordReset = lastPasswordReset;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }
}
