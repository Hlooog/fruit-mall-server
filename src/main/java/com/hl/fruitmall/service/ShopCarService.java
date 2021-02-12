package com.hl.fruitmall.service;

import com.hl.fruitmall.common.uitls.R;
import com.hl.fruitmall.entity.vo.CreateCarVO;

import javax.servlet.http.HttpServletRequest;

/**
 * 购物车表(ShopCar)表服务接口
 *
 * @author hl
 * @since 2020-11-21 00:33:40
 */
public interface ShopCarService {

    R list(HttpServletRequest request);

    R create(CreateCarVO carVO);

    R increase(Integer id, HttpServletRequest request);

    R decrease(Integer id, HttpServletRequest request);

    R moveOut(Integer id, HttpServletRequest request);

}