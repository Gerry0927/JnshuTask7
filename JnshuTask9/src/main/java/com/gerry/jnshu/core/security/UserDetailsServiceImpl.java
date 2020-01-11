package com.gerry.jnshu.core.security;

import com.gerry.jnshu.core.utils.StringUtils;
import com.gerry.jnshu.pojo.UserInfo;
import com.gerry.jnshu.service.AuthService;
import com.gerry.jnshu.service.UserInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private static final Logger log = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    @Autowired
    private UserInfoService userInfoService;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 查询指定用户名对应的 SysUser
        UserInfo userInfo = userInfoService.findUserInfoByAccount(username);
        // 各种校验
        if (StringUtils.isNull(userInfo)) { // 用户不存在
            log.info("登录用户：{} 不存在.", username);
            throw new UsernameNotFoundException("用户：" + username + " 不存在");
        }
//        else if (UserStatus.DELETED.getCode().equals(user.getDelFlag())) { // 用户已删除
//            log.info("登录用户：{} 已被删除.", username);
//            throw new BaseException("对不起，您的账号：" + username + " 已被删除");
//        } else if (UserStatus.DISABLE.getCode().equals(user.getStatus())) { // 用户已禁用
//            log.info("登录用户：{} 已被停用.", username);
//            throw new BaseException("对不起，您的账号：" + username + " 已停用");
//        }

        // 创建 Spring Security UserDetails 用户明细
        return createLoginUser(userInfo);
    }

    public UserDetails createLoginUser(UserInfo user) {
        return new JwtUser(user,null);
    }

}