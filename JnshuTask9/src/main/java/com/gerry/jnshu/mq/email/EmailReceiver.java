package com.gerry.jnshu.mq.email;

import com.gerry.jnshu.pojo.EmailInfo;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class EmailReceiver {

    @Autowired
    private EmailSender sendEmailService;


    @RabbitHandler
    @RabbitListener(
            bindings = @QueueBinding(
                    value = @Queue(value = "email-queue", durable = "true"),
                    exchange = @Exchange(name = "email-exchange", durable = "true",
                            type = "topic"),
                    key = "email.#"
            )
    )
    public void onEmailMessage(@Payload EmailInfo emailInfo,
                               @Headers Map<String, Object> headers,
                               Channel channel) throws Exception {
        //消费者消费消息
        System.out.println("收到消息，开始消费....");
        System.out.println("发送邮件 id:" + emailInfo.getId());
        sendEmailService.sendMail(emailInfo);
        Long deliverTag = (Long) headers.get(AmqpHeaders.DELIVERY_TAG);
        //给 MQ 一个反馈
        channel.basicAck(deliverTag, false);

    }
}
