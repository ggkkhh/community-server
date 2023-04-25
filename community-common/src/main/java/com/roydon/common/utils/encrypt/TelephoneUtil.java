package com.roydon.common.utils.encrypt;

import com.roydon.common.utils.StringUtils;

/**
 * @USER: roydon
 * @DATE: 2023/4/17 10:34
 * @Description 手机号加密工具类 182****4706
 **/
public class TelephoneUtil {

    private static final int START_INDEX = 3;
    private static final int TELEPHONE_PLAIN_TEXT_LENGTH = 4;
    private static final char ASTERISK = '*';

    public static String replaceSomeCharByAsterisk(String telephone) {
        if (StringUtils.isEmpty(telephone)) {
            return telephone;
        }
        char[] chars = telephone.toCharArray();
        for (int i = START_INDEX; i < chars.length - TELEPHONE_PLAIN_TEXT_LENGTH; i++) {
            chars[i] = ASTERISK;
        }
        String result = new String(chars);
        return result;
    }
}
