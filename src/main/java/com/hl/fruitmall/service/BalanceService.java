package com.hl.fruitmall.service;

import com.hl.fruitmall.common.uitls.R;
import com.hl.fruitmall.entity.vo.WithdrawVO;

import javax.servlet.http.HttpServletRequest;

/**
 * 余额表(Balance)表服务接口
 *
 * @author hl
 * @since 2020-11-21 00:33:29
 */
public interface BalanceService {

    R get(HttpServletRequest request);

    R withdraw(WithdrawVO withdrawVO, HttpServletRequest request);

}