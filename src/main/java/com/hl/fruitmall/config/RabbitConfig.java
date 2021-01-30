package com.hl.fruitmall.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
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

    public static final String EXCHANGE_OSS_DELETE = "fruit.mall.oss.delete";
    public static final String EXCHANGE_SMS_SEND = "fruit.mall.sms.send";

    public static final String QUEUE_OSS_DELETE = "fruit.mall.oss.delete";
    public static final String QUEUE_SMS_SEND = "fruit.mall.sms.send";


    public static final String ROUTING_OSS_DELETE = "fruit.mall.oss.delete.key";
    public static final String ROUTING_SMS_SEND = "fruit.mall.sms.send.key";


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
    public Queue ossQueue() {
        return new Queue(QUEUE_OSS_DELETE, true);
    }

    @Bean
    public Queue smsQueue() {
        return new Queue(QUEUE_SMS_SEND, true);
    }

    @Bean
    public Binding ossRoutingKey() {
        return BindingBuilder.bind(ossQueue()).to(ossExchange()).with(ROUTING_OSS_DELETE);
    }

    @Bean
    public Binding smsRoutingKey(){
        return BindingBuilder.bind(smsQueue()).to(smsExchange()).with(ROUTING_SMS_SEND);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

}
