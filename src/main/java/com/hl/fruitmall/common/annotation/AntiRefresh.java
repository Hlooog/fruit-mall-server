package com.hl.fruitmall.common.annotation;

import com.hl.fruitmall.common.enums.ExceptionEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Hl
 * @create 2021/3/6 17:40
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface AntiRefresh {

    String[] logos();

    long time(); // TimeUtils.SECONDS

    ExceptionEnum err() default ExceptionEnum.SYSTEM_UNKNOW_EXCEPTION;

}
