package com.roydon.sms.enums;

import com.roydon.common.exception.base.BaseException;

import java.util.HashMap;
import java.util.Map;

/**
 * @USER: roydon
 * @DATE: 2023/5/24 14:39
 * @Description sms短信发送类型
 **/
public enum SmsSendType {

    LOGIN_AUTH_CODE("1", "登录验证码"),
    REGISTER_AUTH_CODE("2", "注册验证码"),
    MESSAGE_CODE("3", "消息通知"),
    ;

    private final String typeId;
    private final String typeName;
    private static final Map<String, SmsSendType> ENUM_MAP = new HashMap();

    SmsSendType(String typeId, String typeName) {
        this.typeId = typeId;
        this.typeName = typeName;
    }

    public String getTypeId() {
        return typeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public static SmsSendType fromValue(String valueType) {
        SmsSendType enm = (SmsSendType) ENUM_MAP.get(valueType);
        return enm;
    }

    protected static void registerEnum(SmsSendType[] enums) {
        if (enums != null) {
            SmsSendType[] var1 = enums;
            int var2 = enums.length;
            for (int var3 = 0; var3 < var2; ++var3) {
                SmsSendType enm = var1[var3];
                String key = enm.getTypeId();
                SmsSendType old = (SmsSendType) ENUM_MAP.put(key, enm);
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
