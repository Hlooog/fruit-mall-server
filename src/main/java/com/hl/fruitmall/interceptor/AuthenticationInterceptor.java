package com.hl.fruitmall.interceptor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.hl.fruitmall.common.annotation.PassToken;
import com.hl.fruitmall.common.annotation.VerificationToken;
import com.hl.fruitmall.common.enums.ExceptionEnum;
import com.hl.fruitmall.common.enums.RedisKeyEnum;
import com.hl.fruitmall.common.exception.GlobalException;
import com.hl.fruitmall.common.uitls.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author Hl
 * @create 2020/12/28 19:12
 */
@Component
public class AuthenticationInterceptor implements HandlerInterceptor {
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)  {
        String token = request.getHeader("X-Token");
        // 如果不是映射到方法直接通过
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();

        if (method.isAnnotationPresent(PassToken.class)) {
            return true;
        }
        if (method.isAnnotationPresent(VerificationToken.class)) {
            VerificationToken verificationToken = method.getAnnotation(VerificationToken.class);
            if (verificationToken.required()) {
                if (token == null) {
                    throw new GlobalException(ExceptionEnum.CAN_NOT_LOGIN);
                }
                String id, phoneNumber, roleType;
                int intRoleType;
                try {
                    List<String> audience = JWT.decode(token).getAudience();
                    id = audience.get(0);
                    phoneNumber = audience.get(1);
                    roleType = audience.get(2);
                    intRoleType = Integer.valueOf(roleType);
                } catch (JWTDecodeException e) {
                    throw new GlobalException(ExceptionEnum.SYSTEM_UNKNOW_EXCEPTION);
                }

                if (intRoleType > verificationToken.roleType().getCode()) {
                    throw new GlobalException(ExceptionEnum.INSUFFICIENT_PERMISSIONS);
                }

                String key = String.format(RedisKeyEnum.USER_LOGIN_KEY.getKey(), id, phoneNumber);
                String cache = (String) redisTemplate.opsForValue().get(key);
                if (cache == null) {
                    throw new GlobalException(ExceptionEnum.TOKEN_INVALIDATION);
                }
                JSONObject jsonObject = JSON.parseObject(cache);
                if (!token.equals(jsonObject.get("token"))) {
                    throw new GlobalException(ExceptionEnum.TOKEN_VERIFICATION_FAIL);
                }
                JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256((String) jsonObject.get("password"))).build();
                try {
                    jwtVerifier.verify(token);
                } catch (Exception e) {
                    throw new GlobalException(ExceptionEnum.TOKEN_VERIFICATION_FAIL);
                }
                redisTemplate.opsForValue().set(key, cache, TokenUtils.SAVE_TIME, TimeUnit.SECONDS);
            }
            return true;
        }
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
