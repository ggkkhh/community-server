package com.roydon.sms.controller;

import com.roydon.common.core.domain.AjaxResult;
import com.roydon.common.utils.PhoneUtils;
import com.roydon.common.utils.StringUtil;
import com.roydon.sms.domain.model.SmsCode;
import com.roydon.sms.service.AliyunSmsService;
import com.roydon.system.service.ISysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Description: 阿里云SMS短信发送API
 */
@Api("aliyun短信服务")
@RestController
@RequestMapping("/sms")
public class AliyunSmsApiController {

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    @Value("${aliyun.sms.templateCode}")
    private String templateCode;

    @Resource
    private AliyunSmsService aliyunSmsService;

    @Resource
    private ISysUserService sysUserService;

    /**
     * 短信发送
     *
     * @param phone 电话
     * @return code
     */
    @ApiOperation("指定号码发送短信")
    @GetMapping("/sendCode/{phone}")
    public AjaxResult sendCode(@PathVariable("phone") String phone) {
        if (!PhoneUtils.isMobile(phone)) {
            return AjaxResult.error("请输入正确的手机号");
        }
        SmsCode smsCode = aliyunSmsService.sendCode(phone);
        return AjaxResult.success(smsCode.getCode());
    }

    /**
     * 发送短信注册码
     *
     * @param phone 电话
     * @return code
     */
    @ApiOperation("指定号码发送注册短信")
    @GetMapping("/registerCode/{phone}")
    public AjaxResult registerCode(@PathVariable("phone") String phone) {
        if (StringUtil.isNotEmpty(sysUserService.checkTelephoneExists(phone))) {
            return AjaxResult.error("此手机号已注册");
        }
        if (!PhoneUtils.isMobile(phone)) {
            return AjaxResult.error("请输入正确的手机号");
        }
        SmsCode smsCode = aliyunSmsService.sendRegisterCode(phone);
        return AjaxResult.success(smsCode.getCode());
    }
}
