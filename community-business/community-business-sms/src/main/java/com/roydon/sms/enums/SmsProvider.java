package com.roydon.sms.enums;

import com.roydon.common.exception.base.BaseException;

import java.util.HashMap;
import java.util.Map;

/**
 * @USER: roydon
 * @DATE: 2023/5/24 14:39
 * @Description sms短信服务供应商
 **/
public enum SmsProvider {

    ALIYUN_SMS("1", "阿里云sms"),
    TENCENTUUN_SMS("2", "腾讯云sms"),
    ;

    private final String typeId;
    private final String typeName;
    private static final Map<String, SmsProvider> ENUM_MAP = new HashMap();

    SmsProvider(String typeId, String typeName) {
        this.typeId = typeId;
        this.typeName = typeName;
    }

    public String getTypeId() {
        return typeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public static SmsProvider fromValue(String valueType) {
        SmsProvider enm = (SmsProvider) ENUM_MAP.get(valueType);
        return enm;
    }

    protected static void registerEnum(SmsProvider[] enums) {
        if (enums != null) {
            SmsProvider[] var1 = enums;
            int var2 = enums.length;
            for (int var3 = 0; var3 < var2; ++var3) {
                SmsProvider enm = var1[var3];
                String key = enm.getTypeId();
                SmsProvider old = (SmsProvider) ENUM_MAP.put(key, enm);
                if (old != null) {
                    throw new BaseException("Repeated value:" + old.name());
                }
            }
        }

    }

    static {
        registerEnum(values());
    }
}
