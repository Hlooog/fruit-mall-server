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
        //?????????ascClient?????????????????????
        final String product = "Dysmsapi";//??????API??????????????????????????????????????????????????????
        final String domain = "dysmsapi.aliyuncs.com";//??????API???????????????????????????????????????????????????
        //???????????????AK
        final String accessKeyId = keyId;//??????accessKeyId,?????????????????????2
        final String accessKeySecret = keySecret;//??????accessKeySecret????????????????????????2

        //?????????ascClient,??????????????????region??????????????????
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId,
                accessKeySecret);
        DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
        IAcsClient acsClient = new DefaultAcsClient(profile);

        //??????????????????
        SendSmsRequest request = new SendSmsRequest();
        //??????post??????
        request.setMethod(MethodType.POST);
        //??????:???????????????????????????????????????????????????????????????????????????????????????1000???????????????,??????????????????????????????????????????????????????,????????????????????????????????????????????????????????????????????????/??????????????????????????????????????????????????????+???????????????85200000000???
        request.setPhoneNumbers(phone);
        //??????:????????????-??????????????????????????????
        request.setSignName(singName);
        //??????:????????????-?????????????????????????????????????????????/????????????????????????????????????/?????????????????????
        request.setTemplateCode(templateCode);
        //??????:????????????????????????JSON???,??????????????????"?????????${name},??????????????????${code}"???,???????????????
        //????????????:??????JSON?????????????????????,??????????????????JSON???????????????????????????,???????????????????????????\r\n????????????JSON??????????????????\\r\\n,???????????????JSON????????????????????????
        //?????????request.setTemplateParam("{\"??????1\":\"???1\",\"??????2\":\"???2\",\"??????3\":\"???3\"}")
        request.setTemplateParam("{\"code\": '" + code + "'}");
        //??????-?????????????????????(????????????????????????7??????????????????????????????????????????????????????)
        //request.setSmsUpExtendCode("90997");

        //??????:outId?????????????????????????????????,?????????????????????????????????????????????????????????

        //????????????????????????ClientException??????
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

    public R closeSend(String phone) {
        String key = String.format(RedisKeyEnum.CLOSE_SHOP_KEY.getKey(), phone);
        send(key, phone);
        return R.ok();
    }

    public R editSend(String phone) {
        String key = String.format(RedisKeyEnum.UPDATE_PASSWORD_KEY.getKey(), phone);
        send(key, phone);
        return R.ok();
    }
}
