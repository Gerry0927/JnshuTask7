package com.gerry.jnshu.dao;

import com.gerry.jnshu.pojo.UserInfo;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

@Repository
public interface UserInfoMapper extends Mapper<UserInfo> {

    Integer insertUserInfo(UserInfo userInfo);
}