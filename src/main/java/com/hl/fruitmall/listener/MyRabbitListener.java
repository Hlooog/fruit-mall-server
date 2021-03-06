package com.hl.fruitmall.listener;

import com.alibaba.fastjson.JSON;
import com.hl.fruitmall.config.RabbitConfig;
import com.hl.fruitmall.entity.bean.IScore;
import com.hl.fruitmall.service.CommodityService;
import com.hl.fruitmall.service.OSSService;
import com.hl.fruitmall.service.OrdersService;
import com.hl.fruitmall.service.impl.SmsService;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author Hl
 * @create 2021/1/30 9:34
 */
@Component
@Slf4j
public class MyRabbitListener {

    @Autowired
    private OSSService ossService;

    @Autowired
    private SmsService smsService;

    @Autowired
    private CommodityService commodityService;

    @Autowired
    private OrdersService ordersService;

    @RabbitListener(queues = {RabbitConfig.QUEUE_OSS_DELETE})
    @RabbitHandler
    public void ossListener(List<String> msg, Channel channel, Message message) throws IOException {
        try {
            ossService.delete(msg);
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (Exception e) {
            if (message.getMessageProperties().getRedelivered()) {
                channel.basicReject(message.getMessageProperties().getDeliveryTag(), false);
            } else {
                channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
            }
        }
    }

    @RabbitListener(queues = {RabbitConfig.QUEUE_SMS_SEND})
    @RabbitHandler
    public void smsListener(Map<String, String> msg, Channel channel, Message message) throws IOException {
        try {
            smsService.invoke(msg.get("phone"), msg.get("code"));
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (Exception e) {
            if (message.getMessageProperties().getRedelivered()) {
                channel.basicReject(message.getMessageProperties().getDeliveryTag(), false);
            } else {
                channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
            }
        }
    }

    @RabbitListener(queues = {RabbitConfig.QUEUE_RECORD})
    @RabbitHandler
    public void recordListener(String msg, Channel channel, Message message ) throws IOException{
        try {
            IScore iScore = JSON.parseObject(msg, IScore.class);
            commodityService.insertScore(iScore);
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (Exception e) {
            if (message.getMessageProperties().getRedelivered()) {
                channel.basicReject(message.getMessageProperties().getDeliveryTag(), false);
            } else {
                channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
            }
        }
    }

    @RabbitListener(queues = {RabbitConfig.QUEUE_ORDER_CANCEL})
    @RabbitHandler
    public void cancelListener(String orderId, Channel channel, Message message) throws IOException{
        try {
            ordersService.cancelOrder(orderId);
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (Exception e) {
            if (message.getMessageProperties().getRedelivered()) {
                channel.basicReject(message.getMessageProperties().getDeliveryTag(), false);
            } else {
                channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
            }
        }
    }
}
