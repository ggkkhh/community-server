package com.roydon.business.news.enums;

import com.roydon.common.exception.base.BaseException;

import java.util.HashMap;
import java.util.Map;

/**
 * NewsType
 *
 * @AUTHOR: roydon
 * @DATE: 2023/5/13
 **/
public enum NewsType {

    FINANCE(509, "财经"),
    SCIENCE(510, "科技"),
    MILITARY(511, "军事"),
    FASHION(512, "时尚"),
    LOF(514, "股票"),
    HEALTH(516, "健康"),
    REVIEW(518, "要闻"),
    SPORT(519, "体育"),
    ENTERTAINMENT(520, "娱乐"),
    HEADLINE(521, "头条"),
    HOTSPOT(525, "热点");

    private final Integer typeId;
    private final String typeName;
    private static final Map<Integer, NewsType> ENUM_MAP = new HashMap();

    NewsType(Integer typeId, String typeName) {
        this.typeId = typeId;
        this.typeName = typeName;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public static NewsType fromValue(int valueType) {
        NewsType enm = (NewsType) ENUM_MAP.get(valueType);
        return enm;
    }

    protected static void registerEnum(NewsType[] enums) {
        if (enums != null) {
            NewsType[] var1 = enums;
            int var2 = enums.length;

            for (int var3 = 0; var3 < var2; ++var3) {
                NewsType enm = var1[var3];
                int key = enm.getTypeId();
                NewsType old = (NewsType) ENUM_MAP.put(key, enm);
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
