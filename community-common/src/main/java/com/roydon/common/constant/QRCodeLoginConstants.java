package com.roydon.common.constant;

/**
 * QRCodeLoginConstants
 *
 * @AUTHOR: roydon
 * @DATE: 2023/10/6
 **/
public class QRCodeLoginConstants {
    /**
     * 扫码登录二维码有效期 5 分钟
     */
    public static final long LOGIN_QRCODE_EXPIRE = 5 * 60L;
    /**
     * 待确认有效期一分钟
     */
    public static final long LOGIN_QRCODE_CONFIRM_EXPIRE =  60L;

    /**
     * 二维码链接
     */
    public static final String LOGIN_QRCODE_KEY = "login_qrcode:";

}
