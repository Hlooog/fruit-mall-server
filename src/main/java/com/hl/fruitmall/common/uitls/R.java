package com.hl.fruitmall.common.uitls;

import com.hl.fruitmall.common.enums.ExceptionEnum;

/**
 * @author Hl
 * @create 2020/12/16 23:23
 */
public class R<T> {

    private Integer code;
    private String msg;
    private T data;

    public Integer getCode() {
        return code;
    }

    public R<T> setCode(Integer code) {
        this.code = code;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public R<T> setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public T getData() {
        return data;
    }

    public R<T> setData(T data) {
        this.data = data;
        return this;
    }

    public R(){

    }

    public R(Integer code){
        this.code = code;
    }

    public R(Integer code,String msg){
        this.code = code;
        this.msg = msg;
    }

    public R(Integer code,String msg, T data){
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static <T> R<T> ok(){
        return new R<T>(20000,"success");
    }

    public static <T> R<T> ok(T data) {
        return new R<T>(20000,"success",data);
    }

    public static <T> R<T> error(){
        return new R<T>(ExceptionEnum.SYSTEM_UNKNOW_EXCEPTION.getCode(),
                ExceptionEnum.SYSTEM_UNKNOW_EXCEPTION.getMsg());
    }

    public static <T> R<T> error(Integer code, String msg){
        return new R<T>(code,msg);
    }
}
