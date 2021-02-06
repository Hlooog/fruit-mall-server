package com.hl.fruitmall.common.uitls;

import com.hl.fruitmall.common.enums.RedisKeyEnum;
import com.hl.fruitmall.entity.bean.Commodity;
import com.hl.fruitmall.mapper.CommodityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author Hl
 * @create 2020/12/28 17:19
 */
@Component("globalUtils")
public class GlobalUtils {

    public static final int[] PUNISHMENT = {1, 3, 7, 30, 365, -1};

    public static final int MAX_VIOLATION = 5;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private CommodityMapper commodityMapper;

    public Date getBanTime(Date create, Integer violation, int day) {
        Calendar cal = Calendar.getInstance();
        day = PUNISHMENT[day];
        if (day == -1) {
            cal.setTime(create);
        } else {
            if (violation == PUNISHMENT.length * MAX_VIOLATION) {
                cal.setTime(create);
                day = PUNISHMENT[MAX_VIOLATION];
            } else {
                cal.setTime(new Date());
                if (violation > MAX_VIOLATION) {
                    day += PUNISHMENT[(violation / MAX_VIOLATION) - 1];
                }
            }
        }
        cal.add(Calendar.DATE, day);
        return cal.getTime();
    }

    public Date[] strToDate(String startTime, String endTime) {
        Date[] dates = new Date[2];
        Date start = null, end = null;
        if (null != startTime
                && null != endTime
                && !"".equals(startTime)
                && !"".equals(endTime)) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            try {
                start = format.parse(startTime);
                end = format.parse(endTime);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        dates[0] = start;
        dates[1] = end;
        return dates;
    }

    public void delCache(Integer id) {
        String key = RedisKeyEnum.COMMODITY_Z_SET.getKey();
        List<Commodity> commodities = commodityMapper.selectByShopId(id);
        List<Integer> ids = new ArrayList<>();
        commodities.forEach(c -> {
            ids.add(c.getId());
        });
        redisTemplate.opsForZSet().remove(key, ids);
        redisTemplate.opsForHash().delete(RedisKeyEnum.COMMODITY_HASH.getKey(), ids);
    }
}

