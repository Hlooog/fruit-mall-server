package com.hl.fruitmall.ws;

import com.alibaba.fastjson.JSON;
import com.hl.fruitmall.common.enums.RedisKeyEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Hl
 * @create 2021/1/25 23:15
 */
@ServerEndpoint("/chat/{phone}")
@Component
public class ChatWS {

    private static RedisTemplate redisTemplate;

    @Autowired
    public void setRedisTemplate(RedisTemplate redisTemplate) {
        ChatWS.redisTemplate = redisTemplate;
    }

    private Session session;

    private static ConcurrentHashMap<String, ChatWS> map = new ConcurrentHashMap<>();

    @OnOpen
    public void onOpen(@PathParam("phone") String phone,
                       Session session) {
        this.session = session;
        if (!map.containsKey(phone)) {
            map.put(phone, this);
        }
    }

    @OnMessage
    public void onMessage(Session session, String msg) {
        Message message = JSON.parseObject(msg, Message.class);
        // 后面等客户端写出来了  完成客服功能  记录聊天记录  转发消息
        String toPhone = message.getToPhone();
        String fromPhone = message.getFromPhone();
        ChatWS chatWS = map.get(toPhone);
        // 记录聊天记录
        String key = toPhone.equals("service") ? fromPhone : toPhone;
        long time = new Date().getTime();
        key = String.format(RedisKeyEnum.CHAT_READ_RECORD_KEY.getKey(), key);
        redisTemplate.opsForZSet().add(key, msg, time);
        Map<String, String> map = new HashMap<>();
        map.put("name", message.getFromName());
        map.put("avatar", message.getAvatar());
        map.put("phone", fromPhone);
        if (toPhone.equals("service")) {
            redisTemplate.opsForZSet().add(RedisKeyEnum.SERVICE_LINK_USER_KEY.getKey(), map, time);
            if (chatWS == null) {
                // 记录客服不在线记录聊天数
                redisTemplate.opsForHash().increment(RedisKeyEnum.CHAT_UNREAD_NUMBER_KEY.getKey(), fromPhone, 1);
            } else {
                try {
                    chatWS.session.getBasicRemote().sendText(JSON.toJSONString(message));
                    chatWS.session.getBasicRemote().sendText(JSON.toJSONString(map));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            try {
                chatWS.session.getBasicRemote().sendText(JSON.toJSONString(message));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * 发送消息
     */
    public void send() {
    }

    @OnClose
    public void onClose(@PathParam("phone") String phone,
                        Session session) {
        if (map.containsKey(phone)) {
            map.remove(phone);
        }
    }

    @OnError
    public void onError(Throwable e) {
        System.out.println(e);
    }
}
