package com.roydon.app.controller;

import com.roydon.app.domain.dto.UserRegisterBody;
import com.roydon.common.constant.CacheConstants;
import com.roydon.common.core.domain.AjaxResult;
import com.roydon.common.core.domain.entity.SysUser;
import com.roydon.common.exception.alisms.SmsException;
import com.roydon.common.utils.SecurityUtils;
import com.roydon.common.utils.StringUtil;
import com.roydon.common.utils.StringUtils;
import com.roydon.system.mapper.SysUserMapper;
import com.roydon.system.service.ISysUserService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @USER: roydon
 * @DATE: 2023/4/28 14:26
 * @Description RegisterController
 **/
@RestController
public class RegisterController {

    @Resource
    private ISysUserService userService;

    @Resource
    private SysUserMapper sysUserMapper;

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 用户使用手机号注册
     *
     * @param userRegisterBody
     * @return
     */
    @PostMapping("/register")
    public AjaxResult add(@RequestBody UserRegisterBody userRegisterBody) {
        if (StringUtil.isEmpty(userRegisterBody.getTelephone())) {
            return AjaxResult.error("新增用户失败，请输入手机号");
        } else if (StringUtils.isNotNull(sysUserMapper.checkPhoneUnique(userRegisterBody.getTelephone()))) {
            return AjaxResult.error("新增用户失败，手机号码'" + userRegisterBody.getTelephone() + "'已存在");
        } else if (StringUtils.isEmpty(userRegisterBody.getCode())) {
            return AjaxResult.error("新增用户失败，请输入验证码");
        }
        String phoneCode = redisTemplate.opsForValue().get(CacheConstants.ALIYUN_SMS_KEY + userRegisterBody.getTelephone());
        if (StringUtil.isEmpty(phoneCode)) {
            throw new SmsException("验证码已失效");
        }
        SysUser user = new SysUser();
        user.setPhonenumber(userRegisterBody.getTelephone());
        // 生成随机用户名
        user.setUserName(StringUtil.genRandomLengthStr(6));
        user.setNickName(StringUtil.genRandomChinese(6));
        // 手机号注册先默认密码是手机短信验证码
        user.setPassword(SecurityUtils.encryptPassword(phoneCode));
        return AjaxResult.success(userService.insertUser(user));
    }
}
