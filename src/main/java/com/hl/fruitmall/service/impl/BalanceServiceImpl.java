package com.hl.fruitmall.service.impl;

import com.hl.fruitmall.common.enums.ExceptionEnum;
import com.hl.fruitmall.common.enums.RedisKeyEnum;
import com.hl.fruitmall.common.exception.GlobalException;
import com.hl.fruitmall.common.uitls.BalanceUtils;
import com.hl.fruitmall.common.uitls.R;
import com.hl.fruitmall.common.uitls.TokenUtils;
import com.hl.fruitmall.entity.vo.BalanceVO;
import com.hl.fruitmall.entity.vo.WithdrawVO;
import com.hl.fruitmall.mapper.BalanceMapper;
import com.hl.fruitmall.mapper.WithdrawMapper;
import com.hl.fruitmall.service.BalanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * 余额表(Balance)表服务实现类
 *
 * @author hl
 * @since 2020-11-21 00:33:30
 */
@Service("balanceService")
public class BalanceServiceImpl implements BalanceService {

    @Autowired
    private BalanceMapper balanceMapper;

    @Autowired
    private WithdrawMapper withdrawMapper;

    @Autowired
    private BalanceUtils balanceUtils;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public R get(HttpServletRequest request) {
        String phone = TokenUtils.getPhone(request);
        BalanceVO balanceVO = balanceMapper.select(phone);
        return R.ok(balanceVO);
    }


    @Override
    public R withdraw(WithdrawVO withdrawVO) {
        String phone = withdrawVO.getPhone();
        if (!phone.equals(withdrawVO.getPhone())) {
            throw new GlobalException(ExceptionEnum.PHONE_NUMBER_HAS_ERROR);
        }
        String key = String.format(RedisKeyEnum.WITHDRAW_CODE_KEY.getKey(), phone);
        String code = (String) redisTemplate.opsForValue().get(key);
        if (!code.equals(withdrawVO.getCode())) {
            throw new GlobalException(ExceptionEnum.VERIFICATION_CODE_ERROR);
        }
        balanceUtils.withdraw(withdrawVO);
        return R.ok();
    }
}