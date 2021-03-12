package com.hl.fruitmall.common.enums;

/**
 * @author Hl
 * @create 2020/12/21 19:44
 */
public enum RedisKeyEnum {
    USER_LOGIN_KEY("USER_LOGIN_%s_%s"),                 // 用户id_手机号码
    CITY("REDIS_CITY_LIST"),
    WITHDRAW_CODE_KEY("WITHDRAW_CODE_%s"),
    LOGIN_CODE_KEY("LOGIN_CODE_%s"),
    WX_LOGIN_KEY("WX_LOGIN_KEY_%s"),
    CLOSE_SHOP_KEY("CLOSE_SHOP_KEY_%s"),
    UPDATE_PASSWORD_KEY("UPDATE_PASSWORD_KEY_%s"),
    CHAT_READ_RECORD_KEY("CHAT_RECORD_RECORD_%s"),
    CHAT_UNREAD_NUMBER_KEY("CHAT_UNREAD_RECORD"),
    SERVICE_LINK_USER_KEY("SERVICE_LINK"),
    COMMODITY_HASH("COMMODITY_HASH"),
    COMMODITY_Z_SET("COMMODITY_Z_SET"),
    VARIETY_KEY("VARIETY_%s"),
    VARIETY_SET("VARIETY_SET"),
    PRICE("PRICE"),
    PRICE_INTERVAL("PRICE:%s:%s"),
    BROWSE_RECORDS("BROWSE_RECORDS_%s"),
    MONTHLY_LIST("MONTHLY_LIST_%s"),
    BROWSE_RECORD_VARIETY("BROWSE_RECORDS_VARIETY_%s"),
    USER_KEEP_COMMODITY("USER_KEEP_COMMODITY_%s"),
    ANTI_REFRESH_WITHDRAW("ANTI_REFRESH_WITHDRAW")
    ;

    private String key;

    RedisKeyEnum(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
