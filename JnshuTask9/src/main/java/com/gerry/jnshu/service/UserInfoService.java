package com.gerry.jnshu.service;

import com.gerry.jnshu.pojo.UserInfo;

public interface UserInfoService {

    UserInfo findUserInfoByAccount(String phoneNum);

    UserInfo findUserInfoById(Integer userId);
}
