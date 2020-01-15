package com.gerry.jnshu.mq.sms;

import com.gerry.jnshu.pojo.SmsInfo;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class SmsCodeReceiver {

    @Autowired
    private SmsCodeSender sendSmsCodeService;


    @RabbitHandler
    @RabbitListener(
            bindings = @QueueBinding(
                    value = @Queue(value = "sms-code-queue", durable = "true"),
                    exchange = @Exchange(name = "sms-exchange", durable = "true",
                            type = "topic"),
                    key = "sms.login.code"
            )
    )
    public void onOrderMessage(@Payload SmsInfo smsInfo,
                               @Headers Map<String, Object> headers,
                               Channel channel) throws Exception {
        //消费者消费消息
        System.out.println("收到消息，开始消费....");
        System.out.println("发送短信 id:" + smsInfo.getId());
//        sendSmsCodeService.sendMessage(smsInfo);
        Long deliverTag = (Long) headers.get(AmqpHeaders.DELIVERY_TAG);
        //给 MQ 一个反馈
        channel.basicAck(deliverTag, false);

    }
}
