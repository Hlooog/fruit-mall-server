package com.hl.fruitmall.service.impl;

import com.auth0.jwt.JWT;
import com.hl.fruitmall.common.enums.ExceptionEnum;
import com.hl.fruitmall.common.enums.RedisKeyEnum;
import com.hl.fruitmall.common.enums.RoleEnum;
import com.hl.fruitmall.common.exception.GlobalException;
import com.hl.fruitmall.common.uitls.GlobalUtils;
import com.hl.fruitmall.common.uitls.R;
import com.hl.fruitmall.common.uitls.TokenUtils;
import com.hl.fruitmall.entity.bean.Shop;
import com.hl.fruitmall.entity.bean.User;
import com.hl.fruitmall.entity.vo.UserPageVO;
import com.hl.fruitmall.mapper.MerchantInfoMapper;
import com.hl.fruitmall.mapper.ShopMapper;
import com.hl.fruitmall.mapper.UserMapper;
import com.hl.fruitmall.service.ShopService;
import com.hl.fruitmall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
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
    private ShopService shopService;

    @Autowired
    private MerchantInfoMapper merchantInfoMapper;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private GlobalUtils globalUtils;

    public static final String STR_TO_JSON = "{\"token\":\"%s\",\"password\":\"%s\"}";

    @Override
    public R adminLogin(String phone, String password) {
        User user = userMapper.selectByField("phone", phone);
        if (user == null) {
            throw new GlobalException(ExceptionEnum.HAS_NOT_USER_RECORDS);
        }
        if (!password.equals(user.getPassword())) {
            throw new GlobalException(ExceptionEnum.INCORRECT_PASSWORD);
        }
        if (RoleEnum.CUSTOMER_SERVICE.getCode() < user.getRoleType()) {
            throw new GlobalException(ExceptionEnum.INSUFFICIENT_PERMISSIONS);
        }
        String key = String.format(RedisKeyEnum.USER_LOGIN_KEY.getKey(), user.getId(), user.getPhone());
        String token = TokenUtils.getToken(user);
        String cache = String.format(STR_TO_JSON, token, user.getPassword());
        redisTemplate.opsForValue().set(key, cache, TokenUtils.SAVE_TIME, TimeUnit.SECONDS);
        return R.ok(new HashMap<String, Object>() {
            {
                put("token", token);
                put("id", user.getId());
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
        String key = String.format(RedisKeyEnum.USER_LOGIN_KEY.getKey(), id, phone);
        redisTemplate.delete(key);
        return R.ok();
    }

    @Override
    public R page(Integer cur, String key, String startTime, String endTime) {
        Date[] dates = globalUtils.strToDate(startTime, endTime);
        Date start = dates[0], end = dates[1];
        List<UserPageVO> list = userMapper.page((cur - 1) * 10, key, start, end);
        Integer total = userMapper.getTotal(key, start, end,3);
        return R.ok(new HashMap<String, Object>() {
            {
                put("data", list);
                put("total", total);
            }
        });
    }

    @Override
    public R banGeneral(Integer id, Integer days) {
        User user = checkUser("id", id);
        user.addViolation();
        user.setBanTime(
                globalUtils.getBanTime(user.getCreateTime(), user.getViolation(), days)
        );
        userMapper.updateBanTime(id, user.getBanTime(), user.getViolation());
        return R.ok();
    }

    @Override
    public R setService(Integer id) {
        checkUser("id", id);
        userMapper.updateByField("id", id,
                "role_type", RoleEnum.CUSTOMER_SERVICE.getCode());
        return R.ok();
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public R banMerchant(Integer id, Integer days) {
        User user = checkUser("id", id);
        user.addViolation();
        user.setBanTime(
                globalUtils.getBanTime(user.getCreateTime(), user.getViolation(), days)
        );
        Shop shop = shopService.getShop("owner_id", id);
        if (!shop.getBanTime().before(shop.getCreateTime())) {
            if (user.getBanTime().before(user.getCreateTime())) {
                Date banTime = globalUtils.getBanTime(shop.getCreateTime(),
                        shop.getViolation(),
                        GlobalUtils.PUNISHMENT.length - 1);
                shopMapper.updateBanTime(shop.getId(), banTime, shop.getViolation());
            } else {
                if (user.getBanTime().after(shop.getBanTime())) {
                    shopMapper.updateBanTime(shop.getId(), user.getBanTime(), shop.getViolation());
                }
            }
        }
        userMapper.updateBanTime(id, user.getBanTime(), user.getViolation());
        return R.ok();
    }

    @Override
    public User checkUser(String field, Object value) {
        User user = userMapper.selectByField(field, value);
        if (user == null) {
            throw new GlobalException(ExceptionEnum.HAS_NOT_USER_RECORDS);
        }
        if (user.getCreateTime().after(user.getBanTime())
                || user.getBanTime().after(new Date())) {
            throw new GlobalException(ExceptionEnum.USER_HAS_BEEN_BAN);
        }
        return user;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public R cancelMerchant(Integer id) {
        checkUser("id", id);
        userMapper.updateByField("id", id, "role_type", RoleEnum.USER);
        shopMapper.updateByField("owner_id", id, "role_type", RoleEnum.USER.getCode());
        return R.ok();
    }

    @Override
    public R getList(Integer code) {
        List<UserPageVO> list = userMapper.getListByFiled("role_type", code);
        return R.ok(list);
    }

    @Override
    public R cancelService(Integer id) {
        userMapper.updateByField("id", id, "role_type", RoleEnum.USER.getCode());
        return R.ok();
    }

    @Override
    public R pageMerchant(Integer cur, String key, String startTime, String endTime) {
        Date[] dates = globalUtils.strToDate(startTime, endTime);
        Date start = dates[0], end = dates[1];
        List<UserPageVO> list = userMapper.pageMerchant((cur - 1) * 10, key, start, end);
        Integer total = userMapper.getTotal(key, start, end, 2);
        return R.ok(new HashMap<String, Object>() {
            {
                put("data", list);
                put("total", total);
            }
        });
    }

}