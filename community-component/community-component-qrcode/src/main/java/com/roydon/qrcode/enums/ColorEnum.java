package com.roydon.qrcode.enums;

/**
 * @author roydon
 * @date 2023-08-08 15:54【星期二】
 * @description com.roydon.qrcode.enums
 * <p> 二维码颜色枚举 </p>
 **/
public enum ColorEnum {

    BLACK(0x00000000, "黑"),
    WHITE(0xFFFFFFFF, "白"),
    GREEN(0x00008000, "绿"),
    YELLOW(0x00ffff00, "黄"),
    RED(0x00FF0000, "红"),
    ;

    private final int hexCode;
    private final String color;

    ColorEnum(int hexCode, String color) {
        this.hexCode = hexCode;
        this.color = color;
    }

    public int getHexCode() {
        return hexCode;
    }

    public String getColor() {
        return color;
    }

    public static ColorEnum find(String color) {
        for (ColorEnum value : ColorEnum.values()) {
            if (color.equals(value.getColor())) {
                return value;
            }
        }
        return null;
    }
}
