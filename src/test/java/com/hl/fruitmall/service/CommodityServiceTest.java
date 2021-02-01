package com.hl.fruitmall.service;

import com.hl.fruitmall.common.enums.RedisKeyEnum;
import com.hl.fruitmall.entity.vo.CommodityListVO;
import com.hl.fruitmall.mapper.CommodityMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.math.BigDecimal;
import java.util.*;

/**
 * @author Hl
 * @create 2021/1/29 9:59
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CommodityServiceTest {
    @Autowired
    private CommodityMapper commodityMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;


    @Test
    void test(){
        List<CommodityListVO> commodityListVOS = commodityMapper.selectList(10000);
        System.out.println(commodityListVOS);
    }

    @Test
    void test1(){
        BigDecimal b1 = new BigDecimal(1);
        BigDecimal b2 = new BigDecimal(2);
        BigDecimal b3 = new BigDecimal(1);
        System.out.println("b1 == b2" + b1.compareTo(b2));
        System.out.println("b2 == b1" + b2.compareTo(b1));
        System.out.println("b1 == b3" + b1.compareTo(b3));
        System.out.println(b1.subtract(b2));
    }

    @Test
    void test2(){
        /*redisTemplate.opsForZSet().add("key", "1",10);
        redisTemplate.opsForZSet().add("key", "2",20);
        redisTemplate.opsForZSet().add("key", "3",30);
        redisTemplate.opsForZSet().add("key", "5",40);*/
        /*Set set = redisTemplate.opsForZSet().reverseRange("key", 0, 0);
        Iterator iterator = set.iterator();
        while (iterator.hasNext()){
            System.out.println(iterator.next());
        }*/
        /*Set<Map<String,String>> set = redisTemplate.opsForZSet()
                .reverseRange(RedisKeyEnum.SERVICE_LINK_USER.getKey(), 0, 49);
        Iterator iterator = set.iterator();
        while (iterator.hasNext()){
            System.out.println(iterator.next());
        }*/
        Map<String,Integer> map = redisTemplate.opsForHash().entries(RedisKeyEnum.CHAT_UNREAD_NUMBER_KEY.getKey());
        Set set = map.entrySet();
        Iterator iterator = set.iterator();
        while (iterator.hasNext()){
            System.out.println(iterator.next());
        }
    }

    @Test
    void test3(){
        Map<String,String> map = new HashMap<>();
        map.put("name", "000");
        map.put("avatar", "21111");
        map.put("phone", "333");
        redisTemplate.opsForZSet().add(RedisKeyEnum.SERVICE_LINK_USER.getKey(), map, new Date().getTime());
    }
    @Test
    void test4(){
        String cityStr = (String) redisTemplate.opsForValue().get(RedisKeyEnum.CITY.getKey());
        System.out.println(cityStr);
    }

    @Test
    void test5(){
        /*String str = stringRedisTemplate.opsForValue().get(RedisKeyEnum.CITY.getKey());
        List list = JSON.parseObject(str, List.class);
        redisTemplate.opsForValue().set(RedisKeyEnum.CITY.getKey(), list);*/
        List list = (List) redisTemplate.opsForValue().get(RedisKeyEnum.CITY.getKey());
        System.out.println(list);
    }

    @Test
    void test6(){
        String key = String.format(RedisKeyEnum.CHAT_READ_RECORD_KEY.getKey(), "18211461717");
        Set set = redisTemplate.opsForZSet().reverseRange(key, 20, 30);
        System.out.println(set.size());
        /*Iterator iterator = set.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }*/
    }
}
