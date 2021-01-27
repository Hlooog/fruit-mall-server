package com.hl.fruitmall.common.enums;

import com.hl.fruitmall.common.interfaces.EnumInterface;

/**
 * @author Hl
 * @create 2020/12/2 0:37
 */
public enum RoleEnum implements EnumInterface {
    ADMIN(0,"管理员"),
    CUSTOMER_SERVICE(1,"客服"),
    MERCHANT(2,"商家"),
    USER(3,"普通用户")
    ;

    private Integer code;
    private String msg;

    RoleEnum(Integer code, String msg){
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
