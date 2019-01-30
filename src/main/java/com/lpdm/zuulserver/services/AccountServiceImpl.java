package com.lpdm.zuulserver.services;

import com.lpdm.zuulserver.beans.AppRoleBean;
import com.lpdm.zuulserver.beans.AppUserBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public AppUserBean saveUser(AppUserBean user) {
        return null;
    }

    @Override
    public AppRoleBean saveRole(AppRoleBean role) {
        return null;
    }

    @Override
    public void addRoleToUse(String userName, String roleName) {

    }

    @Override
    public AppUserBean findUserByUserName(String userName) {
        return null;
    }
}
