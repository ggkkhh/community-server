package com.roydon.framework.config.smsconfig.handler;

import cn.hutool.json.JSONObject;
import com.roydon.common.constant.Constants;
import com.roydon.common.constant.HttpStatus;
import com.roydon.common.core.domain.entity.SysUser;
import com.roydon.framework.manager.AsyncManager;
import com.roydon.framework.manager.factory.AsyncFactory;
import com.roydon.system.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;
 
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
 
/**
 * Author: LiuLin
 * Date: 2022/5/30 10:19
 * Description:
 */
 
@Component
public class FailAuthenticationHandler extends SimpleUrlAuthenticationFailureHandler {
 
    @Autowired
    private ISysUserService userService;
 
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {
 
        String mobile = request.getParameter("telephone");
        SysUser sysUser = userService.getUserByTelephone(mobile);
        if (sysUser == null) {
            AsyncManager.me().execute(AsyncFactory.recordLogininfor("未知", Constants.LOGIN_FAIL, "手机号：" + mobile + "不存在"));
        } else {
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(sysUser.getUserName(), Constants.LOGIN_FAIL, exception.getMessage()));
        }
 
        JSONObject res = new JSONObject();//返回前端数据
        res.put("code", HttpStatus.ERROR);
        res.put("msg", exception.getMessage());
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(String.valueOf(res));
 
    }
 
}