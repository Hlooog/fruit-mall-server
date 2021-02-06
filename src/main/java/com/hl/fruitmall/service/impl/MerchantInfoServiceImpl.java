package com.hl.fruitmall.service.impl;

import com.auth0.jwt.JWT;
import com.hl.fruitmall.common.enums.ExceptionEnum;
import com.hl.fruitmall.common.enums.ReviewStatusEnum;
import com.hl.fruitmall.common.enums.RoleEnum;
import com.hl.fruitmall.common.exception.GlobalException;
import com.hl.fruitmall.common.uitls.R;
import com.hl.fruitmall.config.RabbitConfig;
import com.hl.fruitmall.entity.bean.MerchantInfo;
import com.hl.fruitmall.entity.vo.ApplyMerchantVO;
import com.hl.fruitmall.entity.vo.ApplyVO;
import com.hl.fruitmall.mapper.MerchantInfoMapper;
import com.hl.fruitmall.mapper.UserMapper;
import com.hl.fruitmall.service.MerchantInfoService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
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

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public R review(Integer id) {
        merchantInfoMapper.updateByField("user_id", id, "status", ReviewStatusEnum.REVIEWED.getCode());
        userMapper.updateByField("id", id, "role_type", RoleEnum.MERCHANT.getCode());
        return R.ok();
    }

    @Override
    public R getListReview(Integer cur, Integer status) {
        List<ApplyVO> list = merchantInfoMapper.getList((cur - 1) * 10, status);
        Integer total = merchantInfoMapper.getTotal(status);
        return R.ok(new HashMap<String, Object>() {
            {
                put("data", list);
                put("total", total);
            }
        });
    }

    @Override
    public R refuse(Integer id) {
        merchantInfoMapper.updateByField("id", id, "status", ReviewStatusEnum.REFUSE.getCode());
        return R.ok();
    }

    @Override
    public R apply(ApplyMerchantVO vo) {
        MerchantInfo info = merchantInfoMapper.select(vo.getId(),vo.getIdCard());
        if (info != null) {
            vo.getUrlList().add(vo.getNegative());
            vo.getUrlList().add(vo.getPositive());
            rabbitTemplate.convertAndSend(RabbitConfig.ROUTING_OSS_DELETE,
                    RabbitConfig.ROUTING_OSS_DELETE, vo.getUrlList());
            throw new GlobalException(ExceptionEnum.REPEAT_APPLICATION);
        }
        merchantInfoMapper.insert(vo);
        if (vo.getUrlList()!= null && vo.getUrlList().size() > 0) {
            rabbitTemplate.convertAndSend(RabbitConfig.ROUTING_OSS_DELETE,
                    RabbitConfig.ROUTING_OSS_DELETE, vo.getUrlList());
        }
        return R.ok();
    }

    @Override
    public R get(HttpServletRequest request) {
        Integer id = Integer.valueOf(JWT.decode(request.getHeader("X-Token")).getAudience().get(0));
        Integer status = merchantInfoMapper.selectStatus(id);
        return R.ok(status);
    }

}
