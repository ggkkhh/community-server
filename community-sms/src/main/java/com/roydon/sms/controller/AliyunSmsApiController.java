package com.roydon.sms.controller;

import com.roydon.common.core.domain.AjaxResult;
import com.roydon.sms.domain.model.AliSmsResponse;
import com.roydon.sms.domain.model.SmsCode;
import com.roydon.sms.service.AliyunSmsService;
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
    @GetMapping("/sendCode/{phone}")
    public AjaxResult sendCode(@PathVariable("phone") String phone) {

        SmsCode smsCode = aliyunSmsService.sendCode(phone);
//        String smsCode = redisTemplate.opsForValue().get(CacheConstants.ALIYUN_SMS_KEY + phone);

        // 如果redis 中根据手机号拿不到验证码，则生成6位随机验证码
//        code = UUID.randomUUID().toString().substring(0, 6);
//        Map<String, Object> codeMap = new HashMap<>();
//        codeMap.put(phone, code);
//
//        // 调用aliyunSendSmsService发送短信
//        Boolean bool = aliyunSendSmsService.sendMessage(phone, templateCode, codeMap);
//
//        if (bool) {
//            // 如果发送成功，则将生成的4位随机验证码存入redis缓存,5分钟后过期
//            redisTemplate.opsForValue().set(phone, code, 1, TimeUnit.MINUTES);
//            return AjaxResult.success("发送成功", codeMap);
//        } else {
//            return AjaxResult.error("发送失败");
//        }

        return AjaxResult.success(smsCode.getCode());
    }
}
