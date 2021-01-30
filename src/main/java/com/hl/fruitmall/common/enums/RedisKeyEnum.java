package com.hl.fruitmall.common.enums;

/**
 * @author Hl
 * @create 2020/12/21 19:44
 */
public enum RedisKeyEnum {
    USER_LOGIN_KEY("USER_LOGIN_%s_%s"),                 // 用户id_手机号码
    CITY("redis_city_info"),
    WITHDRAW_CODE_KEY("WITHDRAW_CODE_%s")
    ;

    private String key;

    RedisKeyEnum(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
