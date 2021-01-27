package com.hl.fruitmall.common.uitls;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.hl.fruitmall.entity.bean.User;

/**
 * @author Hl
 * @create 2020/12/21 19:52
 */
public class TokenUtils {
    public static final long SAVE_TIME = 60 * 30;

    public static String getToken(User user) {
        String token = "";
        token = JWT.create().withAudience(
                String.valueOf(user.getId()),
                user.getPhone(),
                String.valueOf(user.getRoleType()))
                .sign(Algorithm.HMAC256(user.getPassword()));
        return token;
    }
}
