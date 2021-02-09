package com.hl.fruitmall.service;

import com.hl.fruitmall.common.uitls.R;
import com.hl.fruitmall.entity.bean.Address;

import javax.servlet.http.HttpServletRequest;

/**
 * 收货信息表(Address)表服务接口
 *
 * @author hl
 * @since 2020-11-21 00:33:33
 */
public interface AddressService {

    R list(HttpServletRequest request);

    R add(Address address, HttpServletRequest request);

    R edit(Address address);

    R delete(Integer id);
}