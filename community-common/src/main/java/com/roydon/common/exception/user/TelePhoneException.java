package com.roydon.common.exception.user;

/**
 * @USER: roydon
 * @DATE: 2023/5/9 10:13
 * @Description 手机号
 **/
public class TelePhoneException extends UserException {
    private static final long serialVersionUID = 1L;

    public TelePhoneException() {
        super("user.telephone.error", null);
    }
}
