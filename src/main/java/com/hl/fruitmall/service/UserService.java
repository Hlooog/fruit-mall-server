package com.hl.fruitmall.service;

import com.hl.fruitmall.common.uitls.R;
import com.hl.fruitmall.entity.bean.User;
import com.hl.fruitmall.entity.vo.LoginVO;

import javax.servlet.http.HttpServletRequest;

/**
 * 用户表(User)表服务接口
 *
 * @author hl
 * @since 2020-11-21 00:33:41
 */
public interface UserService {

    R adminLogin(LoginVO loginVO);

    R logout(HttpServletRequest request);

    R page(Integer cur, String key, String startTime, String endTime);

    R banGeneral(Integer id, Integer days);

    R setService(Integer id);

    R banMerchant(Integer id, Integer days);

    User checkUser(String field,Object value);

    R cancelMerchant(Integer id);

    R getList(Integer code);

    R cancelService(Integer id);

    R pageMerchant(Integer cur, String key, String startTime, String endTime);

    R merchantLogin(LoginVO loginVO);
}