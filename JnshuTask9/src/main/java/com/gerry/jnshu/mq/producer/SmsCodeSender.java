package com.gerry.jnshu.mq.producer;

import com.gerry.jnshu.core.utils.UUID;
import com.gerry.jnshu.pojo.SmsInfo;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SmsCodeSender {

    @Autowired
    private RabbitTemplate rabbitTemplate;

//    @Autowired
//    private BatchingRabbitTemplate batchingRabbitTemplate;

    public void sendSMSLoginCode(String phoneNum,String content) throws Exception{

        SmsInfo smsInfo = new SmsInfo();
        smsInfo.setId(UUID.fastUUID().toString());
        smsInfo.setContent(content);
        smsInfo.setOutId(UUID.fastUUID().toString());
        smsInfo.setPhoneNum(phoneNum);

        CorrelationData correlationData = new CorrelationData();
        correlationData.setId(smsInfo.getId());
        rabbitTemplate.convertAndSend("sms-exchange",
                "sms.login.code", //消息路由键
                 smsInfo,             //发送内容
                 correlationData);                      //消息唯一 id


    }
}
