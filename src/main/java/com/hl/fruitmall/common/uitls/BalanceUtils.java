package com.hl.fruitmall.common.uitls;

import com.hl.fruitmall.common.enums.ExceptionEnum;
import com.hl.fruitmall.common.enums.WithdrawStatusEnum;
import com.hl.fruitmall.common.exception.GlobalException;
import com.hl.fruitmall.entity.bean.Withdraw;
import com.hl.fruitmall.entity.vo.BalanceVO;
import com.hl.fruitmall.entity.vo.UserOrderInfoVO;
import com.hl.fruitmall.entity.vo.WithdrawVO;
import com.hl.fruitmall.mapper.BalanceMapper;
import com.hl.fruitmall.mapper.WithdrawMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Hl
 * @create 2021/2/15 15:42
 */
@Component
public class BalanceUtils {

    @Autowired
    private BalanceMapper balanceMapper;

    @Autowired
    private WithdrawMapper withdrawMapper;

    @Resource(name = "taskExecutor")
    private Executor taskExecutor;

    public static WeakHashMap<Integer, ReentrantLock> map = new WeakHashMap<>();

    public static ReentrantLock reentrantLock = new ReentrantLock();

    private static ReentrantLock getLock(Integer shopId) {
        try {
            reentrantLock.lock();
            ReentrantLock lock = map.get(shopId);
            if (lock == null) {
                lock = new ReentrantLock();
                map.put(shopId, lock);
            }
            return lock;
        } finally {
            reentrantLock.unlock();
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void increaseFrozen(List<UserOrderInfoVO> infoList) {
        Map<Integer, BigDecimal> priceMap = new HashMap<>();
        infoList.stream().forEach(info -> {
            BigDecimal tmp = priceMap.getOrDefault(info.getShopId(), new BigDecimal(0));
            tmp = tmp.add(info.getPrice().multiply(new BigDecimal(info.getQuantity())));
            priceMap.put(info.getShopId(), tmp);
        });
        priceMap.forEach((k, v) -> {
            ReentrantLock lock = getLock(k);
            taskExecutor.execute( () -> {
                try {
                    lock.lock();
                    balanceMapper.increaseFrozen(k, v);
                } finally {
                    lock.unlock();
                }
            });
        });
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void withdraw(WithdrawVO withdrawVO) {
        BalanceVO balanceVO = balanceMapper.select(withdrawVO.getPhone());
        if (withdrawVO.getAmount().compareTo(balanceVO.getWithdrawAble()) == 1) {
            throw new GlobalException(ExceptionEnum.WITHDRAW_THAN_WITHDRAWABLE);
        }
        ReentrantLock lock = getLock(balanceVO.getShopId());
        try {
            lock.lock();
            balanceVO.setWithdrawAble(balanceVO.getWithdrawAble().subtract(withdrawVO.getAmount()));
            balanceVO.setFrozen(balanceVO.getFrozen().add(withdrawVO.getAmount()));
            balanceMapper.update(withdrawVO.getPhone(), balanceVO);
        } finally {
            lock.unlock();
        }
        Withdraw withdraw = new Withdraw(
                withdrawVO.getPhone(),
                withdrawVO.getAccount(),
                WithdrawStatusEnum.REVIEW.getCode(),
                withdrawVO.getAmount()
        );
        withdrawMapper.insert(withdraw);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void decreaseFrozen(Integer shopId, BigDecimal price) {
        ReentrantLock lock = getLock(shopId);
        try {
            lock.lock();
            balanceMapper.decreaseFrozen(shopId, price);
        } finally {
            lock.unlock();
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void increaseWithdrawAble(Integer shopId, BigDecimal price) {
        ReentrantLock lock = getLock(shopId);
        try {
            lock.lock();
            balanceMapper.increaseWithdrawAble(shopId,price);
        } finally {
            lock.unlock();
        }
    }
}
