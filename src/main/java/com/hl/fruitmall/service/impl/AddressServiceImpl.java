package com.hl.fruitmall.service.impl;

import com.hl.fruitmall.common.uitls.R;
import com.hl.fruitmall.common.uitls.TokenUtils;
import com.hl.fruitmall.entity.bean.Address;
import com.hl.fruitmall.mapper.AddressMapper;
import com.hl.fruitmall.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 收货信息表(Address)表服务实现类
 *
 * @author hl
 * @since 2020-11-21 00:33:33
 */
@Service("addressService")
public class AddressServiceImpl implements AddressService {

    @Autowired
    private AddressMapper addressMapper;

    @Override
    public R list(HttpServletRequest request) {
        Integer id = TokenUtils.getId(request);
        List<Address> list = addressMapper.list(id);
        return R.ok(list);
    }

    @Override
    public R add(Address address, HttpServletRequest request) {
        Integer userId = TokenUtils.getId(request);
        address.setUserId(userId);
        Integer id = addressMapper.insert(address);
        return R.ok(id);
    }

    @Override
    public R edit(Address address) {
        addressMapper.update(address);
        return R.ok();
    }

    @Override
    public R delete(Integer id) {
        addressMapper.delete(id);
        return R.ok();
    }
}