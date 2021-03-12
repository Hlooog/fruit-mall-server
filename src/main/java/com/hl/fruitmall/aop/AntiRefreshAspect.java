package com.hl.fruitmall.aop;

import com.hl.fruitmall.common.annotation.AntiRefresh;
import com.hl.fruitmall.common.enums.RedisKeyEnum;
import com.hl.fruitmall.common.exception.GlobalException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.CodeSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author Hl
 * @create 2021/3/6 22:27
 */
@Component
@Aspect
@Slf4j
public class AntiRefreshAspect {

    @Autowired
    private RedisTemplate redisTemplate;

    @Around("@annotation(refresh)")
    public Object around(ProceedingJoinPoint point, AntiRefresh refresh) throws Throwable {
        Map<String,Object> map = new HashMap<>();
        Object[] args = point.getArgs();
        String[] names = ((CodeSignature) point.getSignature()).getParameterNames();
        for (int i = 0; i < args.length; i++) {
            Object arg = args[i];
            boolean type = checkType(arg);
            if (type) {
                map.put(names[i], arg);
            } else {
                if (arg instanceof  Map) {
                    map.putAll((Map<? extends String, ?>) arg);
                } else {
                    Field[] fields = arg.getClass().getDeclaredFields();
                    for (Field field : fields) {
                        field.setAccessible(true);
                        map.put(field.getName(), field.get(arg));
                    }
                }
            }
        }
        String[] logos = refresh.logos();
        StringBuilder sb = new StringBuilder(RedisKeyEnum.ANTI_REFRESH_WITHDRAW.getKey());
        for (String logo : logos) {
            if (map.containsKey(logo)) {
                sb.append("_").append(map.get(logo));
            } else {
                log.warn("未使用到的参数： " + logo);
            }
        }
        Boolean isBy = redisTemplate.opsForValue().
                setIfAbsent(sb.toString(), 1, refresh.time(), TimeUnit.SECONDS);
        if (!isBy) {
            throw new GlobalException(refresh.err());
        }
        return point.proceed();
    }

    private boolean checkType(Object arg){
        if (arg instanceof Integer) {
            return true;
        } else if (arg instanceof String) {
            return true;
        } else if (arg instanceof Long) {
            return true;
        } else if (arg instanceof Double) {
            return true;
        } else if (arg instanceof Float) {
            return true;
        } else if (arg instanceof Byte) {
            return true;
        } else if (arg instanceof Short) {
            return true;
        } else if (arg instanceof Boolean) {
            return true;
        } else if (arg instanceof Character) {
            return true;
        } else {
            return false;
        }
    }
}
