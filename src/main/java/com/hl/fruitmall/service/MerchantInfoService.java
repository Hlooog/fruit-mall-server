package com.hl.fruitmall.service;

import com.hl.fruitmall.common.uitls.R;
import com.hl.fruitmall.entity.vo.ApplyMerchantVO;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Hl
 * @create 2021/1/25 22:29
 */
public interface MerchantInfoService {

    R review(Integer id);

    R getListReview(Integer cur, Integer isReview);

    R refuse(Integer id);

    R apply(ApplyMerchantVO vo);

    R get(HttpServletRequest request);

}
