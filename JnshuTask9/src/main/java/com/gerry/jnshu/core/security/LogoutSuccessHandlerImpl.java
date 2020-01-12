package com.gerry.jnshu.core.security;

import com.gerry.jnshu.core.CommonResult;
import com.gerry.jnshu.core.utils.JSONUtil;
import com.gerry.jnshu.core.utils.ServletUtils;
import com.gerry.jnshu.core.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import sun.swing.StringUIClientPropertyKey;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class LogoutSuccessHandlerImpl implements LogoutSuccessHandler {

    @Autowired
    private TokenService tokenService;

    @Override
    public void onLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        JwtUser jwtUser= tokenService.getLoginUser(httpServletRequest);
        if (StringUtils.isNotNull(jwtUser)) {
            //String username =
            //删除 Redis 存储的用户信息
            tokenService.delLoginUser(jwtUser.getToken());
        }
        //响应退出成功
        ServletUtils.renderString(httpServletResponse, JSONUtil.toJSONString(CommonResult.success(null,"退出成功")));
    }
}
