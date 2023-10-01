package com.roydon.framework.web.service;

import com.roydon.common.constant.CacheConstants;
import com.roydon.common.constant.Constants;
import com.roydon.common.core.domain.entity.SysUser;
import com.roydon.common.core.domain.model.LoginUser;
import com.roydon.common.core.redis.RedisCache;
import com.roydon.common.exception.ServiceException;
import com.roydon.common.exception.alisms.SmsException;
import com.roydon.common.exception.user.CaptchaException;
import com.roydon.common.exception.user.CaptchaExpireException;
import com.roydon.common.exception.user.TelePhoneException;
import com.roydon.common.exception.user.UserPasswordNotMatchException;
import com.roydon.common.utils.*;
import com.roydon.common.utils.ip.IpUtils;
import com.roydon.framework.config.smsconfig.SmsAuthenticationToken;
import com.roydon.framework.manager.AsyncManager;
import com.roydon.framework.manager.factory.AsyncFactory;
import com.roydon.framework.security.context.AuthenticationContextHolder;
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
}
