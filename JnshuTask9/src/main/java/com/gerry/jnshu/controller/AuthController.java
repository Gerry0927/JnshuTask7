package com.gerry.jnshu.controller;

import com.gerry.jnshu.core.CommonResult;
import com.gerry.jnshu.core.limit.Limit;
import com.gerry.jnshu.core.limit.LimitType;
import com.gerry.jnshu.pojo.UserInfo;
import com.gerry.jnshu.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@RestController
@RequestMapping("/auth")
@Validated
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

    @Limit(prefix = "limit:auth:",key = "sendSmsCode", period =100, count = 2,limitType = LimitType.IP)
    @PostMapping("/sendSmsCode")
    public CommonResult<Boolean> sendSmsCode(
            @NotBlank
            @Pattern(regexp = "^((13[0-9])|(14[5,7])|(15[0-3,5-9])|(17[0,3,5-8])|(18[0-9])|166|198|199|(147))\\d{8}$",message = "手机号格式不正确") String phoneNum){
        authService.sendSMSCode(phoneNum);
        return CommonResult.success(true,"验证码发送成功，请注意查收");
    }

    @PostMapping("/sendEmail")
    public CommonResult<Boolean> sendEmail(@RequestParam String toAddress,String content,String subject){
        authService.sendEmail(toAddress,subject,content);
        return CommonResult.success(true,"邮件发送成功，请注意查收");
    }



}
