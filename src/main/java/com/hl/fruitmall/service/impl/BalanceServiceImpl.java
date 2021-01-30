package com.hl.fruitmall.service.impl;

import com.auth0.jwt.JWT;
import com.hl.fruitmall.common.enums.ExceptionEnum;
import com.hl.fruitmall.common.enums.RedisKeyEnum;
import com.hl.fruitmall.common.enums.WithdrawStatusEnum;
import com.hl.fruitmall.common.exception.GlobalException;
import com.hl.fruitmall.common.uitls.R;
import com.hl.fruitmall.entity.bean.WithdrawalRecord;
import com.hl.fruitmall.entity.vo.BalanceVO;
import com.hl.fruitmall.entity.vo.WithdrawVO;
import com.hl.fruitmall.mapper.BalanceMapper;
import com.hl.fruitmall.mapper.WithdrawalRecordMapper;
import com.hl.fruitmall.service.BalanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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
    private WithdrawalRecordMapper withdrawalRecordMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public R get(HttpServletRequest request) {
        String phone = JWT.decode(request.getHeader("X-Token")).getAudience().get(1);
        BalanceVO balanceVO = balanceMapper.select(phone);
        return R.ok(balanceVO);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public R withdraw(WithdrawVO withdrawVO, HttpServletRequest request) {
        String phone = JWT.decode(request.getHeader("X-Token")).getAudience().get(1);
        if (!phone.equals(withdrawVO.getPhone())) {
            throw new GlobalException(ExceptionEnum.PHONE_NUMBER_HAS_ERROR);
        }
        String key = String.format(RedisKeyEnum.WITHDRAW_CODE_KEY.getKey(), withdrawVO.getPhone());
        String code = (String) redisTemplate.opsForValue().get(key);
        if (!code.equals(withdrawVO.getCode())) {
            throw new GlobalException(ExceptionEnum.VERIFICATION_CODE_ERROR);
        }
        BalanceVO balanceVO = balanceMapper.select(withdrawVO.getPhone());
        if (withdrawVO.getAmount().compareTo(balanceVO.getWithdrawAble()) == 1) {
            throw new GlobalException(ExceptionEnum.WITHDRAW_THAN_WITHDRAWABLE);
        }
        balanceVO.setWithdrawAble(balanceVO.getWithdrawAble().subtract(withdrawVO.getAmount()));
        balanceVO.setFrozen(balanceVO.getFrozen().add(withdrawVO.getAmount()));
        WithdrawalRecord withdrawalRecord = new WithdrawalRecord(
                withdrawVO.getPhone(),
                withdrawVO.getAccount(),
                WithdrawStatusEnum.REVIEW.getCode(),
                withdrawVO.getAmount()
        );
        balanceMapper.update(withdrawVO.getPhone(), balanceVO);
        withdrawalRecordMapper.insert(withdrawalRecord);
        return R.ok();
    }
}