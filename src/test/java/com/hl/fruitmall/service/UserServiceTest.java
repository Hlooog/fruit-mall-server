package com.hl.fruitmall.service;

import com.hl.fruitmall.common.uitls.GlobalUtils;
import com.hl.fruitmall.entity.bean.User;
import com.hl.fruitmall.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Hl
 * @create 2021/1/27 10:55
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserServiceTest {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private GlobalUtils globalUtils;

    @Test
    void test() {
        String startTime = "", endTime = "";
        Date[] dates = globalUtils.strToDate(startTime, endTime);
        Date start = dates[0], end = dates[1];
        System.out.println(start == null);
        System.out.println(start);
        System.out.println(end == null);
        System.out.println(end);
    }

    @Test
    void test1(){
        User user = userMapper.selectByField("id", 10009);
        System.out.println(!user.getBanTime().before(user.getCreateTime()));
        System.out.println(!user.getBanTime().after(user.getCreateTime()));
    }

    @Test
    void test2(){
        List<User> list = new ArrayList<>();
        long phone = 12345678950l;
        for (int i = 0; i < 100; i++) {
            User user = new User();
            user.setPhone(String.valueOf(phone + i));
            user.setPassword("e10adc3949ba59abbe56e057f20f883e");
            user.setAvatar("https://hl-fruit-mall.oss-cn-guangzhou.aliyuncs.com/2020/12/01/358d6b989d554015ba230e441f0d60e1avatar.jpg");
            user.setNickname("user" + (i + 41));
            list.add(user);
        }
        userMapper.insertBatch(list);
    }

    @Test
    void test3(){
        List<Map<Date, Integer>> report = userMapper.getReport();
        System.out.println(report);
    }
}
