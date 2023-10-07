package com.roydon.admin.web.controller.system;

import com.alibaba.fastjson2.JSONObject;
import com.roydon.app.domain.dto.SmsLoginBody;
import com.roydon.common.constant.Constants;
import com.roydon.common.constant.QRCodeLoginConstants;
import com.roydon.common.core.domain.AjaxResult;
import com.roydon.common.core.domain.entity.SysMenu;
import com.roydon.common.core.domain.entity.SysUser;
import com.roydon.common.core.domain.model.LoginBody;
import com.roydon.common.core.domain.model.LoginUser;
import com.roydon.common.enums.LoginQRCodeStatus;
import com.roydon.common.exception.ServiceException;
import com.roydon.common.exception.user.UserPasswordNotMatchException;
import com.roydon.common.utils.MessageUtils;
import com.roydon.common.utils.SecurityUtils;
import com.roydon.common.utils.StringUtil;
import com.roydon.framework.config.smsconfig.SmsAuthenticationToken;
import com.roydon.framework.manager.AsyncManager;
import com.roydon.framework.manager.factory.AsyncFactory;
import com.roydon.framework.security.context.AuthenticationContextHolder;
import com.roydon.framework.web.domain.dto.LoginTicket;
import com.roydon.framework.web.domain.vo.QRCodeVO;
import com.roydon.framework.web.service.SysLoginService;
import com.roydon.framework.web.service.SysPermissionService;
import com.roydon.framework.web.service.TokenService;
import com.roydon.system.service.ISysMenuService;
import com.roydon.system.service.ISysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
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

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    @Resource
    private ISysUserService userService;

    @Resource
    private AuthenticationManager authenticationManager;

    @Resource
    private TokenService tokenService;

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
    @ApiOperation("获取用户信息")
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
    @ApiOperation("获取路由信息")
    @GetMapping("getRouters")
    public AjaxResult getRouters() {
        Long userId = SecurityUtils.getUserId();
        List<SysMenu> menus = menuService.selectMenuTreeByUserId(userId);
        return AjaxResult.success(menuService.buildMenus(menus));
    }

    /**
     * 获取登录二维码
     */
    @ApiOperation("获取登录二维码")
    @GetMapping("getLoginQRCode")
    public AjaxResult getLoginQRCode() {
        QRCodeVO loginQRCode = loginService.createLoginQRCode();
        return AjaxResult.success(loginQRCode);
    }

    /**
     * 获取二维码状态
     */
    @ApiOperation("获取登录二维码状态")
    @GetMapping("getLoginQrCodeStatus")
    public AjaxResult getQrCodeStatus(@RequestParam String uuid, @RequestParam String currentStatus) throws LoginException {
//        String status = loginService.getLoginQrCodeStatus(uuid, currentStatus);
        AjaxResult ajaxResult = AjaxResult.success();
        String token = null;
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
        if (loginTicket.getStatus().equals(LoginQRCodeStatus.CONFIRMED.getCode())) {
            // 已确认登陆
            Long userId = loginTicket.getUserId();
            SysUser sysUser = userService.selectUserById(userId);
            String telephone = sysUser.getPhonenumber();
            // 用户验证
            Authentication authentication;
            // 用户验证
            try {
                SmsAuthenticationToken authenticationToken = new SmsAuthenticationToken(telephone);
                AuthenticationContextHolder.setContext(authenticationToken);
                // 该方法会去调用 SmsUserDetailsService.loadUserByUsername
                authentication = authenticationManager.authenticate(authenticationToken);
            } catch (Exception e) {
                if (e instanceof BadCredentialsException) {
                    // 异步记录日志
                    AsyncManager.me().execute(AsyncFactory.recordLogininfor(telephone, Constants.LOGIN_FAIL, MessageUtils.message("user.smsLogin.error")));
                    throw new UserPasswordNotMatchException();
                } else {
                    AsyncManager.me().execute(AsyncFactory.recordLogininfor(telephone, Constants.LOGIN_FAIL, e.getMessage()));
                    throw new ServiceException(e.getMessage());
                }
            } finally {
                AuthenticationContextHolder.clearContext();
            }
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(telephone, Constants.LOGIN_SUCCESS, MessageUtils.message("user.login.success")));

            LoginUser loginUser = (LoginUser) authentication.getPrincipal();
            // 记录登录信息，修改用户表，添加登录IP、登录时间
            loginService.recordLoginInfo(loginUser.getUserId());
            // 生成token
            token = tokenService.createToken(loginUser);
        }
        ajaxResult.put("status", loginTicket.getStatus());
        if (token != null) {
            ajaxResult.put("token", token);
        }
        return ajaxResult;
    }

    /**
     * 扫码
     */
    @ApiOperation("移动端扫码")
    @GetMapping("scan")
    public AjaxResult scanQrCodeImg(@RequestParam String uuid) {
        String state = loginService.scanQrCode(uuid);
        return AjaxResult.success(state);
    }

    /**
     * 确认登陆
     */
    @ApiOperation("移动端确认登录")
    @GetMapping("confirm")
    public AjaxResult confirmLogin(@RequestParam String uuid) throws LoginException {
        String state = loginService.confirmLogin(uuid);
        return AjaxResult.success(state);
    }

}
