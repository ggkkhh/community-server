package com.roydon.framework.config.smsconfig;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * 短信登录校验器
 */
public class SmsAuthenticationProvider implements AuthenticationProvider {

    private UserDetailsService userDetailsService;

    public SmsAuthenticationProvider(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    /**
     * 重写 authenticate方法，实现身份验证逻辑。
     *
     * @param authentication
     * @return
     * @throws AuthenticationException
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        SmsAuthenticationToken authenticationToken = (SmsAuthenticationToken) authentication;
        String telephone =(String) authenticationToken.getPrincipal();// 获取凭证也就是用户的手机号
        UserDetails userDetails = userDetailsService.loadUserByUsername(telephone);
        if (userDetails == null) {
            throw new InternalAuthenticationServiceException("用户不存在");
        }
        SmsAuthenticationToken smsAuthenticationToken = new SmsAuthenticationToken(userDetails, userDetails.getAuthorities());
        smsAuthenticationToken.setDetails(authenticationToken.getDetails());
        return smsAuthenticationToken;
    }

    /**
     * 重写supports方法，指定此 AuthenticationProvider 仅支持短信验证码身份验证。
     *
     * @param authentication
     * @return
     */
    @Override
    public boolean supports(Class<?> authentication) {
        return SmsAuthenticationToken.class.isAssignableFrom(authentication);
    }

    public UserDetailsService getUserDetailsService() {
        return userDetailsService;
    }

    public void setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }
}
