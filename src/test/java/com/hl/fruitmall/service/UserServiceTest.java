package com.hl.fruitmall.service;

import com.hl.fruitmall.common.uitls.GlobalUtils;
import com.hl.fruitmall.entity.bean.User;
import com.hl.fruitmall.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

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
}
