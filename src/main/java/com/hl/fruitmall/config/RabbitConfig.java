package com.hl.fruitmall.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Hl
 * @create 2021/1/30 9:05
 */
@Configuration
@Slf4j
public class RabbitConfig {

    public static final long DLX_TIME = 1000 * 60 * 3;

    public static final String EXCHANGE_OSS_DELETE = "fruit.mall.oss.delete";
    public static final String EXCHANGE_SMS_SEND = "fruit.mall.sms.send";
    public static final String EXCHANGE_RECORD = "fruit.mall.record";
    public static final String EXCHANGE_ORDER_CANCEL_DLX = "fruit.mall.order.cancel.dlx";
    public static final String EXCHANGE_ORDER_CANCEL = "fruit.mall.order.cancel";

    public static final String QUEUE_OSS_DELETE = "fruit.mall.oss.delete";
    public static final String QUEUE_SMS_SEND = "fruit.mall.sms.send";
    public static final String QUEUE_RECORD = "fruit.mall.record";
    public static final String QUEUE_ORDER_CANCEL_DLX = "fruit.mall.order.cancel.dlx";
    public static final String QUEUE_ORDER_CANCEL = "fruit.mall.order.cancel";


    public static final String ROUTING_OSS_DELETE = "fruit.mall.oss.delete.key";
    public static final String ROUTING_SMS_SEND = "fruit.mall.sms.send.key";
    public static final String ROUTING_RECORD = "fruit.mall.record.key";
    public static final String ROUTING_ORDER_CANCEL = "fruit.mall.order.cancel";
    public static final String ROUTING_ORDER_CANCEL_DLX = "fruit.mall.order.cancel.dlx";

    /**
     * 消息的Confirm机制和Returns机制
     *
     * @return
     */
    /*private RabbitTemplate.ConfirmCallback confirmCallback = new RabbitTemplate.ConfirmCallback() {
        @Override
        public void confirm(CorrelationData correlationData, boolean ack, String cause) {
            if (ack) {
                log.info("confirm机制启动，消息接收成功");
            } else {
                log.error("confirm机制启动,消息发送不成功");
            }
        }
    };

    private RabbitTemplate.ReturnCallback returnCallback = new RabbitTemplate.ReturnCallback() {
        @Override
        public void returnedMessage(Message message, int replyCode, String replyText,
                                    String exchange, String routingKey) {
            log.info("return机制启动", routingKey);
        }
    };

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setConfirmCallback(confirmCallback);
        rabbitTemplate.setReturnCallback(returnCallback);
        return rabbitTemplate;
    }
    */
    @Bean
    public DirectExchange ossExchange() {
        return new DirectExchange(EXCHANGE_OSS_DELETE);
    }

    @Bean
    public DirectExchange smsExchange() {
        return new DirectExchange(EXCHANGE_SMS_SEND);
    }

    @Bean
    public DirectExchange recordExchange() {
        return new DirectExchange(EXCHANGE_RECORD);
    }

    @Bean
    public DirectExchange dlxExchange(){
        return new DirectExchange(EXCHANGE_ORDER_CANCEL_DLX);
    }

    @Bean
    public DirectExchange cancelExchange(){
        return new DirectExchange(EXCHANGE_ORDER_CANCEL);
    }

    @Bean
    public Queue ossQueue() {
        return new Queue(QUEUE_OSS_DELETE, true);
    }

    @Bean
    public Queue smsQueue() {
        return new Queue(QUEUE_SMS_SEND, true);
    }

    @Bean
    public Queue recordQueue(){
        return new Queue(QUEUE_RECORD, true);
    }

    @Bean
    public Queue dlxQueue(){
        return QueueBuilder.durable(QUEUE_ORDER_CANCEL_DLX)
                .withArgument("x-dead-letter-exchange", EXCHANGE_ORDER_CANCEL)  // 绑定超时时转发的交换机
                .withArgument("x-message-ttl", DLX_TIME) // 超时时间
                .withArgument("x-dead-letter-routing-key", ROUTING_ORDER_CANCEL) // 转发消息使用的绑定键
                .build();
    }

    @Bean
    public Queue cancelQueue(){
        return new Queue(QUEUE_ORDER_CANCEL);
    }

    @Bean
    public Binding ossRoutingKey() {
        return BindingBuilder.bind(ossQueue()).to(ossExchange()).with(ROUTING_OSS_DELETE);
    }

    @Bean
    public Binding smsRoutingKey() {
        return BindingBuilder.bind(smsQueue()).to(smsExchange()).with(ROUTING_SMS_SEND);
    }

    @Bean
    public Binding recordRoutingKey(){
        return BindingBuilder.bind(recordQueue()).to(recordExchange()).with(ROUTING_RECORD);
    }

    @Bean
    public Binding cancelDlxRoutingKey(){
        return BindingBuilder.bind(dlxQueue()).to(dlxExchange()).with(ROUTING_ORDER_CANCEL_DLX);
    }

    @Bean
    public Binding cancelRoutingKey(){
        return BindingBuilder.bind(cancelQueue()).to(cancelExchange()).with(ROUTING_ORDER_CANCEL);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

}
