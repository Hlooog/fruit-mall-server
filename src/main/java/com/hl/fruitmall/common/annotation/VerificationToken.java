package com.hl.fruitmall.common.annotation;

import com.hl.fruitmall.common.enums.RoleEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Hl
 * @create 2020/12/16 23:28
 */
@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface VerificationToken {
    boolean required() default true;
    RoleEnum roleType() default RoleEnum.ADMIN;
}
