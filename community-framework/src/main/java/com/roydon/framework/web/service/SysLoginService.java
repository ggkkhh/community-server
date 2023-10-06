package com.roydon.framework.web.service;

import com.alibaba.fastjson2.JSONObject;
import com.roydon.common.constant.CacheConstants;
import com.roydon.common.constant.Constants;
import com.roydon.common.constant.QRCodeLoginConstants;
import com.roydon.common.core.domain.entity.SysUser;
import com.roydon.common.core.domain.model.LoginUser;
import com.roydon.common.core.redis.RedisCache;
import com.roydon.common.enums.LoginQRCodeStatus;
import com.roydon.common.exception.ServiceException;
import com.roydon.common.exception.alisms.SmsException;
import com.roydon.common.exception.user.CaptchaException;
import com.roydon.common.exception.user.CaptchaExpireException;
import com.roydon.common.exception.user.TelePhoneException;
import com.roydon.common.exception.user.UserPasswordNotMatchException;
import com.roydon.common.utils.*;
import com.roydon.common.utils.ip.IpUtils;
import com.roydon.common.utils.uuid.IdUtils;
import com.roydon.framework.config.smsconfig.SmsAuthenticationToken;
import com.roydon.framework.manager.AsyncManager;
import com.roydon.framework.manager.factory.AsyncFactory;
import com.roydon.framework.security.context.AuthenticationContextHolder;
import com.roydon.framework.web.domain.dto.LoginTicket;
import com.roydon.framework.web.domain.vo.QRCodeVO;
import com.roydon.qrcode.enums.ColorEnum;
import com.roydon.qrcode.util.QRCodeUtils;
import com.roydon.system.service.ISysConfigService;
import com.roydon.system.service.ISysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.security.auth.login.LoginException;
import java.util.concurrent.TimeUnit;

/**
 * 登录校验方法
 */
@Slf4j
@Service
public class SysLoginService {
    @Resource
    private TokenService tokenService;

    @Resource
    private AuthenticationManager authenticationManager;

    @Resource
    private RedisCache redisCache;

    @Resource
    private ISysUserService userService;

    @Resource
    private ISysConfigService configService;

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    @Resource
    private RabbitTemplate rabbitTemplate;

    /**
     * 登录验证
     *
     * @param username 用户名
     * @param password 密码
     * @param code     验证码
     * @param uuid     唯一标识
     * @return token
     */
    public String login(String username, String password, String code, String uuid) {
        boolean captchaEnabled = configService.selectCaptchaEnabled();
        // 验证码开关
        if (captchaEnabled) {
            validateCaptcha(username, code, uuid);
        }
        // 用户验证
        Authentication authentication = null;
        try {
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
            AuthenticationContextHolder.setContext(authenticationToken);
            // 该方法会去调用UserDetailsServiceImpl.loadUserByUsername
            authentication = authenticationManager.authenticate(authenticationToken);
        } catch (Exception e) {
            if (e instanceof BadCredentialsException) {
                // 异步记录日志
                AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("user.password.not.match")));
                throw new UserPasswordNotMatchException();
            } else {
                AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, e.getMessage()));
                throw new ServiceException(e.getMessage());
            }
        } finally {
            AuthenticationContextHolder.clearContext();
        }
        AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_SUCCESS, MessageUtils.message("user.login.success")));

        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        // 记录登录信息，修改用户表，添加登录IP、登录时间
        recordLoginInfo(loginUser.getUserId());
//        rabbitTemplate.convertAndSend(DirectRabbitConfig.DIRECT_EXCHANGE_NAME, DirectRabbitConfig.DIRECT_ROUTING_NAME, "登录成功");
        log.info("登录成功");
        // 生成token
        return tokenService.createToken(loginUser);
    }

    /**
     * app端账号密码登录
     *
     * @param username 用户名
     * @param password 密码
     * @return token
     */
    public String appUPLogin(String username, String password) {
        // 用户验证
        Authentication authentication = null;
        try {
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
            AuthenticationContextHolder.setContext(authenticationToken);
            // 该方法会去调用UserDetailsServiceImpl.loadUserByUsername
            authentication = authenticationManager.authenticate(authenticationToken);
        } catch (Exception e) {
            if (e instanceof BadCredentialsException) {
                // 异步记录日志
                AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("user.password.not.match")));
                throw new UserPasswordNotMatchException();
            } else {
                AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, e.getMessage()));
                throw new ServiceException(e.getMessage());
            }
        } finally {
            AuthenticationContextHolder.clearContext();
        }
        AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_SUCCESS, MessageUtils.message("user.login.success")));

        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        // 记录登录信息，修改用户表，添加登录IP、登录时间
        recordLoginInfo(loginUser.getUserId());
        // 生成token
        return tokenService.createToken(loginUser);
    }

    /**
     * 手机验证码登录
     *
     * @param telephone 手机号
     * @param phoneCode 验证码
     * @return token
     */
    public String smsLogin(String telephone, String phoneCode) {
        // 未携带手机号或验证码
        if (StringUtil.isEmpty(telephone)) {
            throw new TelePhoneException();
        }
        if (StringUtil.isEmpty(phoneCode)) {
            throw new CaptchaException();
        }
        // 获取手机验证码
        String verifyKey = CacheConstants.ALIYUN_SMS_LOGIN_KEY + telephone;
        String verifyPhoneCode = redisTemplate.opsForValue().get(verifyKey);
        if (StringUtil.isEmpty(verifyPhoneCode)) {
            throw new SmsException("验证码已失效");
        }
        if (!verifyPhoneCode.equals(phoneCode)) {
            throw new SmsException("验证码错误");
        }
        // 删除key
        redisTemplate.delete(verifyKey);
        // 通过手机号获取用户
        SysUser userByTelephone = userService.getUserByTelephone(telephone);
        if (StringUtil.isEmpty(userByTelephone)) {
            throw new TelePhoneException();
        }
        // 用户验证
        Authentication authentication = null;
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
        AsyncManager.me().execute(AsyncFactory.recordLogininfor(telephone, Constants.LOGIN_SUCCESS, MessageUtils.message("user.smsLogin.success")));

        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        // 记录登录信息，修改用户表，添加登录IP、登录时间
        recordLoginInfo(loginUser.getUserId());
        // 生成token
        return tokenService.createToken(loginUser);
    }

    /**
     * 校验验证码
     *
     * @param username 用户名
     * @param code     验证码
     * @param uuid     唯一标识
     */
    public void validateCaptcha(String username, String code, String uuid) {
        String verifyKey = CacheConstants.CAPTCHA_CODE_KEY + StringUtils.nvl(uuid, "");
        String captcha = redisCache.getCacheObject(verifyKey);
        redisCache.deleteObject(verifyKey);
        if (captcha == null) {
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("user.jcaptcha.expire")));
            throw new CaptchaExpireException();
        }
        if (!code.equalsIgnoreCase(captcha)) {
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("user.jcaptcha.error")));
            throw new CaptchaException();
        }
    }

    /**
     * 记录登录信息
     *
     * @param userId 用户ID
     */
    public void recordLoginInfo(Long userId) {
        SysUser sysUser = new SysUser();
        sysUser.setUserId(userId);
        sysUser.setLoginIp(IpUtils.getIpAddr(ServletUtils.getRequest()));
        sysUser.setLoginDate(DateUtils.getNowDate());
        userService.updateUserProfile(sysUser);
    }

    /**
     * 创建登录二维码
     *
     * @return base64格式登录二维码
     */
    public QRCodeVO createLoginQRCode() {
        // 创建登录二维码(base64格式)
        String randomUUID = IdUtils.randomUUID();
        String base64QRCode = QRCodeUtils.getBase64QRCode("http://127.0.0.1:8088/qr_login/uuid=" + randomUUID, ColorEnum.BLACK);
        String redisKey = QRCodeLoginConstants.LOGIN_QRCODE_KEY + randomUUID;
        // 链接缓存到redis
        LoginTicket loginTicket = new LoginTicket();
        loginTicket.setStatus(LoginQRCodeStatus.WAITING.getCode());
        redisTemplate.opsForValue().set(redisKey, JSONObject.toJSONString(loginTicket), QRCodeLoginConstants.LOGIN_QRCODE_EXPIRE, TimeUnit.SECONDS);
        QRCodeVO qrCodeVO = new QRCodeVO();
        qrCodeVO.setUuid(randomUUID);
        qrCodeVO.setQrcode(base64QRCode);
        return qrCodeVO;
    }

    /**
     * 获取二维码状态
     *
     * @param uuid
     * @param currentStatus
     * @return
     * @throws LoginException
     */
//    public String getLoginQrCodeStatus(String uuid, String currentStatus) throws LoginException {
//        String jsonStr = redisTemplate.opsForValue().get(QRCodeLoginConstants.LOGIN_QRCODE_KEY + uuid);
//        LoginTicket loginTicket = JSONObject.parseObject(jsonStr, LoginTicket.class);
//        if (StringUtil.isEmpty(loginTicket)) {
//            throw new LoginException("状态已过期");
//        }
//        if (loginTicket.getStatus().equals(LoginQRCodeStatus.SCANNED.getCode())) {
//            // 已扫描待确认
//            Long userId = loginTicket.getUserId();
//            SysUser sysUser = userService.selectUserById(userId);
//            return sysUser.getAvatar();
//        }
//        return loginTicket.getStatus();
//    }

    /**
     * 扫描二维码
     *
     * @param uuid
     * @return
     */
    public String scanQrCode(String uuid) {
        String redisKey = QRCodeLoginConstants.LOGIN_QRCODE_KEY + uuid;
        String jsonStr = redisTemplate.opsForValue().get(redisKey);
        LoginTicket loginTicket = JSONObject.parseObject(jsonStr, LoginTicket.class);
        if (StringUtil.isEmpty(loginTicket)) {
            return LoginQRCodeStatus.INVALID.getCode();
        }
        // 拿到token获取用户信息
        Long userId = SecurityUtils.getUserId();
        System.out.println("userId = " + userId);
        // 状态修改为已扫描
        loginTicket.setUserId(userId);
        loginTicket.setStatus(LoginQRCodeStatus.SCANNED.getCode());
        redisTemplate.opsForValue().set(redisKey, JSONObject.toJSONString(loginTicket), QRCodeLoginConstants.LOGIN_QRCODE_CONFIRM_EXPIRE, TimeUnit.SECONDS);
        return SecurityUtils.getLoginUser().getUser().getAvatar();
    }

    public String confirmLogin(String uuid) throws LoginException {
        String redisKey = QRCodeLoginConstants.LOGIN_QRCODE_KEY + uuid;
        String jsonStr = redisTemplate.opsForValue().get(redisKey);
        LoginTicket loginTicket = JSONObject.parseObject(jsonStr, LoginTicket.class);
        if (StringUtil.isEmpty(loginTicket)) {
            throw new LoginException("二维码已过期");
        }
        String telephone = SecurityUtils.getLoginUser().getUser().getPhonenumber();
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
        recordLoginInfo(loginUser.getUserId());
        // 状态修改为已登录
        loginTicket.setStatus(LoginQRCodeStatus.CONFIRMED.getCode());
        redisTemplate.opsForValue().set(redisKey, JSONObject.toJSONString(loginTicket));
        // 生成token
        return tokenService.createToken(loginUser);
    }
}
