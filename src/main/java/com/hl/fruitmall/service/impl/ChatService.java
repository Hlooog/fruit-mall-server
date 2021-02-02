package com.hl.fruitmall.service.impl;

import com.alibaba.fastjson.JSON;
import com.hl.fruitmall.common.enums.RedisKeyEnum;
import com.hl.fruitmall.common.uitls.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author Hl
 * @create 2021/2/1 17:37
 */
@Service("chatService")
public class ChatService {

    @Autowired
    private RedisTemplate redisTemplate;

    public R getLinkUser() {
        Set<Map<String, String>> set = redisTemplate.opsForZSet()
                .reverseRange(RedisKeyEnum.SERVICE_LINK_USER_KEY.getKey(), 0, 49);
        Map<String, Integer> map = redisTemplate.opsForHash().entries(RedisKeyEnum.CHAT_UNREAD_NUMBER_KEY.getKey());
        return R.ok(new HashMap<String, Object>() {
            {
                put("link", set);
                put("unread", map);
            }
        });
    }

    public R recordGet(String phone, Integer cur) {
        String key = String.format(RedisKeyEnum.CHAT_READ_RECORD_KEY.getKey(), phone);
        Set set = redisTemplate.opsForZSet().reverseRange(key, (cur - 1) * 10, cur * 10 - 1);
        redisTemplate.opsForHash().delete(RedisKeyEnum.CHAT_UNREAD_NUMBER_KEY.getKey(), phone);
        int size = set.size();
        Map[] maps = new Map[size];
        Iterator iterator = set.iterator();
        while (iterator.hasNext()) {
            String next = (String) iterator.next();
            Map map = JSON.parseObject(next, Map.class);
            maps[--size] = map;
        }
        return R.ok(maps);
    }
}
