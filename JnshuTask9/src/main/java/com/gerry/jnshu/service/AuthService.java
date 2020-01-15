package com.gerry.jnshu.service;

import com.gerry.jnshu.pojo.EmailInfo;
import com.gerry.jnshu.pojo.SmsInfo;
import com.gerry.jnshu.pojo.UserInfo;

public interface AuthService {

    UserInfo login(UserInfo userInfo);
    Integer register(UserInfo userInfo);

    SmsInfo sendSMSCode(String phone);

    EmailInfo sendEmail(String toAddress,String subject,String content);

}
