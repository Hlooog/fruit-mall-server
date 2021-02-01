package com.hl.fruitmall.service;

import com.hl.fruitmall.common.uitls.R;

/**
 * 提现记录表(Withdraw)表服务接口
 *
 * @author hl
 * @since 2020-11-21 00:33:48
 */
public interface WithdrawService {

    R page(String phone, Integer cur, String startTime, String endTime, Integer status);

    R review(Integer id, String phone);

    R refuse(Integer id, String phone);
}