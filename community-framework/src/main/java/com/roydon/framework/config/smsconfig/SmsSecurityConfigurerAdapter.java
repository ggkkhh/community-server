//package com.roydon.framework.config.smsconfig;
//
//import com.roydon.framework.config.smsconfig.handler.FailAuthenticationHandler;
//import com.roydon.framework.config.smsconfig.handler.SuccessAuthenticationHandler;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.web.DefaultSecurityFilterChain;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//import org.springframework.stereotype.Component;
//
///**
// * Author: LiuLin
// * Date: 2022/5/27 16:25
// * Description:
// */
//@Component
//public class SmsSecurityConfigurerAdapter extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {
//
//    @Autowired
//    private SuccessAuthenticationHandler successHandler;
//
//    @Autowired
//    private FailAuthenticationHandler failureHandler;
//
//    @Autowired
//    private SmsAuthenticationProvider smsAuthenticationProvider;
//
//    @Override
//    public void configure(HttpSecurity builder) throws Exception {
//
//        SmsAuthenticationFilter filter = new SmsAuthenticationFilter();
//        filter.setAuthenticationSuccessHandler(successHandler);
//        filter.setAuthenticationFailureHandler(failureHandler);
//        builder.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
//
//        filter.setAuthenticationManager(builder.getSharedObject(AuthenticationManager.class));
//
//        builder.authenticationProvider(smsAuthenticationProvider);
//
//        super.configure(builder);
//
//    }
//}