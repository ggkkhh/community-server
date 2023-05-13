package com.roydon.app.controller;

import com.roydon.common.constant.Constants;
import com.roydon.common.core.domain.AjaxResult;
import com.roydon.common.core.domain.entity.SysUser;
import com.roydon.common.core.domain.model.SmsLoginBody;
import com.roydon.common.utils.SecurityUtils;
import com.roydon.framework.web.service.SysLoginService;
import com.roydon.framework.web.service.SysPermissionService;
import com.roydon.app.domain.dto.AppLoginBody;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Set;

/**
 * 登录验证
 */
@Api("登录控制层")
@RestController()
@RequestMapping("/app")
public class AppLoginController {

    private static final Logger log = LoggerFactory.getLogger(AppLoginController.class);

    @Resource
    private SysLoginService loginService;

    @Resource
    private SysPermissionService permissionService;

    /**
     * 登录方法
     *
     * @param appLoginBody 登录信息
     * @return 结果
     */
    @PostMapping("/login")
    public AjaxResult login(@RequestBody AppLoginBody appLoginBody) {
        // 生成令牌
        String token = loginService.appUPLogin(appLoginBody.getUsername(), appLoginBody.getPassword());
        return AjaxResult.success().put(Constants.TOKEN, token);
    }

    /**
     * 手机验证码登录方法
     *
     * @param smsLoginBody
     * @return 结果
     */
    @PostMapping("/sms-login")
    public AjaxResult smsLogin(@RequestBody SmsLoginBody smsLoginBody) {
        // 生成令牌
        log.info("手机验证码登录：{}",smsLoginBody.getTelephone());
        String token = loginService.smsLogin(smsLoginBody.getTelephone(), smsLoginBody.getPhoneCode());
        return AjaxResult.success().put(Constants.TOKEN, token);
    }

    /**
     * 获取用户信息
     *
     * @return 用户信息
     */
    @GetMapping("getInfo")
    public AjaxResult getInfo() {
        SysUser user = SecurityUtils.getLoginUser().getUser();
        // 角色集合
        Set<String> roles = permissionService.getRolePermission(user);
        // 权限集合
        Set<String> permissions = permissionService.getMenuPermission(user);
        AjaxResult ajax = AjaxResult.success();
        ajax.put("user", user);
        ajax.put("roles", roles);
        ajax.put("permissions", permissions);
        return ajax;
    }

}
