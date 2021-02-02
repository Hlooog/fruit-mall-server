package com.hl.fruitmall.common.exception;


import com.hl.fruitmall.common.uitls.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理
 *
 * @author Hl
 * @create 2020/11/24 22:48
 */
@RestControllerAdvice(basePackages = "com.hl.fruitmall.controller")
@Slf4j
public class MyExceptionHandler {

    @ExceptionHandler(Throwable.class)
    public R handlerException(Throwable e) {
        log.error(e.getMessage());
        return R.error();
    }

    @ExceptionHandler(GlobalException.class)
    public R handlerException(GlobalException e) {
        return R.error(e.getCode(), e.getMsg());
    }

}
