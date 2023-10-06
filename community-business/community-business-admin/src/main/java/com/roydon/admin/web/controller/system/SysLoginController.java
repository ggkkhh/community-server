package com.roydon.admin.web.controller.system;

import com.alibaba.fastjson2.JSONObject;
import com.roydon.app.domain.dto.SmsLoginBody;
import com.roydon.common.constant.Constants;
import com.roydon.common.constant.QRCodeLoginConstants;
import com.roydon.common.core.domain.AjaxResult;
import com.roydon.common.core.domain.entity.SysMenu;
import com.roydon.common.core.domain.entity.SysUser;
import com.roydon.common.core.domain.model.LoginBody;
import com.roydon.common.enums.LoginQRCodeStatus;
import com.roydon.common.utils.SecurityUtils;
import com.roydon.common.utils.StringUtil;
import com.roydon.framework.web.domain.dto.LoginTicket;
import com.roydon.framework.web.domain.vo.QRCodeVO;
import com.roydon.framework.web.service.SysLoginService;
import com.roydon.framework.web.service.SysPermissionService;
import com.roydon.system.service.ISysMenuService;
import com.roydon.system.service.ISysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.security.auth.login.LoginException;
import java.util.List;
import java.util.Set;

/**
 * 登录验证
 */
@Api("登录系统")
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
    @ApiOperation("账号密码登录")
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
     * @param smsLoginBody 短信登陆体 {String telephone;String phoneCode;}
     * @return 结果
     */
    @ApiOperation("短信登陆")
    @PostMapping("/sms-login")
    public AjaxResult smsLogin(@RequestBody SmsLoginBody smsLoginBody) {
        // 生成令牌
        log.info("手机验证码登录：{}", smsLoginBody.getTelephone());
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

    /**
     * 获取登录二维码
     */
    @GetMapping("getLoginQRCode")
    public AjaxResult getLoginQRCode() {
        QRCodeVO loginQRCode = loginService.createLoginQRCode();
        return AjaxResult.success(loginQRCode);
    }

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    @Resource
    private ISysUserService userService;

    /**
     * 获取二维码状态
     *
     * @param uuid
     * @param currentStatus
     * @return
     * @throws InterruptedException
     */
    @GetMapping("getLoginQrCodeStatus")
    public AjaxResult getQrCodeStatus(@RequestParam String uuid, @RequestParam String currentStatus) throws LoginException {
//        String status = loginService.getLoginQrCodeStatus(uuid, currentStatus);
        AjaxResult ajaxResult = AjaxResult.success();
        String jsonStr = redisTemplate.opsForValue().get(QRCodeLoginConstants.LOGIN_QRCODE_KEY + uuid);
        LoginTicket loginTicket = JSONObject.parseObject(jsonStr, LoginTicket.class);
        if (StringUtil.isEmpty(loginTicket)) {
            throw new LoginException("二维码状态已过期");
        }
        if (loginTicket.getStatus().equals(LoginQRCodeStatus.SCANNED.getCode())) {
            // 已扫描待确认
            Long userId = loginTicket.getUserId();
            SysUser sysUser = userService.selectUserById(userId);
            ajaxResult.put("avatar", sysUser.getAvatar());
        }
        ajaxResult.put("status", loginTicket.getStatus());
        return ajaxResult;
    }

    @GetMapping("scan")
    public AjaxResult scanQrCodeImg(@RequestParam String uuid) {
        String state = loginService.scanQrCode(uuid);
        return AjaxResult.success(state);
    }

    @GetMapping("confirm")
    public AjaxResult confirmLogin(@RequestParam String uuid) throws LoginException {
        String token = loginService.confirmLogin(uuid);
        return AjaxResult.success().put(Constants.TOKEN, token);
    }

}
