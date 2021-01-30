package com.hl.fruitmall.service.impl;

import com.hl.fruitmall.common.enums.ReviewStatusEnum;
import com.hl.fruitmall.common.enums.RoleEnum;
import com.hl.fruitmall.common.uitls.R;
import com.hl.fruitmall.mapper.MerchantInfoMapper;
import com.hl.fruitmall.entity.vo.ApplyVO;
import com.hl.fruitmall.mapper.UserMapper;
import com.hl.fruitmall.service.MerchantInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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

    @Autowired
    private UserMapper userMapper;

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public R review(Integer id) {
        merchantInfoMapper.updateByField("user_id",id,"status", ReviewStatusEnum.REVIEWED.getCode());
        userMapper.updateByField("id", id, "role_type", RoleEnum.MERCHANT.getCode());
        return R.ok();
    }

    @Override
    public R getListReview(Integer cur,Integer status) {
        List<ApplyVO> list = merchantInfoMapper.getList((cur - 1) * 10,status);
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
        merchantInfoMapper.updateByField("id",id, "status", ReviewStatusEnum.REFUSE.getCode());
        return R.ok();
    }

}
