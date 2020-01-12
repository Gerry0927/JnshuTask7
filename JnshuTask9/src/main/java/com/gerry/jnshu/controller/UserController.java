package com.gerry.jnshu.controller;

import com.gerry.jnshu.core.CommonResult;
import com.gerry.jnshu.pojo.UserInfo;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @RequestMapping("/info")
    @PreAuthorize("hasRole('admin')")
    public CommonResult<UserInfo> getUserInfo(Integer userId){
        return CommonResult.success(new UserInfo(),"查询成功");
    }
}
