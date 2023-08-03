package com.roydon.openai.config;

import com.roydon.openai.util.OpenAiUtils;
import lombok.Data;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "openai.gpt")
public class OpenAiProperties implements InitializingBean {
    // 秘钥
    @Value("${openai.gpt.token}")
    private String token;
    // 超时时间
    @Value("${openai.gpt.timeout}")
    private Integer timeout;

    // 设置属性时同时设置给OpenAiUtils
    @Override
    public void afterPropertiesSet() throws Exception {
        OpenAiUtils.OPENAPI_TOKEN = token;
        OpenAiUtils.TIMEOUT = timeout;
    }
}
