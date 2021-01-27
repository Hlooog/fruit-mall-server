package com.hl.fruitmall.service;

import com.hl.fruitmall.common.uitls.R;

import javax.servlet.http.HttpServletRequest;

/**
 * 用户表(User)表服务接口
 *
 * @author hl
 * @since 2020-11-21 00:33:41
 */
public interface UserService {

    R adminLogin(String phone, String password);

    R logout(HttpServletRequest request);

    R page(Integer cur, String key, String startTime, String endTime, Integer code);

    R ban(Integer id, Integer days);

    R setService(Integer id);
}