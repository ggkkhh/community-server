package com.roydon.business.news.config;

import com.roydon.common.enums.SecretKeyEnum;
import com.roydon.system.domain.SysSecret;
import com.roydon.system.service.ISysSecretService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * NewsConfig
 *
 * @AUTHOR: roydon
 * @DATE: 2023/5/13
 **/
@Component
public class NewsConfig {

    private static final String BASE_URL = "https://www.mxnzp.com/api/news/";

    @Resource
    private ISysSecretService sysSecretService;

    public String getNewsListUrl(String typeId, Integer page) {
        SysSecret sysSecret = sysSecretService.selectOneBySecretKey(SecretKeyEnum.ROLL.getInfo());
        return BASE_URL + "list?typeId=" + typeId + "&page=" + page + "&app_id=" + sysSecret.getKeyId() + "&app_secret=" + sysSecret.getKeySecret();
    }

    public String getNewsDetailsUrl(String newsId) {
        SysSecret sysSecret = sysSecretService.selectOneBySecretKey(SecretKeyEnum.ROLL.getInfo());
        return BASE_URL + "details?newsId=" + newsId + "&app_id=" + sysSecret.getKeyId() + "&app_secret=" + sysSecret.getKeySecret();
    }

}
