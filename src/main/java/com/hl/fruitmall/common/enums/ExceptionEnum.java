package com.hl.fruitmall.common.enums;

/**
 * @author Hl
 * @create 2020/11/24 23:57
 */
public enum ExceptionEnum {
    SYSTEM_UNKNOW_EXCEPTION(10000, "系统未知异常"),
    INCORRECT_PASSWORD(10001, "密码错误"),
    LOGIN_HAS_NOT_PERMISSION(10002, "你不是管理员"),
    FAIL_TO_EDIT(10003, "修改失败"),
    HAS_NOT_USER_RECORDS(10004, "没有找到用户信息"),
    ILLEGAL_OPERATION(10005, "非法操作"),
    TOKEN_INVALIDATION(10006, "token失效 请重新申请"),
    TOKEN_VERIFICATION_FAIL(10007, "token验证失败"),
    HAS_NOT_SHOP_RECORDS(10008, "没有找到店铺信息"),
    DELETE_USER_HAS_ERR(10009, "删除用户失败"),
    USER_HAS_BEEN_BAN(10010, "用户已经被封禁"),
    HAS_NOT_CITY_RECORDS(10011, "没有找到城市信息"),
    NOT_THE_CORRECT_TIME_TYPE(10012, "不是正确的时间类型"),
    SHOP_HAS_BEEN_BAN(10013,"店铺已经被永久封禁"),
    INSUFFICIENT_PERMISSIONS(10014,"权限不够"),
    OPERATION_FAILED(10015,"操作失败")
    ;
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
