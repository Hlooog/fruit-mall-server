package com.hl.fruitmall.service.impl;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.hl.fruitmall.common.enums.RedisKeyEnum;
import com.hl.fruitmall.common.uitls.R;
import com.hl.fruitmall.config.RabbitConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @author Hl
 * @create 2021/1/30 17:31
 */
@Service("smsService")
public class SmsService {

    public static final long TIME = 60 * 5;

    @Value("${aliyun.sms.key-id}")
    private String keyId;
    @Value("${aliyun.sms.key-secret}")
    private String keySecret;
    @Value("${aliyun.sms.sign-name}")
    private String singName;
    @Value("${aliyun.sms.template-code}")
    private String templateCode;

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    RedisTemplate redisTemplate;

    private void send(String key,String phone) {
        String code = (String) redisTemplate.opsForValue().get(key);
        if (code == null) {
            Random random = new Random();
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < 6; i++) {
                sb.append(random.nextInt(10));
            }
            code = sb.toString();
        }
        redisTemplate.opsForValue().set(key, code, TIME, TimeUnit.SECONDS);
        Map<String, String> map = new HashMap<>();
        map.put("phone", phone);
        map.put("code", code);
        rabbitTemplate.convertAndSend(RabbitConfig.EXCHANGE_SMS_SEND, RabbitConfig.ROUTING_SMS_SEND, map);
    }

    public void invoke(String phone, String code) throws ClientException {
        //初始化ascClient需要的几个参数
        final String product = "Dysmsapi";//短信API产品名称（短信产品名固定，无需修改）
        final String domain = "dysmsapi.aliyuncs.com";//短信API产品域名（接口地址固定，无需修改）
        //替换成你的AK
        final String accessKeyId = keyId;//你的accessKeyId,参考本文档步骤2
        final String accessKeySecret = keySecret;//你的accessKeySecret，参考本文档步骤2

        //初始化ascClient,暂时不支持多region（请勿修改）
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId,
                accessKeySecret);
        DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
        IAcsClient acsClient = new DefaultAcsClient(profile);

        //组装请求对象
        SendSmsRequest request = new SendSmsRequest();
        //使用post提交
        request.setMethod(MethodType.POST);
        //必填:待发送手机号。支持以逗号分隔的形式进行批量调用，批量上限为1000个手机号码,批量调用相对于单条调用及时性稍有延迟,验证码类型的短信推荐使用单条调用的方式；发送国际/港澳台消息时，接收号码格式为国际区号+号码，如“85200000000”
        request.setPhoneNumbers(phone);
        //必填:短信签名-可在短信控制台中找到
        request.setSignName(singName);
        //必填:短信模板-可在短信控制台中找到，发送国际/港澳台消息时，请使用国际/港澳台短信模版
        request.setTemplateCode(templateCode);
        //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
        //友情提示:如果JSON中需要带换行符,请参照标准的JSON协议对换行符的要求,比如短信内容中包含\r\n的情况在JSON中需要表示成\\r\\n,否则会导致JSON在服务端解析失败
        //参考：request.setTemplateParam("{\"变量1\":\"值1\",\"变量2\":\"值2\",\"变量3\":\"值3\"}")
        request.setTemplateParam("{\"code\": '" + code + "'}");
        //可选-上行短信扩展码(扩展码字段控制在7位或以下，无特殊需求用户请忽略此字段)
        //request.setSmsUpExtendCode("90997");

        //可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者

        //请求失败这里会抛ClientException异常
        SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
    }

    public R withdrawSend(String phone) {
        String key = String.format(RedisKeyEnum.WITHDRAW_CODE_KEY.getKey(), phone);
        send(key, phone);
        return R.ok();
    }

    public R loginSend(String phone) {
        String key = String.format(RedisKeyEnum.LOGIN_CODE_KEY.getKey(), phone);
        send(key, phone);
        return R.ok();
    }
}
