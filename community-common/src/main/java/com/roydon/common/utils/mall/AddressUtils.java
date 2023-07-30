package com.roydon.common.utils.mall;

import com.roydon.common.enums.AddressTypeEnum;

/**
 * @author roydon
 * @date 2023-07-27 22:27【星期四】
 * @description com.roydon.common.utils.mall
 * <p> 地址操作工具类 </p>
 **/
public class AddressUtils {

    public static void main(String[] args) throws Exception {
        String str = "411481";
        // 省
        String format = String.format("%s0000", str.substring(0, 2));
        System.out.println("format = " + getProvinceCode(str, AddressTypeEnum.PROVINCE));
        // 市
        String format1 = String.format("%s00", str.substring(0, 4));
        System.out.println("format1 = " + format1);
        // 区
        String format2 = String.format("%s", str.substring(0, 6));
        System.out.println("format2 = " + format2);
    }

    public static String getProvinceCode(String code, AddressTypeEnum addressType) throws Exception {
        if (code.length() != 6) {
            throw new Exception("this code`s length is not match 6");
        }
        if (addressType.equals(AddressTypeEnum.PROVINCE)) {
            // 省
            return String.format("%s0000", code.substring(0, 2));
        } else if (addressType.equals(AddressTypeEnum.CITY)) {
            // 市
            return String.format("%s00", code.substring(0, 4));
        } else if (addressType.equals(AddressTypeEnum.REGION)) {
            // 区
            return String.format("%s", code.substring(0, 6));
        }
        return code;
    }

}
