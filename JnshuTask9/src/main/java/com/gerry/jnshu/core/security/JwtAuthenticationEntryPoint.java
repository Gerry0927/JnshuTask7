package com.gerry.jnshu.core.security;

import com.gerry.jnshu.core.CommonResult;
import com.gerry.jnshu.core.utils.JSONUtil;
import com.gerry.jnshu.core.utils.ServletUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint, Serializable {
    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        int code = HttpStatus.UNAUTHORIZED.value();
        String msg = "认证失败，token 不合法";
        ServletUtils.renderString(httpServletResponse, JSONUtil.toJSONString(CommonResult.error(code,msg)));
    }
}
