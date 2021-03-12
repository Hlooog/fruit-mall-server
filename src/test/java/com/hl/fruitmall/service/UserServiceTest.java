package com.hl.fruitmall.service;

import com.hl.fruitmall.common.uitls.GlobalUtils;
import com.hl.fruitmall.entity.bean.User;
import com.hl.fruitmall.entity.vo.WithdrawVO;
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
    void test1() {
        User user = userMapper.selectByField("id", 10009);
        System.out.println(!user.getBanTime().before(user.getCreateTime()));
        System.out.println(!user.getBanTime().after(user.getCreateTime()));
    }

    @Test
    void test2() {
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
    void test3() {
        List<Map<Date, Integer>> report = userMapper.getReport();
        System.out.println(report);
    }

    @Test
    void test4() {
        String names = "乐木霆 锐新俊 严川皓 " +
                "江楠尧 江幽方 杨星辰 " +
                "骏烨军 宪崇石 王辉栋 " +
                "岳华藏 周曦都 赵鹤茂 " +
                "王胤霖 赵诺翰 赵颂崴 " +
                "载安宁 孙致霖 杨康引 " +
                "烨璟天 迅轩馨 杨天厉 " +
                "周诚文 贺之珩 赵语槿 " +
                "蒲宏达 之宏毅 解澄邈 " +
                "隗思淼 索浩博 李骏寒 " +
                "王宸君 井华容 金景明 " +
                "奕鹏池 伏浦泽 鞠文柏 " +
                "昔昆明 奇锐利 竹彦昌 " +
                "解晏立 李明鸿 李明轩 " +
                "子无 伊耀 靳言 简清 " +
                "清迅 云谦 谦然 容启 " +
                "炫明 乐驹 修宇 擎轩 " +
                "澈幕 鹭洋 玉枫 昊焱 " +
                "宸浩 圣朴 耀然 慕宇 " +
                "弘坤 源元 瑾远 炎瑞 " +
                "贤嘉 静嘉 少言 思德 " +
                "聪健 叶轩 天钧 祁汜 " +
                "博笃 炎誉 启博 奕维 " +
                "展畅 胜天 千尘 瀚玉 " +
                "捷迅 靖宇 洛冰 言风 " +
                "宇辰 文博 耀然 靳言 " +
                "楚仁 无洛 裴煜 锦程 " +
                "瀚泽 浩帆 弘璋 博宇 " +
                "蔺玄觞 萧楚容 洛麟于 云浩坤 " +
                "洛仁耀 宁为泽 木君言 颜谦熙 " +
                "萧墨尘 沐云霄 安卿尘 安宏其 " +
                "叶寒山 安于乐 洛闲云 风轩旋 " +
                "枫叶羽 言幕翊 柳廷阳 洛卓羽 " +
                "蔺渊川 王洛初 易书希 叶清彦 " +
                "凌剑天 叶寒云 萧北铭 叶翌辰 " +
                "陆修冰 雷雪昊 玄墨染 狄云义 " +
                "莫言宇 龙宇昊 花满楼 莫风昕 " +
                "安简瑜 宇若径 苏朴博 易晨然 " +
                "安瑾然 洛秋凉 林楚黎 南承曜 " +
                "秦辰钧 凌尘之 云熙然 宁陌夙 " +
                "闻焕日 墨子晚 云霆弦 花煜若";
        int id = 10002;
        String[] s = names.split(" ");
        for (int i = 0; i < s.length; i++) {
            userMapper.updateByField("id", id + i , "nickname", s[i]);
        }
    }

    @Test
    void test5(){
        Class a = int.class;
        Class b = String.class;
        Class c = Integer.class;
        Class d = WithdrawVO.class;

        System.out.println();

        /*withdrawVO.setAccount("1123");
        withdrawVO.setAmount(new BigDecimal(12));
        withdrawVO.setCode("132");
        withdrawVO.setPhone("151515");
        withdrawVO.setShopId(100212);
        Field[] fields = withdrawVO.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                Object o = field.get(withdrawVO);
                System.out.println(o);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }*/
    }


    public static boolean isWrapClass(Class clz) {
        try {
            return ((Class) clz.getField("TYPE").get(null)).isPrimitive();
        } catch (Exception e) {
            return false;
        }
    }
}
