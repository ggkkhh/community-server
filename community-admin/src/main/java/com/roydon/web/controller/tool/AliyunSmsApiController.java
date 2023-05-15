package com.roydon.web.controller.tool;

import com.roydon.common.core.domain.AjaxResult;
import com.roydon.sms.domain.model.SmsCode;
import com.roydon.sms.service.AliyunSmsService;
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

    /**
     * 短信发送
     *
     * @param phone
     * @return
     */
    @ApiOperation("指定号码发送短信")
    @GetMapping("/sendCode/{phone}")
    public AjaxResult sendCode(@PathVariable("phone") String phone) {
        SmsCode smsCode = aliyunSmsService.sendCode(phone);
        return AjaxResult.success(smsCode.getCode());
    }
}
