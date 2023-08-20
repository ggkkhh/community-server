package com.roydon.common.constant;

/**
 * 缓存的 key 常量
 */
public class CacheConstants {
    /**
     * 登录用户 redis key
     */
    public static final String LOGIN_TOKEN_KEY = "login_tokens:";

    /**
     * 验证码 redis key
     */
    public static final String CAPTCHA_CODE_KEY = "captcha_codes:";

    /**
     * 参数管理 cache key
     */
    public static final String SYS_CONFIG_KEY = "sys_config:";

    /**
     * 字典管理 cache key
     */
    public static final String SYS_DICT_KEY = "sys_dict:";

    /**
     * 防重提交 redis key
     */
    public static final String REPEAT_SUBMIT_KEY = "repeat_submit:";

    /**
     * 限流 redis key
     */
    public static final String RATE_LIMIT_KEY = "rate_limit:";

    /**
     * 登录账户密码错误次数 redis key
     */
    public static final String PWD_ERR_CNT_KEY = "pwd_err_cnt:";

    /**
     * aliyun短信验证码key
     */
    public static final String ALIYUN_SMS_KEY = "sms:";

    /**
     * aliyun用户登录短信验证码key
     */
    public static final String ALIYUN_SMS_LOGIN_KEY = "sms:login_captcha:";

    /**
     * aliyun用户登录短信验证码key
     */
    public static final String ALIYUN_SMS_REGISTER_KEY = "sms:register_captcha:";

    /**
     * 新闻阅读量加一的业务key
     */
    public static final String NEWS_VIEW_NUM_KEY = "news:view_num";
    public static final Long NEWS_VIEW_NUM_KEY_TTL = 30L;

    /**
     * 热门新闻key
     */
    public static final String NEWS_HOT_NEWS = "news:hot_news";
    public static final Long NEWS_HOT_NEWS_TTL = 30L; // 过期时间30分钟
    public static final String NEWS_HOT_NEWS_LOCK_KEY = "news:hot_news:lock";

    /**
     * 缓存建立互斥锁
     */
    public static final Long CACHE_BUILD_LOCK_TTL = 10L;

    /**
     * app_user
     */
    public static final String APP_USER = "app:user:";

    /**
     * appNotice
     */
    public static final String APP_NOTICE_LIST = "app:notice:list";

    /**
     * 隔离天数自减一的key
     */
    public static final String EPIDEMIC_ISOLATION_TIME = "epidemic:isolation:time";


}
