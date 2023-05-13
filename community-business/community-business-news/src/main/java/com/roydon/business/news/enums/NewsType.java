package com.roydon.business.news.enums;

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
    HOTSPOT(525, "热点")
    ;

    private final Integer typeId;
    private final String typeName;

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



}
