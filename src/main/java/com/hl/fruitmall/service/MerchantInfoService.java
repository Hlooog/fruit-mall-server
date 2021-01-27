package com.hl.fruitmall.service;

import com.hl.fruitmall.common.uitls.R;

/**
 * @author Hl
 * @create 2021/1/25 22:29
 */
public interface MerchantInfoService {

    R review(Integer id);

    R getListReview(Integer cur,Integer isReview);

    R refuse(Integer id);
}
