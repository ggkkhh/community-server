package com.roydon.sms.enums;

import com.roydon.common.exception.base.BaseException;

import java.util.HashMap;
import java.util.Map;


/**
 * @USER: roydon
 * @DATE: 2023/5/24
 * @Description sms短信模板类型
 **/
public enum TemplateType {

    AUTH_CODE("1", "验证码"),
    MESSAGE("2", "通知短信"),
    ;

    private final String typeId;
    private final String typeName;
    private static final Map<String, TemplateType> ENUM_MAP = new HashMap();

    TemplateType(String typeId, String typeName) {
        this.typeId = typeId;
        this.typeName = typeName;
    }

    public String getTypeId() {
        return typeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public static TemplateType fromValue(String valueType) {
        TemplateType enm = (TemplateType) ENUM_MAP.get(valueType);
        return enm;
    }

    protected static void registerEnum(TemplateType[] enums) {
        if (enums != null) {
            TemplateType[] var1 = enums;
            int var2 = enums.length;
            for (int var3 = 0; var3 < var2; ++var3) {
                TemplateType enm = var1[var3];
                String key = enm.getTypeId();
                TemplateType old = (TemplateType) ENUM_MAP.put(key, enm);
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
