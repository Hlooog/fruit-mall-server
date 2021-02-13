package com.hl.fruitmall.common.enums;

import com.hl.fruitmall.common.interfaces.EnumInterface;

/**
 * @author Hl
 * @create 2021/2/12 16:19
 */
public enum OrderStatusEnum implements EnumInterface {
    UNPAID(0,"未支付"),
    PAID(1,"已支付"),
    SHIPPING(2, "已发货"),
    CONFIRM_RECEIPT(3, "交易完成"),
    REFUNDING(4, "退款中"),
    REFUND(5, "完成退款"),
    REFUSAL_TO_REFUND(6,"拒绝退款"),
    CANCEL(7,"交易关闭"),
    ;


    private Integer code;
    private String msg;

    OrderStatusEnum(Integer code, String msg) {
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
