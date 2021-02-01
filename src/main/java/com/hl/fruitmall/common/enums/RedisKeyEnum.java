package com.hl.fruitmall.common.enums;

/**
 * @author Hl
 * @create 2020/12/21 19:44
 */
public enum RedisKeyEnum {
    USER_LOGIN_KEY("USER_LOGIN_%s_%s"),                 // 用户id_手机号码
    CITY("REDIS_CITY_LIST"),
    WITHDRAW_CODE_KEY("WITHDRAW_CODE_%s"),
    CHAT_READ_RECORD_KEY("CHAT_RECORD_RECORD_%s"),
    CHAT_UNREAD_NUMBER_KEY("CHAT_UNREAD_RECORD"),
    SERVICE_LINK_USER("SERVICE_LINK")
    ;

    private String key;

    RedisKeyEnum(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
