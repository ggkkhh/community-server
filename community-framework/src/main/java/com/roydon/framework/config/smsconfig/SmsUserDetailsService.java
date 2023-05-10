package com.roydon.framework.config.smsconfig;

import com.roydon.common.core.domain.entity.SysUser;
import com.roydon.common.core.domain.model.LoginUser;
import com.roydon.common.enums.UserStatus;
import com.roydon.common.exception.ServiceException;
import com.roydon.common.utils.StringUtils;
import com.roydon.framework.web.service.SysPermissionService;
import com.roydon.system.service.ISysUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 查询短信登录信息并封装为 UserDetails 这里可以抽取一个抽象类，权限加载和校验租户等逻辑交给父类处理
 */
@Service("smsUserDetailsService")
public class SmsUserDetailsService implements UserDetailsService {
    private static final Logger log = LoggerFactory.getLogger(SmsUserDetailsService.class);

    @Resource
    private ISysUserService userService;

    @Resource
    private SysPermissionService permissionService;

    /**
     * loadUserByUsername
     *
     * @param phone
     * @return LoginUser
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String phone) throws UsernameNotFoundException {
        SysUser user = userService.getUserByTelephone(phone);
        if (StringUtils.isNull(user)) {
            log.info("手机号：{} 不存在.", phone);
            throw new InternalAuthenticationServiceException("手机号：" + phone + " 不存在");
        } else if (UserStatus.DELETED.getCode().equals(user.getDelFlag())) {
            log.info("登录用户：{} 已被删除.", phone);
            throw new ServiceException("对不起，您的账号：" + phone + " 已被删除");
        } else if (UserStatus.DISABLE.getCode().equals(user.getStatus())) {
            log.info("登录用户：{} 已被停用.", phone);
            throw new DisabledException("对不起，您的账号：" + phone + " 已停用");
        }
        return createLoginUser(user);
    }

    public UserDetails createLoginUser(SysUser user) {
        return new LoginUser(user.getUserId(), user.getDeptId(), user, permissionService.getMenuPermission(user));
    }

}