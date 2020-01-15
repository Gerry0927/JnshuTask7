package com.gerry.jnshu.mq.email;

import com.gerry.jnshu.core.utils.UUID;
import com.gerry.jnshu.pojo.EmailInfo;
import com.gerry.jnshu.pojo.SmsInfo;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class EmailSendService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

//    @Autowired
//    private BatchingRabbitTemplate batchingRabbitTemplate;

    public EmailInfo sendEmail(String address,String subject,String content){

        EmailInfo emailInfo = new EmailInfo();
        emailInfo.setToAddress(address);
        emailInfo.setSubject(subject);
        emailInfo.setContent(content);
        emailInfo.setId(UUID.fastUUID().toString());
        CorrelationData correlationData = new CorrelationData();
        correlationData.setId(emailInfo.getId());
        rabbitTemplate.convertAndSend("email-exchange",
                "email.register", //消息路由键
                emailInfo,             //发送内容
                correlationData);                      //消息唯一 id

        return emailInfo;
    }
}
