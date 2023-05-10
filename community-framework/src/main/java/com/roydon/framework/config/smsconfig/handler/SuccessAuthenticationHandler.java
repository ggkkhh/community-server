package com.roydon.framework.config.smsconfig.handler;

import cn.hutool.json.JSONObject;
import com.roydon.common.constant.Constants;
import com.roydon.common.constant.HttpStatus;
import com.roydon.common.core.domain.entity.SysUser;
import com.roydon.common.core.domain.model.LoginUser;
import com.roydon.common.utils.DateUtils;
import com.roydon.common.utils.MessageUtils;
import com.roydon.common.utils.ServletUtils;
import com.roydon.common.utils.ip.IpUtils;
import com.roydon.framework.manager.AsyncManager;
import com.roydon.framework.manager.factory.AsyncFactory;
import com.roydon.framework.web.service.TokenService;
import com.roydon.system.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
 
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class SuccessAuthenticationHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Autowired
    private TokenService tokenService;
 
    @Autowired
    private ISysUserService userService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws ServletException, IOException {

        LoginUser loginUser = (LoginUser) authentication.getDetails();
        recordLoginInfo(loginUser.getUserId());
 
        AsyncManager.me().execute(AsyncFactory.recordLogininfor(loginUser.getUsername(), Constants.LOGIN_SUCCESS, MessageUtils.message("user.login.success")));
 
        JSONObject res = new JSONObject();//返回前端数据
        res.put("msg", "操作成功");
        res.put("code", HttpStatus.SUCCESS);
        res.put("token", tokenService.createToken(loginUser));
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(String.valueOf(res));
        //response.getWriter().write(objectMapper.writeValueAsString(res));
 
    }
 
    public void recordLoginInfo(Long userId) {
        SysUser sysUser = new SysUser();
        sysUser.setUserId(userId);
        sysUser.setLoginIp(IpUtils.getIpAddr(ServletUtils.getRequest()));
        sysUser.setLoginDate(DateUtils.getNowDate());
        userService.updateUserProfile(sysUser);
    }
 
}