package com.roydon.web.controller.system;

import com.roydon.common.constant.Constants;
import com.roydon.common.core.domain.AjaxResult;
import com.roydon.common.core.domain.entity.SysMenu;
import com.roydon.common.core.domain.entity.SysUser;
import com.roydon.common.core.domain.model.LoginBody;
import com.roydon.common.core.domain.model.SmsLoginBody;
import com.roydon.common.utils.SecurityUtils;
import com.roydon.framework.web.service.SysLoginService;
import com.roydon.framework.web.service.SysPermissionService;
import com.roydon.system.service.ISysMenuService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

/**
 * 登录验证
 */
@Api("登录控制层")
@Slf4j
@RestController
public class SysLoginController {
    @Resource
    private SysLoginService loginService;

    @Resource
    private ISysMenuService menuService;

    @Resource
    private SysPermissionService permissionService;

    /**
     * 登录方法
     *
     * @param loginBody 登录信息
     * @return 结果
     */
    @PostMapping("/login")
    public AjaxResult login(@RequestBody LoginBody loginBody) {
        // 生成令牌
        String token = loginService.login(loginBody.getUsername(),
                loginBody.getPassword(),
                loginBody.getCode(),
                loginBody.getUuid());
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

    /**
     * 获取路由信息
     *
     * @return 路由信息
     */
    @GetMapping("getRouters")
    public AjaxResult getRouters() {
        Long userId = SecurityUtils.getUserId();
        List<SysMenu> menus = menuService.selectMenuTreeByUserId(userId);
        return AjaxResult.success(menuService.buildMenus(menus));
    }
}
