//package com.roydon.framework.config.smsconfig;
//
//import org.springframework.lang.Nullable;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.AuthenticationServiceException;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
//import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
//import org.springframework.util.Assert;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//
///**
// * 短信登录过滤器
// */
//public class SmsAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
//
//    private static final AntPathRequestMatcher DEFAULT_ANT_PATH_REQUEST_MATCHER = new AntPathRequestMatcher("/smsLogin", "POST");
//    // 认证参数
//    private String principalParameter = "telephone"; //对应手机号
//    private String credentialsParameter = "code"; //对应验证码
//    private boolean postOnly = true;
//
//    public SmsAuthenticationFilter() {
//        super(DEFAULT_ANT_PATH_REQUEST_MATCHER);
//    }
//
//    public SmsAuthenticationFilter(AuthenticationManager authenticationManager) {
//        super(DEFAULT_ANT_PATH_REQUEST_MATCHER, authenticationManager);
//    }
//
//    @Override
//    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
//        if (this.postOnly && !"POST".equals(request.getMethod())) {
//            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
//        } else {
//
//            String phone = this.obtainPrincipal(request);
//            phone = phone != null ? phone : "";
//            phone = phone.trim();
//            String code = this.obtainCredentials(request);
//            code = code != null ? code : "";
//
//            SmsAuthenticationToken authRequest = new SmsAuthenticationToken(phone, code);
//            this.setDetails(request, authRequest);
//            // 认证
//            return this.getAuthenticationManager().authenticate(authRequest);
//        }
//    }
//
//    @Nullable
//    protected String obtainCredentials(HttpServletRequest request) {
//        return request.getParameter(this.credentialsParameter);
//    }
//
//    @Nullable
//    protected String obtainPrincipal(HttpServletRequest request) {
//        return request.getParameter(this.principalParameter);
//    }
//
//
//    protected void setDetails(HttpServletRequest request, SmsAuthenticationToken authRequest) {
//        authRequest.setDetails(this.authenticationDetailsSource.buildDetails(request));
//    }
//
//
//    public void setPrincipalParameter(String principalParameter) {
//        Assert.hasText(principalParameter, "principal parameter must not be empty or null");
//        this.principalParameter = principalParameter;
//    }
//
//    public void setCredentialsParameter(String credentialsParameter) {
//        Assert.hasText(credentialsParameter, "credentials parameter must not be empty or null");
//        this.credentialsParameter = credentialsParameter;
//    }
//
//    public void setPostOnly(boolean postOnly) {
//        this.postOnly = postOnly;
//    }
//
//    public final String getPrincipalParameter() {
//        return this.principalParameter;
//    }
//
//    public final String getCredentialsParameter() {
//        return this.credentialsParameter;
//    }
//
//}