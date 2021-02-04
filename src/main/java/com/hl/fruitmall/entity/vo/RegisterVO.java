package com.hl.fruitmall.entity.vo;

import com.hl.fruitmall.common.enums.RoleEnum;
import com.hl.fruitmall.entity.bean.User;
import lombok.Data;

import java.util.List;

/**
 * @author Hl
 * @create 2021/2/4 10:37
 */
@Data
public class RegisterVO {

    private String uuid;
    private String avatar;
    private String name;
    private String phone;
    private String password;
    private String code;
    private List<String> urlList;

    public User toUser(){
        User user = new User();
        user.setAvatar(avatar);
        user.setNickname(name);
        user.setPhone(phone);
        user.setPassword(password);
        user.setRoleType(RoleEnum.USER.getCode());
        return user;
    }
}
