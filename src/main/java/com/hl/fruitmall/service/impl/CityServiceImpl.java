package com.hl.fruitmall.service.impl;

import com.alibaba.fastjson.JSON;
import com.hl.fruitmall.common.enums.RedisKeyEnum;
import com.hl.fruitmall.common.uitls.R;
import com.hl.fruitmall.mapper.CityMapper;
import com.hl.fruitmall.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 城市表(City)表服务实现类
 *
 * @author JiongFy
 * @since 2020-12-26 21:55:47
 */
@Service("cityService")
public class CityServiceImpl implements CityService {
    @Resource
    private CityMapper cityMapper;

    @Autowired
    StringRedisTemplate redisTemplate;

    @Override
    public R getInfo() {
        String cityStr = redisTemplate.opsForValue().get(RedisKeyEnum.CITY.getKey());
        List cityVOList = JSON.parseObject(cityStr,List.class);
        return R.ok(cityVOList);
    }
}