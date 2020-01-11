package com.gerry.jnshu.controller;

import com.gerry.jnshu.core.CommonResult;
import com.gerry.jnshu.pojo.UserInfo;
import com.gerry.jnshu.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public CommonResult<Integer> register(@Valid UserInfo userInfo){
        Integer userId = authService.register(userInfo);
        return CommonResult.success(userInfo.getUserId(),"注册成功");
    }

    @PostMapping("/login")
    public CommonResult<UserInfo> login(@Valid UserInfo userInfo){
        userInfo = authService.login(userInfo);
        return CommonResult.success(userInfo,"登录");
    }


    @PostMapping("/sendSmsCode")
    public CommonResult<Boolean> sendSmsCode(){
        return CommonResult.success(true,"验证码发送成功，请注意查收");
    }



}
