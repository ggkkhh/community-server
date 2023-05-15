package com.roydon.framework.config.smsconfig;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;
import org.springframework.util.Assert;

import java.util.Collection;

/**
 * 短信登录令牌
 */
public class SmsAuthenticationToken extends AbstractAuthenticationToken {

    private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

    private final Object telephone;

    /**
     * SmsCodeAuthenticationFilter中构建的未认证的Authentication
     *
     * @param telephone
     */
    public SmsAuthenticationToken(Object telephone) {
        super(null);
        this.telephone = telephone;
        this.setAuthenticated(false);
    }

    /**
     * SmsCodeAuthenticationProvider中构建已认证的Authentication
     *
     * @param telephone
     * @param authorities
     */
    public SmsAuthenticationToken(Object telephone, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.telephone = telephone;
        super.setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return this.telephone;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        Assert.isTrue(!isAuthenticated, "Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
        super.setAuthenticated(false);
    }

    @Override
    public void eraseCredentials() {
        super.eraseCredentials();
    }
}