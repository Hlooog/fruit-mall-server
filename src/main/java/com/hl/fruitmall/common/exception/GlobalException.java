package com.hl.fruitmall.common.exception;


import com.hl.fruitmall.common.enums.ExceptionEnum;

/**
 * @author Hl
 * @create 2020/11/24 23:05
 */
public class GlobalException extends RuntimeException {
    private Integer code;
    private String msg;

    public GlobalException(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public GlobalException(ExceptionEnum exceptionEnum){
        this.code = exceptionEnum.getCode();
        this.msg = exceptionEnum.getMsg();
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
