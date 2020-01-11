package com.gerry.jnshu.service.impl;

import com.gerry.jnshu.dao.UserInfoMapper;
import com.gerry.jnshu.pojo.UserInfo;
import com.gerry.jnshu.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserInfoServiceImpl implements UserInfoService {

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Override
    public UserInfo findUserInfoByAccount(String phoneNum) {
        UserInfo userInfo = new UserInfo();
        userInfo.setPhoneNum(phoneNum);
        return userInfoMapper.selectOne(userInfo);
    }

    @Override
    public UserInfo findUserInfoById(Integer userId) {
        return userInfoMapper.selectByPrimaryKey(userId);
    }
}
