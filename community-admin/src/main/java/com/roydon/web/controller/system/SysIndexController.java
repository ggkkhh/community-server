package com.roydon.web.controller.system;

import com.roydon.common.config.CommunityConfig;
import com.roydon.common.utils.StringUtils;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 首页
 */
@Api("后台系统首页")
@RestController
public class SysIndexController {
    /**
     * 系统基础配置
     */
    @Resource
    private CommunityConfig adminConfig;

    /**
     * 访问首页，提示语
     */
    @RequestMapping("/")
    public String index() {
        return StringUtils.format("欢迎使用{}后台管理框架，当前版本：v{}，请通过前端地址访问。", adminConfig.getName(), adminConfig.getVersion());
    }
}
