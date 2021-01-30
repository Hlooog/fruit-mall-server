package com.hl.fruitmall.service.impl;

import com.hl.fruitmall.common.uitls.GlobalUtils;
import com.hl.fruitmall.common.uitls.R;
import com.hl.fruitmall.entity.vo.WithdrawRecordVO;
import com.hl.fruitmall.mapper.WithdrawalRecordMapper;
import com.hl.fruitmall.service.WithdrawalRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * 提现记录表(WithdrawalRecord)表服务实现类
 *
 * @author hl
 * @since 2020-11-21 00:33:48
 */
@Service("withdrawalRecordService")
public class WithdrawalRecordServiceImpl implements WithdrawalRecordService {

    @Autowired
    private WithdrawalRecordMapper withdrawalRecordMapper;

    @Autowired
    private GlobalUtils globalUtils;

    @Override
    public R page(String phone, Integer cur, String startTime, String endTime) {
        Date[] dates = globalUtils.strToDate(startTime, endTime);
        Date start = dates[0], end = dates[1];
        List<WithdrawRecordVO> list = withdrawalRecordMapper.page(phone, (cur - 1) * 10,start,end);
        Integer total = withdrawalRecordMapper.getTotal(phone,start,end);
        return R.ok(new HashMap<String,Object>(){
            {
                put("data", list);
                put("total", total);
            }
        });
    }
}