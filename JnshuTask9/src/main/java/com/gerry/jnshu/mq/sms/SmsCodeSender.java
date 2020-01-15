package com.gerry.jnshu.mq.sms;


import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.gerry.jnshu.core.redis.RedisCache;
import com.gerry.jnshu.core.utils.StringUtils;
import com.gerry.jnshu.pojo.SmsInfo;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.gerry.jnshu.constants.Constant.SMS_KEY_PATTERN;

@Component
public class SmsCodeSender {

    @Value("${aliyun.smssdk.accessKeyId}")
    private String accessKeyId;

    @Value("${aliyun.smssdk.accessKeySecret}")
    private String accessKeySecret;

    @Value("${aliyun.smssdk.signName}")
    private String signName;
    @Value("${aliyun.smssdk.templateCode}")
    private String templateCode;

    @Value("${aliyun.smssdk.product}")
    private String product;

    @Value("${aliyun.smssdk.domain}")
    private String domain;

    @Value("${aliyun.smssdk.defaultConnectTimeout}")
    private String defaultConnectTimeout;

    @Value("${aliyun.smssdk.defaultReadTimeout}")
    private String defaultReadTimeout;

    @Autowired
    private RedisCache redisCache;

    @Value("${aliyun.smssdk.expireTime}")
    private int expireTime;


    public SendSmsResponse sendMessage(SmsInfo smsInfo) {

        try {
            //可自助调整超时时间
            System.setProperty("sun.net.client.defaultConnectTimeout", defaultConnectTimeout);
            System.setProperty("sun.net.client.defaultReadTimeout", defaultReadTimeout);

            //初始化acsClient,暂不支持region化
            IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);

            DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);

            IAcsClient acsClient = new DefaultAcsClient(profile);

            //组装请求对象-具体描述见控制台-文档部分内容
            SendSmsRequest request = new SendSmsRequest();
            //必填:待发送手机号
            request.setPhoneNumbers(smsInfo.getPhoneNum());
            //必填:短信签名-可在短信控制台中找到
            request.setSignName(signName);
            //必填:短信模板-可在短信控制台中找到
            request.setTemplateCode("{\"code\":\"" + smsInfo.getContent() + "\"}");
            //可选:模板中的变量替换JSON串,如模板内容为"您的验证码为${code},有效时间5分钟。" ,此处的值为
            request.setTemplateParam(smsInfo.getContent());

            //选填-上行短信扩展码(无特殊需求用户请忽略此字段)
            //request.setSmsUpExtendCode("90997");

            //可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
            request.setOutId(smsInfo.getOutId());
            //hint 此处可能会抛出异常，注意catch

            SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
            if (sendSmsResponse == null) {
                smsInfo.setResCode(2);
                smsInfo.setResMsg("sendSmsResponse为空，验证码发送失败");
            } else if (!StringUtils.isEmpty(sendSmsResponse.getCode()) && sendSmsResponse.getCode().equals("OK")) {
                smsInfo.setResCode(1);
                smsInfo.setResMsg("验证码发送成功！");
                //发送成功存入缓存
                redisCache.setCacheObject(SMS_KEY_PATTERN + smsInfo.getPhoneNum() + "_" + smsInfo.getContent(), smsInfo, expireTime, TimeUnit.MINUTES);
            } else {
                smsInfo.setResCode(2);
                smsInfo.setResMsg(StringUtils.isEmpty(sendSmsResponse.getMessage()) ? "请稍后重试" : sendSmsResponse.getMessage());
            }
            return sendSmsResponse;
        } catch (ClientException e) {
            e.printStackTrace();
            smsInfo.setResCode(2);
            smsInfo.setResMsg("调用阿里云sms发生错误");
        }

        return null;

    }

}
