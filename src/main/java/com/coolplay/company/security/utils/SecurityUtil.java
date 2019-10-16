package com.coolplay.company.security.utils;

import com.coolplay.company.security.security.SecurityUser;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Created by cgj on 2015/10/26.
 */
public class SecurityUtil {

    public static String getCurrentUserName() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userName = null;
        if (principal instanceof SecurityUser) userName = ((SecurityUser) principal).getUserName();
        if (principal instanceof UserDetails) userName = ((UserDetails) principal).getUsername();
        return userName;
    }

    public static Integer getCurrentUserId() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Integer userId = null;
        if (principal instanceof SecurityUser) userId = ((SecurityUser) principal).getId();
        return userId;
    }

    public static SecurityUser getCurrentSecurityUser() {
        if(SecurityContextHolder.getContext().getAuthentication() == null)
            return null;

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof SecurityUser) return (SecurityUser) principal;
        return null;
    }

    public static Integer getCurrentCompanyId() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof SecurityUser) return ((SecurityUser) principal).getCompanyId();
        return null;
    }

    public static String encodeString(String character) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(character);
    }

    public static boolean matchString(String character, String encodedCharacter) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.matches(character, encodedCharacter);
    }

    public static void main(String[] args) {
        System.out.println(encodeString("admin"));
    }

}
