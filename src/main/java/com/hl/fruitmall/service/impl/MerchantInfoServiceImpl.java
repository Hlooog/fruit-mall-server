package com.hl.fruitmall.service.impl;

import com.hl.fruitmall.common.enums.ReviewStatusEnums;
import com.hl.fruitmall.common.uitls.R;
import com.hl.fruitmall.mapper.MerchantInfoMapper;
import com.hl.fruitmall.entity.vo.MerchantVO;
import com.hl.fruitmall.service.MerchantInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * @author Hl
 * @create 2021/1/25 22:29
 */
@Service("merchantInfoService")
public class MerchantInfoServiceImpl implements MerchantInfoService {

    @Autowired
    private MerchantInfoMapper merchantInfoMapper;

    @Override
    public R review(Integer id) {
        merchantInfoMapper.updateByField(id,"status", ReviewStatusEnums.ReVIEWED.getCode());
        return R.ok();
    }

    @Override
    public R getListReview(Integer cur,Integer status) {
        List<MerchantVO> list = merchantInfoMapper.getList((cur - 1) * 10,status);
        Integer total = merchantInfoMapper.getTotal(status);
        return R.ok(new HashMap<String,Object>(){
            {
                put("data", list);
                put("total", total);
            }
        });
    }

    @Override
    public R refuse(Integer id) {
        merchantInfoMapper.updateByField(id, "status", ReviewStatusEnums.REFUSE.getCode());
        return R.ok();
    }

}
