package com.gerry.jnshu.service;

import com.gerry.jnshu.pojo.UserInfo;

public interface AuthService {

    UserInfo login(UserInfo userInfo);
    Integer register(UserInfo userInfo);

}
