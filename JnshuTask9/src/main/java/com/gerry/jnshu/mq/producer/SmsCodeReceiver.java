package com.gerry.jnshu.mq.producer;

import com.aliyuncs.exceptions.ClientException;
import com.gerry.jnshu.core.redis.RedisCache;
import com.gerry.jnshu.pojo.SmsInfo;
import com.gerry.jnshu.service.SendSmsCodeService;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import javax.net.ssl.SNIServerName;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Component
public class SmsCodeReceiver {

    @Autowired
    private SendSmsCodeService sendSmsCodeService;
    @Autowired
    private RedisCache redisCache;

    @Value("${aliyun.smssdk.expireTime}")
    private int expireTime;

    @RabbitHandler
    @RabbitListener(
            bindings = @QueueBinding(
                    value = @Queue(value="sms-code-queue",durable = "true"),
                    exchange = @Exchange(name = "sms-exchange",durable = "true",
                            type = "topic"),
                    key = "sms.login.code"
            )
    )
    public void onOrderMessage(@Payload SmsInfo smsInfo,
                               @Headers Map<String,Object> headers,
                               Channel channel) throws Exception{
        //消费者消费消息
        System.out.println("收到消息，开始消费....");
        System.out.println("发送短信 id:"+smsInfo.getId());
        try {
            sendSmsCodeService.sendMessage(smsInfo);
            Long deliverTag = (Long) headers.get(AmqpHeaders.DELIVERY_TAG);
            //给 MQ 一个反馈
            channel.basicAck(deliverTag,false);

            //发送成功让如缓存
            redisCache.setCacheObject("sms:code:"+smsInfo.getId(), smsInfo, expireTime, TimeUnit.MINUTES);
        }
        catch (ClientException e){

        }



    }
}
