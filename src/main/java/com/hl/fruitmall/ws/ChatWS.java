package com.hl.fruitmall.ws;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Hl
 * @create 2021/1/25 23:15
 */
@ServerEndpoint("/chat/{phone}/{toPhone}")
@Component
public class ChatWS {

    @Autowired
    private RedisTemplate redisTemplate;

    private Session session;

    private static ConcurrentHashMap<String, ChatWS> map = new ConcurrentHashMap<>();

    @OnOpen
    public void onOpen(@PathParam("phone") String phone,
                       @PathParam("toPhone") String toPhone,
                       Session session) {
        this.session = session;
        if (!map.containsKey(phone)) {
            map.put(phone, this);
        }
        if (!toPhone.equals("null")) {
            // 客户端连接客服 先获取聊天记录
        }
    }

    @OnMessage
    public void onMessage(Session session, String msg) {
        Message message = JSON.parseObject(msg, Message.class);
        // 后面等客户端写出来了  完成客服功能  记录聊天记录  转发消息
        /*ChatWS chatWS = map.get(message.getFromPhone());
        Message message1 = new Message();
        message1.setFromPhone("18211461718");
        message1.setFromPhone(message.getFromName());
        message1.setContent("我很好");
        message1.setAvatar("https://hl-fruit-mall.oss-cn-guangzhou.aliyuncs.com/2020/12/01/358d6b989d554015ba230e441f0d60e1avatar.jpg");
        message1.setToPhone("18211461717");
        message1.setToName("message");
        try {
            chatWS.session.getBasicRemote().sendText(JSON.toJSONString(message1));
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }

    /**
     * 发送消息
     */
    public void send() {
    }

    @OnClose
    public void onClose(Session session, @PathParam("phone") String phone) {
        if (map.containsKey(phone)) {
            map.remove(phone);
        }
    }

    @OnError
    public void onError(Throwable e) {
        System.out.println(e);
    }
}
