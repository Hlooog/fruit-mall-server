package com.hl.fruitmall.common.enums;

import com.hl.fruitmall.common.interfaces.EnumInterface;

/**
 * @author Hl
 * @create 2021/1/25 22:41
 */
public enum ReviewStatusEnums implements EnumInterface {
    UN_REVIEWED(0,"未审核"),
    REVIEWED(1,"通过审核"),
    REFUSE(2,"不通过审核")
    ;

    private Integer code;
    private String msg;

    ReviewStatusEnums(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getMsg() {
        return msg;
    }
}
