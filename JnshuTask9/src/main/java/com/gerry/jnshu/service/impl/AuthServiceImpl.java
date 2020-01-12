package com.gerry.jnshu.service.impl;

import com.gerry.jnshu.core.constant.ServiceExceptionEnum;
import com.gerry.jnshu.core.exception.ServiceException;
import com.gerry.jnshu.core.security.JwtUser;
import com.gerry.jnshu.core.security.SecurityUtils;
import com.gerry.jnshu.core.security.TokenService;
import com.gerry.jnshu.dao.UserInfoMapper;
import com.gerry.jnshu.pojo.UserInfo;
import com.gerry.jnshu.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserInfoMapper userInfoMapper;
    @Resource
    private AuthenticationManager authenticationManager;
    @Autowired
    private TokenService tokenService;

    @Override
    public UserInfo login(UserInfo userInfo) {
        String userName = userInfo.getPhoneNum();
        String password = userInfo.getPassword();

        UsernamePasswordAuthenticationToken upToken = new UsernamePasswordAuthenticationToken(userName, password);
        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(upToken);
        } catch (Exception e) {
            if(e instanceof BadCredentialsException){
                throw new ServiceException(ServiceExceptionEnum.USER_NOT_MATCH);
            }
            else{
                throw new ServiceException(ServiceExceptionEnum.USER_ACCOUNT_ERROR);
            }
        }
        JwtUser jwtUser = (JwtUser) authentication.getPrincipal();
        userInfo.setToken(tokenService.createToken(jwtUser));
        return userInfo;
    }

    @Override
    public Integer register(UserInfo userInfo) {
        Example example = new Example(UserInfo.class);
        //判断用户是否存在（手机号码是唯一标识）
        example.createCriteria().andEqualTo("phoneNum", userInfo.getPhoneNum());
        int count = userInfoMapper.selectCountByExample(example);
        if (count > 0) {
            throw new ServiceException(ServiceExceptionEnum.USER_EXIST);
        }
        int rowId = userInfoMapper.insertSelective(userInfo);
        return userInfo.getUserId();
    }
}
