package com.hl.fruitmall.service;

import com.hl.fruitmall.common.enums.RedisKeyEnum;
import com.hl.fruitmall.entity.vo.CommodityListVO;
import com.hl.fruitmall.entity.vo.FrontCommodityVO;
import com.hl.fruitmall.entity.vo.VarietyVO;
import com.hl.fruitmall.mapper.CommodityMapper;
import com.hl.fruitmall.mapper.VarietyMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.RedisZSetCommands;
import org.springframework.data.redis.core.DefaultTypedTuple;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

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
    private VarietyMapper varietyMapper;

    @Autowired
    private CommodityService commodityService;


    @Test
    void test() {
        List<CommodityListVO> commodityListVOS = commodityMapper.selectList(10000);
        System.out.println(commodityListVOS);
    }

    @Test
    void test1() {
        BigDecimal b1 = new BigDecimal(1);
        BigDecimal b2 = new BigDecimal(2);
        BigDecimal b3 = new BigDecimal(1);
        System.out.println("b1 == b2" + b1.compareTo(b2));
        System.out.println("b2 == b1" + b2.compareTo(b1));
        System.out.println("b1 == b3" + b1.compareTo(b3));
        System.out.println(b1.subtract(b2));
    }

    @Test
    void test2() {
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
        /*Map<String,Integer> map = redisTemplate.opsForHash().entries(RedisKeyEnum.CHAT_UNREAD_NUMBER_KEY.getKey());
        Set set = map.entrySet();
        Iterator iterator = set.iterator();
        while (iterator.hasNext()){
            System.out.println(iterator.next());
        }*/
        /*Set set = redisTemplate.opsForZSet().reverseRange("key", 0, -1);
        System.out.println(set.size());*/
    }

    @Test
    void test3() {
        Map<String, String> map = new HashMap<>();
        map.put("name", "000");
        map.put("avatar", "21111");
        map.put("phone", "333");
        redisTemplate.opsForZSet().add(RedisKeyEnum.SERVICE_LINK_USER_KEY.getKey(), map, new Date().getTime());
    }

    @Test
    void test4() {
        String cityStr = (String) redisTemplate.opsForValue().get(RedisKeyEnum.CITY.getKey());
        System.out.println(cityStr);
    }

    @Test
    void test5() {
        /*String str = stringRedisTemplate.opsForValue().get(RedisKeyEnum.CITY.getKey());
        List list = JSON.parseObject(str, List.class);
        redisTemplate.opsForValue().set(RedisKeyEnum.CITY.getKey(), list);*/
        List list = (List) redisTemplate.opsForValue().get(RedisKeyEnum.CITY.getKey());
        System.out.println(list);
    }

    @Test
    void test6() {
        /*String key = String.format(RedisKeyEnum.CHAT_READ_RECORD_KEY.getKey(), "18211461717");
        Set set = redisTemplate.opsForZSet().reverseRange(key, 20, 30);
        System.out.println(set.size());*/
        /*Iterator iterator = set.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }*/
        /*long time = new Date().getTime();
        Double d = Double.valueOf(time * 1000000);
        System.out.println(d);
        System.out.println(Double.MAX_VALUE);*/
//        redisTemplate.opsForZSet().incrementScore("key", "123", 0);
        BigDecimal bigDecimal = new BigDecimal(28.5);
        Double d = bigDecimal.doubleValue();
        System.out.println(d);
    }

    @Test
    void test7() {
        String str = "https://thirdwx.qlogo.cn/mmopen/vi_32/DriaNd1wecVkpK7QvMyHDxrqvtNWUzlMia5QySMooniaS5sXTVABUuMTceCuaoKsiayZribXr2D8Nq3icEsBxufSKT2w/132";
        System.out.println(str.length());
    }

    @Test
    void test8() {
        List<VarietyVO> list = varietyMapper.list();
        Set<ZSetOperations.TypedTuple<VarietyVO>> set = new HashSet<>();
        for (int i = 0; i < list.size(); i++) {
            ZSetOperations.TypedTuple<VarietyVO>
                    varietyVOTypedTuple = new DefaultTypedTuple(list.get(i), 0d);
            set.add(varietyVOTypedTuple);
        }
        redisTemplate.opsForZSet().add(RedisKeyEnum.VARIETY_SET.getKey(), set);
    }

    @Test
    void test9() {
//        Set set = redisTemplate.opsForZSet().reverseRange(RedisKeyEnum.COMMODITY_Z_SET.getKey(), 0, 9);
//        List<Integer> idList = new ArrayList<>(set);
        /*List list =  redisTemplate.opsForHash()
                .multiGet(RedisKeyEnum.COMMODITY_HASH.getKey(), set);
        System.out.println(list);*/
        redisTemplate.opsForValue().set("key", 1);
        redisTemplate.opsForValue().set("key:1", 1.1);
    }

    @Test
    void test10() {
        Set<ZSetOperations.TypedTuple<VarietyVO>> set = new HashSet<>();
        for (int i = 0; i < 30; i++) {
            ZSetOperations.TypedTuple<VarietyVO>
                    varietyVOTypedTuple = new DefaultTypedTuple(i, (double) i);
            set.add(varietyVOTypedTuple);
        }
        redisTemplate.opsForZSet().add("key1", set);

        /*set = new HashSet<>();
        for (int i = 5; i < 15; i++) {
            ZSetOperations.TypedTuple<VarietyVO>
                    varietyVOTypedTuple = new DefaultTypedTuple(i,(double)(i * 2));
            set.add(varietyVOTypedTuple);
        }
        redisTemplate.opsForZSet().add("key2", set);*/

        /*set = new HashSet<>();
        for (int i = 0; i < 20; i++) {
            ZSetOperations.TypedTuple<VarietyVO>
                    varietyVOTypedTuple = new DefaultTypedTuple(i,0d);
            set.add(varietyVOTypedTuple);
        }
        redisTemplate.opsForZSet().add("key3", set);*/
    }

    @Test
    void test11() {
//        List<String> list = new ArrayList<>(Arrays.asList("key1","key2"));
//        redisTemplate.opsForZSet().unionAndStore("key1","key2", "key4");
        List<String> list = new ArrayList<>();
        list.add("key2");
        list.add("key3");
        /**
         * RedisZSetCommands.Aggregate.MAX 表示要最大的分数
         * RedisZSetCommands.Aggregate.MIN 表示要最小的分数
         * RedisZSetCommands.Aggregate.SUM 表示要分数之和的分数
         *
         * redisTemplate.opsForZSet().intersectAndStore("key1", list, "key3",
         * RedisZSetCommands.Aggregate.MAX);
         */
//        redisTemplate.opsForZSet().intersectAndStore("key1", list, "key3", RedisZSetCommands.Aggregate.MAX);
        /**
         * 配置权重  1:2:3
         * List<String> list = new ArrayList<>();
         * list.add("key2");
         * list.add("key3");
         * RedisZSetCommands.Weights weights = RedisZSetCommands.Weights.of(1, 2, 3);
         * redisTemplate.opsForZSet().intersectAndStore("key1", list, "key4",
         * RedisZSetCommands.Aggregate.SUM,weights);
         */
        /*RedisZSetCommands.Weights weights = RedisZSetCommands.Weights.of(1, 2, 3);
        redisTemplate.opsForZSet().intersectAndStore("key1", list, "key4", RedisZSetCommands.Aggregate.SUM,weights);*/

        redisTemplate.opsForZSet().unionAndStore("key1", Arrays.asList("key2"), "key5", RedisZSetCommands.Aggregate.SUM, RedisZSetCommands.Weights.of(3, 1));

    }

    @Test
    void test12() {
//        redisTemplate.exec();
//        System.out.println(priceSet == null);
//        System.out.println(priceSet);
        Set priceSet = redisTemplate.opsForZSet()
                .rangeByScoreWithScores(RedisKeyEnum.PRICE.getKey(), 0, Double.POSITIVE_INFINITY);
        Iterator iterator = priceSet.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }

    @Test
    void test13() {
        Set set = redisTemplate.opsForZSet().reverseRange("key1", 0, 9);
        Iterator iterator = set.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }

    @Test
    void test14() {
        Double b1 = Double.POSITIVE_INFINITY;
        Double b2 = Double.POSITIVE_INFINITY + 5;
        System.out.println(b1.compareTo(b2));
    }

    @Test
    void test15(){
        FrontCommodityVO frontCommodityVO = commodityMapper.selectInfo(10020);
        System.out.println(frontCommodityVO);
    }

    @Test
    void test16(){
        Long key = redisTemplate.opsForValue().increment("key",5);
        System.out.println(key);
    }

    @Test
    void test17(){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date parse = format.parse("2021-01-09 09:12:32");
            Date parse1 = format.parse("2021-02-09 09:12:32");
            long time = parse.getTime() + (28 * TimeUnit.DAYS.toMillis(1));
            long time1 = parse1.getTime();
            System.out.println(time1-time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test
    void test18(){
        /*List<Integer> list = new ArrayList<>();
        list.add(10001);
        list.add(10002);
        for (int i = 10005; i < 100040; i++) {
            list.add(i);
        }
        for (int i = 0; i < list.size(); i++) {
            commodityService.up(list.get(i));
        }*/
        redisTemplate.opsForZSet().add("PRICE:0.0:Infinity", 1, 1);
        redisTemplate.expire("PRICE:0.0:Infinity", 300, TimeUnit.SECONDS);
    }
}
