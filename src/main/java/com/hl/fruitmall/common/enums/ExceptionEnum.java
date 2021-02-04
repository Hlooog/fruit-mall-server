package com.hl.fruitmall.common.enums;

/**
 * @author Hl
 * @create 2020/11/24 23:57
 */
public enum ExceptionEnum {
    SYSTEM_UNKNOW_EXCEPTION(10000, "系统未知异常"),
    INCORRECT_PASSWORD(10001, "密码错误"),
    FAIL_TO_EDIT(10002, "修改失败"),
    HAS_NOT_USER_RECORDS(10003, "没有找到用户信息"),
    ILLEGAL_OPERATION(10004, "非法操作"),
    TOKEN_INVALIDATION(10005, "token失效 请重新申请"),
    TOKEN_VERIFICATION_FAIL(10006, "token验证失败"),
    HAS_NOT_SHOP_RECORDS(10007, "没有找到店铺信息"),
    DELETE_USER_HAS_ERR(10008, "删除用户失败"),
    USER_HAS_BEEN_BAN(10009, "用户已经被封禁"),
    HAS_NOT_CITY_RECORDS(10010, "没有找到城市信息"),
    NOT_THE_CORRECT_TIME_TYPE(10011, "不是正确的时间类型"),
    SHOP_HAS_BEEN_BAN(10012, "店铺已经被封禁"),
    INSUFFICIENT_PERMISSIONS(10013, "权限不够"),
    OPERATION_FAILED(10014, "操作失败"),
    VERIFICATION_CODE_ERROR(10015, "验证码错误"),
    WITHDRAW_THAN_WITHDRAWABLE(10016, "提现金额大于可提现金额"),
    PHONE_NUMBER_HAS_ERROR(10017, "输入手机号码和店主手机号码不一致"),
    SPECIFICATION_CAN_NOT_NULL(10018, "请先添加规格在上架"),
    USER_EXIST(10020,"用户已经存在");


    private Integer code;
    private String msg;

    ExceptionEnum(Integer code, String msg) {
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
