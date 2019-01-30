package com.lpdm.zuulserver.services;

import com.lpdm.zuulserver.beans.AppUserBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserDetailsServiceImpl implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUserBean user = new AppUserBean();

        return null;
    }
}
