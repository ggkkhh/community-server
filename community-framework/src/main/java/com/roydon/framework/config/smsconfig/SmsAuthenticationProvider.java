package com.roydon.framework.config.smsconfig;

import cn.hutool.core.util.StrUtil;
import com.roydon.common.constant.CacheConstants;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticatedPrincipal;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.security.Principal;

/**
 * 短信登录校验器
 */
@Component
public class SmsAuthenticationProvider implements AuthenticationProvider {

    private SmsUserDetailsService smsUserDetailsService;

    public SmsAuthenticationProvider(@Qualifier("smsUserDetailsService") SmsUserDetailsService smsUserDetailsService) {
        this.smsUserDetailsService = smsUserDetailsService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        SmsAuthenticationToken authenticationToken = (SmsAuthenticationToken) authentication;
        String telephone =(String) authenticationToken.getPrincipal();// 获取凭证也就是用户的手机号
//        if (principal instanceof UserDetails) {
//            mobile = ((UserDetails) principal).getUsername();
//        } else if (principal instanceof AuthenticatedPrincipal) {
//            mobile = ((AuthenticatedPrincipal) principal).getName();
//        } else if (principal instanceof Principal) {
//            mobile = ((Principal) principal).getName();
//        } else {
//            mobile = principal == null ? "" : principal.toString();
//        }
//        String inputCode = (String) authentication.getCredentials(); // 获取输入的验证码
//        // 1. 根据手机号查询用户信息
        UserDetails userDetails = smsUserDetailsService.loadUserByUsername(telephone);
//        if (userDetails == null) {
//            throw new InternalAuthenticationServiceException("用户不存在");
//        }
//        // 2. 检验Redis手机号的验证码
//        String verifyKey = CacheConstants.ALIYUN_SMS_KEY + telephone;
//        String redisCode = redisTemplate.opsForValue().get(verifyKey);
//        if (StrUtil.isEmpty(redisCode)) {
//            throw new BadCredentialsException("验证码已经过期或尚未发送，请重新发送验证码");
//        }
//        if (!inputCode.equals(redisCode)) {
//            throw new BadCredentialsException("验证码不正确，请重新输入");
//        }
//        redisTemplate.delete(verifyKey);//用完即删
        SmsAuthenticationToken smsAuthenticationToken = new SmsAuthenticationToken(userDetails, userDetails.getAuthorities());
        smsAuthenticationToken.setDetails(authenticationToken.getDetails());
        return smsAuthenticationToken;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return SmsAuthenticationToken.class.isAssignableFrom(authentication);
    }

    public SmsUserDetailsService getSmsUserDetailsService() {
        return smsUserDetailsService;
    }

    public void setSmsUserDetailsService(SmsUserDetailsService smsUserDetailsService) {
        this.smsUserDetailsService = smsUserDetailsService;
    }
}