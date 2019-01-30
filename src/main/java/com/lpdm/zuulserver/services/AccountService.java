package com.lpdm.zuulserver.services;

import com.lpdm.zuulserver.beans.AppRoleBean;
import com.lpdm.zuulserver.beans.AppUserBean;

public interface AccountService {
    AppUserBean saveUser(AppUserBean user);
    AppRoleBean saveRole(AppRoleBean role);
    void addRoleToUse(String userName, String roleName);
    AppUserBean findUserByUserName(String userName);
}
