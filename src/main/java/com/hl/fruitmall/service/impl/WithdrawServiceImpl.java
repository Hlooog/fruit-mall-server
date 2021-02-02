package com.hl.fruitmall.service.impl;

import com.hl.fruitmall.common.enums.WithdrawStatusEnum;
import com.hl.fruitmall.common.uitls.GlobalUtils;
import com.hl.fruitmall.common.uitls.R;
import com.hl.fruitmall.entity.vo.BalanceVO;
import com.hl.fruitmall.entity.vo.WithdrawRecordVO;
import com.hl.fruitmall.mapper.BalanceMapper;
import com.hl.fruitmall.mapper.WithdrawMapper;
import com.hl.fruitmall.service.WithdrawService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @author Hl
 * @create 2021/2/2 22:56
 */
@Service("withdrawService")
public class WithdrawServiceImpl implements WithdrawService {

    @Autowired
    private WithdrawMapper withdrawMapper;

    @Autowired
    private BalanceMapper balanceMapper;

    @Autowired
    private GlobalUtils globalUtils;

    @Override
    public R page(String phone, Integer cur, String startTime, String endTime, Integer status) {
        Date[] dates = globalUtils.strToDate(startTime, endTime);
        Date start = dates[0], end = dates[1];
        List<WithdrawRecordVO> list = withdrawMapper.page(phone, (cur - 1) * 10, start, end, status);
        Integer total = withdrawMapper.getTotal(phone, start, end);
        return R.ok(new HashMap<String, Object>() {
            {
                put("data", list);
                put("total", total);
            }
        });
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public R review(Integer id, String phone) {
        BalanceVO balanceVO = balanceMapper.select(phone);
        WithdrawRecordVO withdraw = withdrawMapper.selectByFiled("id", id);
        balanceVO.setFrozen(balanceVO.getFrozen().subtract(withdraw.getAmount()));
        balanceVO.setWithdraw(balanceVO.getWithdraw().add(withdraw.getAmount()));
        withdrawMapper.updateById(id, WithdrawStatusEnum.FINISH.getCode());
        balanceMapper.update(phone, balanceVO);
        return R.ok();
    }

    @Override
    public R refuse(Integer id, String phone) {
        BalanceVO balanceVO = balanceMapper.select(phone);
        WithdrawRecordVO withdraw = withdrawMapper.selectByFiled("id", id);
        balanceVO.setFrozen(balanceVO.getFrozen().subtract(withdraw.getAmount()));
        balanceVO.setWithdrawAble(balanceVO.getWithdrawAble().add(withdraw.getAmount()));
        withdrawMapper.updateById(id, WithdrawStatusEnum.REFUSE.getCode());
        balanceMapper.update(phone, balanceVO);
        return R.ok();
    }
}
