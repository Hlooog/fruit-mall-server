package com.hl.fruitmall.service.impl;

import com.auth0.jwt.JWT;
import com.hl.fruitmall.common.enums.ExceptionEnum;
import com.hl.fruitmall.common.enums.RedisKeyEnum;
import com.hl.fruitmall.common.enums.RoleEnum;
import com.hl.fruitmall.common.exception.GlobalException;
import com.hl.fruitmall.common.uitls.R;
import com.hl.fruitmall.common.uitls.TokenUtils;
import com.hl.fruitmall.entity.bean.User;
import com.hl.fruitmall.entity.vo.UserPageVO;
import com.hl.fruitmall.mapper.MerchantInfoMapper;
import com.hl.fruitmall.mapper.ShopMapper;
import com.hl.fruitmall.mapper.UserMapper;
import com.hl.fruitmall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 用户表(User)表服务实现类
 *
 * @author hl
 * @since 2020-11-21 00:33:46
 */
@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ShopMapper shopMapper;

    @Autowired
    private MerchantInfoMapper merchantInfoMapper;

    @Autowired
    StringRedisTemplate redisTemplate;

    public static final String STR_TO_JSON = "{\"token\":\"%s\",\"password\":\"%s\"}";

    @Override
    public R adminLogin(String phone, String password) {
        User user = userMapper.selectByField("phone",phone);
        if (user == null) {
            throw new GlobalException(ExceptionEnum.HAS_NOT_USER_RECORDS);
        }
        if (!password.equals(user.getPassword())) {
            throw new GlobalException(ExceptionEnum.INCORRECT_PASSWORD);
        }
        if (!RoleEnum.ADMIN.getCode().equals(user.getRoleType())) {
            throw new GlobalException(ExceptionEnum.LOGIN_HAS_NOT_PERMISSION);
        }
        String key = String.format(RedisKeyEnum.USER_LOGIN_KEY.getKey(), user.getId(), user.getPhone());
        String token = TokenUtils.getToken(user);
        String cache = String.format(STR_TO_JSON, token, user.getPassword());
        redisTemplate.opsForValue().set(key, cache, TokenUtils.SAVE_TIME, TimeUnit.SECONDS);
        return R.ok(new HashMap<String, Object>() {
            {
                put("token", token);
                put("id",user.getId());
                put("name", user.getNickname());
                put("avatar", user.getAvatar());
                put("phone", user.getPhone());
            }
        });
    }



    @Override
    public R logout(HttpServletRequest request) {
        String token = request.getHeader("X-Token");
        List<String> audience = JWT.decode(token).getAudience();
        String id = audience.get(0);
        String phone = audience.get(1);
        String key = String.format(RedisKeyEnum.USER_LOGIN_KEY.getKey(), id,phone);
        redisTemplate.delete(key);
        return R.ok();
    }

    @Override
    public R page(Integer cur, String key, String startTime, String endTime) {
        Date start = null,end = null;
        if (null != startTime
                && null != endTime
                && !"".equals(startTime)
                && !"".equals(endTime)){
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            try {
                start = format.parse(startTime);
                end = format.parse(endTime);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        List<UserPageVO> list = userMapper.page((cur - 1) * 10, key,start,end);
        Integer total = userMapper.getTotal(key,start,end);
        return R.ok(new HashMap<String,Object>(){
            {
                put("data", list);
                put("total", total);
            }
        });
    }

    @Override
    public R ban(Integer id) {
        return null;
    }

}