package com.hl.fruitmall.common.uitls;

import com.hl.fruitmall.common.interfaces.EnumInterface;

/**
 * @author Hl
 * @create 2020/12/2 0:50
 */
public class EnumUtils {
    public static <T extends EnumInterface> String getByCode(Integer code, Class<T> t){
        for (T item: t.getEnumConstants()){
            if (item.getCode().equals(code)){
                return item.getMsg();
            }
        }
        return "系统未知异常";
    }

    public static <T extends EnumInterface> Integer getByMsg(String msg, Class<T> t){
        for (T item: t.getEnumConstants()){
            if (item.getMsg().equals(msg)){
                return item.getCode();
            }
        }
        return 100000;
    }
}
