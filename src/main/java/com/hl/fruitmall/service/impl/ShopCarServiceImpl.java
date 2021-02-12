package com.hl.fruitmall.service.impl;

import com.hl.fruitmall.common.uitls.R;
import com.hl.fruitmall.common.uitls.TokenUtils;
import com.hl.fruitmall.entity.vo.CreateCarVO;
import com.hl.fruitmall.entity.vo.ShopCarVO;
import com.hl.fruitmall.mapper.ShopCarMapper;
import com.hl.fruitmall.service.ShopCarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 购物车表(ShopCar)表服务实现类
 *
 * @author hl
 * @since 2020-11-21 00:33:40
 */
@Service("shopCarService")
public class ShopCarServiceImpl implements ShopCarService {

    @Autowired
    private ShopCarMapper shopCarMapper;

    @Override
    public R list(HttpServletRequest request) {
        Integer id = TokenUtils.getId(request);
        List<ShopCarVO> list = shopCarMapper.list(id);
        Map<Integer, List<ShopCarVO>> map = new HashMap<>();
        for (ShopCarVO vo : list) {
            List<ShopCarVO> innerList = map.getOrDefault(vo.getShopId(), new ArrayList<>());
            innerList.add(vo);
            map.put(vo.getShopId(),innerList);
        }
        return R.ok(map);
    }

    @Override
    public R create(CreateCarVO carVO) {
        shopCarMapper.insert(carVO);
        return R.ok();
    }

    @Override
    public R increase(Integer id, HttpServletRequest request) {
        Integer userId = TokenUtils.getId(request);
        shopCarMapper.updateQuantity(id,userId, 1);
        return R.ok();
    }

    @Override
    public R decrease(Integer id, HttpServletRequest request) {
        Integer userId = TokenUtils.getId(request);
        shopCarMapper.updateQuantity(id, userId,-1);
        return R.ok();
    }

    @Override
    public R moveOut(Integer id, HttpServletRequest request) {
        Integer userId = TokenUtils.getId(request);
        shopCarMapper.delete(id,userId);
        return R.ok();
    }
}