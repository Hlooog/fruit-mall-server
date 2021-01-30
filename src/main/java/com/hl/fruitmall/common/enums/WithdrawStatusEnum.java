package com.hl.fruitmall.common.enums;

import com.hl.fruitmall.common.interfaces.EnumInterface;

/**
 * @author Hl
 * @create 2021/1/30 16:56
 */
public enum  WithdrawStatusEnum implements EnumInterface {
    REVIEW(0, "审核中"),
    MARKING_MONEY(1, "打款中"),
    FINISH(2, "完成提现")
    ;

    private Integer code;
    private String msg;

    WithdrawStatusEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public Integer getCode() {
        return this.code;
    }

    @Override
    public String getMsg() {
        return this.msg;
    }
}
