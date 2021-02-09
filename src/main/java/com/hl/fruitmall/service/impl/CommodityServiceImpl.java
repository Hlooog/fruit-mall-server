package com.hl.fruitmall.service.impl;

import com.auth0.jwt.JWT;
import com.hl.fruitmall.common.enums.ExceptionEnum;
import com.hl.fruitmall.common.enums.RedisKeyEnum;
import com.hl.fruitmall.common.exception.GlobalException;
import com.hl.fruitmall.common.uitls.R;
import com.hl.fruitmall.config.RabbitConfig;
import com.hl.fruitmall.entity.bean.Commodity;
import com.hl.fruitmall.entity.vo.*;
import com.hl.fruitmall.mapper.CommodityInfoMapper;
import com.hl.fruitmall.mapper.CommodityMapper;
import com.hl.fruitmall.mapper.ScoreMapper;
import com.hl.fruitmall.mapper.VarietyMapper;
import com.hl.fruitmall.service.CommodityService;
import com.hl.fruitmall.service.ShopService;
import com.hl.fruitmall.service.UserService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisZSetCommands;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * 水果商品表(Commodity)表服务实现类
 *
 * @author hl
 * @since 2020-11-21 00:33:35
 */
@Service("commodityService")
public class CommodityServiceImpl implements CommodityService {

    @Autowired
    private CommodityMapper commodityMapper;

    @Autowired
    private VarietyMapper varietyMapper;

    @Autowired
    private CommodityInfoMapper commodityInfoMapper;

    @Autowired
    private ScoreMapper scoreMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private ShopService shopService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public R list(Integer id) {
        List<CommodityListVO> list = commodityMapper.selectList(id);
        return R.ok(new HashMap<String, Object>() {
            {
                put("data", list);
            }
        });
    }

    @Override
    public R off(Integer id) {
        commodityMapper.updateByField("id", id, "is_up", 0);
        removeCache(id);
        return R.ok();
    }

    @Override
    public R page(Integer id, Integer cur) {
        List<CommodityPageVO> list = commodityMapper.selectPage(id, (cur - 1) * 10);
        Integer total = commodityMapper.getTotal(id);
        return R.ok(new HashMap<String, Object>() {
            {
                put("data", list);
                put("total", total);
            }
        });
    }

    @Override
    public R getInfoList(Integer id) {
        List<CommodityInfoVO> list = commodityInfoMapper.getInfoList(id);
        return R.ok(list);
    }

    @Override
    public R get(Integer id) {
        CommodityPageVO commodityPageVO = commodityMapper.selectByField("c.id", id);
        EditCommodityVO editCommodityVO =
                new EditCommodityVO(commodityPageVO.getId(),
                        commodityPageVO.getCname(),
                        commodityPageVO.getVarietyId(),
                        commodityPageVO.getUrlList());
        return R.ok(editCommodityVO);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public R up(Integer id, HttpServletRequest request) {
        check(request);
        List<CommodityInfoVO> infoList = commodityInfoMapper.getInfoList(id);
        if (infoList.size() == 0) {
            throw new GlobalException(ExceptionEnum.SPECIFICATION_CAN_NOT_NULL);
        }
        commodityMapper.updateByField("id", id, "is_up", 1);
        addCache(id, infoList);
        return R.ok();
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public void addCache(Integer id, List<CommodityInfoVO> infoList) {
        CommodityVO commodityVO = commodityMapper.select(id);
        BigDecimal price = infoList.get(0).getPrice();
        for (int i = 1; i < infoList.size(); i++) {
            if (price.compareTo(infoList.get(i).getPrice()) == 1) {
                price = infoList.get(i).getPrice();
            }
        }
        Double score = scoreMapper.get(id);
        commodityVO.setPrice(price);
        redisTemplate.opsForHash().put(RedisKeyEnum.COMMODITY_HASH.getKey(), id, commodityVO);
        String key = String.format(RedisKeyEnum.VARIETY_KEY.getKey(), commodityVO.getVarietyId());
        redisTemplate.opsForZSet().add(RedisKeyEnum.COMMODITY_Z_SET.getKey(), id,
                score == null ? commodityVO.getCreateTime().getTime() : score);
        redisTemplate.opsForZSet().incrementScore(key, id, 0);
        redisTemplate.opsForZSet().add(RedisKeyEnum.PRICE, id, price.doubleValue());
        scoreMapper.delete(id);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public void removeCache(Integer id) {
        Double score = redisTemplate.opsForZSet().score(RedisKeyEnum.COMMODITY_Z_SET.getKey(), id);
        scoreMapper.insert(id, score);
        redisTemplate.opsForZSet().remove(RedisKeyEnum.COMMODITY_Z_SET.getKey(), id);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public R delete(Integer id) {
        CommodityPageVO commodityPageVO = commodityMapper.selectByField("c.id", id);
        // 删除本商品
        commodityMapper.deleteByField("id", id);
        // 删除图片内容
        deleteImage(commodityPageVO.getUrlList());
        // 删除规格信息
        commodityInfoMapper.deleteInfo("commodity_id", commodityPageVO.getId());
        return R.ok();
    }

    @Override
    public R getVariety() {
        List<VarietyVO> list = varietyMapper.get();
        return R.ok(list);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public R edit(EditCommodityVO vo) {
        CommodityPageVO commodityPageVO = commodityMapper.selectByField("c.id", vo.getId());
        if (!commodityPageVO.getCname().equals(vo.getName())
                || !commodityPageVO.getVarietyId().equals(vo.getVarietyId())) {
            commodityMapper.update(vo.getId(),
                    vo.getName(),
                    vo.getVarietyId());
        }
        List<String> list = commodityPageVO.getUrlList();
        List<String> urls = new ArrayList<>();
        List<Integer> ids = new ArrayList<>();
        for (String url : vo.getUrlList()) {
            if (!list.contains(url)) {
                urls.add(url);
            }
        }
        if (urls.size() != 0) {
            commodityMapper.insertImage(vo.getId(), urls);
        }
        if (vo.getRemoveList() != null && vo.getRemoveList().size() != 0) {
            deleteImage(vo.getRemoveList());
        }
        return R.ok();
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public R create(EditCommodityVO vo) {
        Commodity commodity = new Commodity(vo.getShopId(),
                vo.getName(),
                vo.getVarietyId());
        commodityMapper.insert(commodity);
        commodityMapper.insertImage(commodity.getId(), vo.getUrlList());
        if (vo.getRemoveList() != null && vo.getRemoveList().size() != 0) {
            deleteImage(vo.getRemoveList());
        }
        return R.ok();
    }

    @Override
    public R insertVariety(String name) {
        VarietyVO varietyVO = new VarietyVO();
        varietyVO.setName(name);
        varietyMapper.insert(varietyVO);
        redisTemplate.opsForZSet().add(RedisKeyEnum.VARIETY_SET.getKey(), varietyVO, 0);
        return R.ok(varietyVO.getId());
    }

    @Override
    public R infoCreate(EditCommodityInfoVO vo) {
        commodityInfoMapper.insertInfo(vo);
        return R.ok();
    }

    @Override
    public R infoEdit(EditCommodityInfoVO vo) {
        commodityInfoMapper.updateInfo(vo);
        return R.ok();
    }

    @Override
    public R infoDelete(Integer id) {
        commodityInfoMapper.deleteInfo("id", id);
        return R.ok();
    }

    @Override
    public R infoGet(Integer id) {
        EditCommodityInfoVO vo = commodityInfoMapper.selectInfo(id);
        return R.ok(vo);
    }

    @Override
    public R varietyList() {
        Set set = redisTemplate.opsForZSet().
                reverseRange(RedisKeyEnum.VARIETY_SET.getKey(), 0, -1);
        return R.ok(set);
    }

    @Override
    public R getList(Map<String, Object> map, HttpServletRequest request) {
        // 取出参数
        Integer cur = (Integer) map.get("cur");
        List<Integer> select = (List<Integer>) map.get("select");
        String minStr = (String) map.get("min");
        String maxStr = (String) map.get("max");
        Double min = minStr.equals("") ? 0 : Double.valueOf(minStr);
        Double max = maxStr.equals("") ? Double.POSITIVE_INFINITY : Double.valueOf(maxStr);

        String token = request.getHeader("X-Token");
        // 全局key
        StringBuilder allKey = new StringBuilder("ALL_KEY");
        Integer id = null;
        // 用户是否登录
        if (token != null) {
            id = Integer.valueOf(JWT.decode(token).getAudience().get(0));
            allKey.append(":" + id);
        }
        // 价格区间key
        String priceKey = String.format(RedisKeyEnum.PRICE_INTERVAL.getKey(), min, max);
        allKey.append(":" + priceKey);
        // 水果类型key
        StringBuilder varietyKey = new StringBuilder("VARIETY_UNION");
        List<String> varietyList = new ArrayList<>();
        String innerKey = null;
        // 选择了水果类型 填补数据
        if (select.size() > 0) {
            Collections.sort(select);
            innerKey = String.format(RedisKeyEnum.VARIETY_KEY.getKey(), select.get(0));
            varietyKey.append(":").append(select.get(0));
            if (select.size() >= 1) {
                for (int i = 1; i < select.size(); i++) {
                    varietyKey.append(":").append(select.get(i));
                    varietyList.add(String.format(RedisKeyEnum.VARIETY_KEY.getKey(), select.get(i)));
                }
            }
            allKey.append(":" + varietyKey.toString());
        }
        // 查看redis 中是否有全局key 如果有就直接取 没有就加入
        if (redisTemplate.keys(allKey).size() == 0) {
            String destKey = null;
            // 取价格区间内的水果id存为一个zset
            Set priceSet = redisTemplate.opsForZSet()
                    .rangeByScoreWithScores(RedisKeyEnum.PRICE.getKey(), min, max);
            redisTemplate.opsForZSet().add(priceKey, priceSet);
            redisTemplate.expire(priceKey, 300, TimeUnit.SECONDS);
            // 选择了水果类型以后 求并集
            if (select.size() > 0) {
                destKey = varietyKey + ":" + priceKey;
                redisTemplate.opsForZSet().unionAndStore(innerKey, varietyList, varietyKey.toString());
                redisTemplate.expire(varietyKey, 300, TimeUnit.SECONDS);
                redisTemplate.opsForZSet().intersectAndStore(varietyKey, priceKey, destKey);
            } else {
                destKey = priceKey;
            }
            // 用户登录了
            if (id != null) {
                String userKey = String.format(RedisKeyEnum.BROWSE_RECORDS.getKey(), id);
                String userDestKey = null;
                userDestKey = destKey + ":" + userKey;
                // 先求用户和destKey的交集
                redisTemplate.opsForZSet().intersectAndStore(userKey, destKey, userDestKey);
                // 然后再用destKey和用户的交集和destKey按分数1：100求并集 目的是为了使用户点击过的尽量靠前
                redisTemplate.opsForZSet().unionAndStore(destKey,
                        Arrays.asList(userDestKey),
                        destKey,
                        RedisZSetCommands.Aggregate.SUM,
                        RedisZSetCommands.Weights.of(1, 100));
            }
            redisTemplate.expire(destKey, 300, TimeUnit.SECONDS);
            // 用destKey 和 总的分数求交集
            redisTemplate.opsForZSet().intersectAndStore(destKey, RedisKeyEnum.COMMODITY_Z_SET.getKey(), allKey);
            redisTemplate.expire(allKey, 300, TimeUnit.SECONDS);
        }
        // 取出allKey内的值 分页取
        Set set = redisTemplate.opsForZSet().reverseRange(allKey, (cur - 1) * 8, ((cur * 8) - 1));
        // 取出hash中的真实值
        List<CommodityVO> list = (List<CommodityVO>) redisTemplate.opsForHash()
                .multiGet(RedisKeyEnum.COMMODITY_HASH.getKey(), set);
        return R.ok(list);
    }

    @Override
    public R getHome(HttpServletRequest request) {
        Set set = redisTemplate.opsForZSet()
                .reverseRange(RedisKeyEnum.COMMODITY_Z_SET.getKey(), 0, 9);
        List list = redisTemplate.opsForHash()
                .multiGet(RedisKeyEnum.COMMODITY_HASH.getKey(), set);
        return R.ok(new HashMap<String, Object>() {
            {
                put("hot", list);
            }
        });
    }

    @Override
    public R getInfo(Integer id) {
        FrontCommodityVO vo = commodityMapper.selectInfo(id);
        return R.ok(vo);
    }

    private void deleteImage(List<String> list) {
        commodityMapper.deleteImage(list);
        rabbitTemplate.convertAndSend(RabbitConfig.EXCHANGE_OSS_DELETE,
                RabbitConfig.ROUTING_OSS_DELETE,
                list);
    }

    private void check(HttpServletRequest request) {
        Integer userId = Integer.valueOf(JWT.decode(request.getHeader("X-Token")).getAudience().get(0));
        userService.checkUser("id", userId);
        shopService.checkShop("owner_id", userId);
    }
}