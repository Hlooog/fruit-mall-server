package com.hl.fruitmall.service.impl;

import com.auth0.jwt.JWT;
import com.hl.fruitmall.common.enums.ExceptionEnum;
import com.hl.fruitmall.common.enums.RedisKeyEnum;
import com.hl.fruitmall.common.exception.GlobalException;
import com.hl.fruitmall.common.uitls.R;
import com.hl.fruitmall.entity.bean.WithdrawalRecord;
import com.hl.fruitmall.entity.vo.BalanceVO;
import com.hl.fruitmall.entity.vo.WithdrawVO;
import com.hl.fruitmall.mapper.BalanceMapper;
import com.hl.fruitmall.service.BalanceService;
import com.hl.fruitmall.service.WithdrawalRecordService;
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
    private WithdrawalRecordService withdrawalRecordService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public R get(HttpServletRequest request) {
        Integer id = Integer.valueOf(JWT.decode(request.getHeader("X-Token")).getAudience().get(0));
        BalanceVO balanceVO = balanceMapper.select(id);
        return R.ok(balanceVO);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public R withdraw(WithdrawVO withdrawVO, HttpServletRequest request) {
        String key = String.format(RedisKeyEnum.WITHDRAW_CODE_KEY.getKey(), withdrawVO.getPhone());
        String code = (String) redisTemplate.opsForValue().get(key);
        if (!code.equals(withdrawVO.getCode())) {
            throw new GlobalException(ExceptionEnum.VERIFICATION_CODE_ERROR);
        }
        Integer id = Integer.valueOf(JWT.decode(request.getHeader("X-Token")).getAudience().get(0));
        BalanceVO balanceVO = balanceMapper.select(id);
        if (withdrawVO.getAmount().compareTo(balanceVO.getWithdrawAble()) == 1) {
            throw new GlobalException(ExceptionEnum.WITHDRAW_THAN_WITHDRAWABLE);
        }
        balanceVO.setWithdrawAble(balanceVO.getWithdrawAble().subtract(withdrawVO.getAmount()));
        balanceVO.setFrozen(balanceVO.getFrozen().add(withdrawVO.getAmount()));
        WithdrawalRecord withdrawalRecord = new WithdrawalRecord(
                id,
                withdrawVO.getAccount(),
                withdrawVO.getAmount()
        );
        balanceMapper.update(id,balanceVO);
        withdrawalRecordService.insert(withdrawalRecord);
        return R.ok();
    }
}