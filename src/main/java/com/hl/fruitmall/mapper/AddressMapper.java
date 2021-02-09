package com.hl.fruitmall.mapper;

import com.hl.fruitmall.entity.bean.Address;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Hl
 * @create 2021/2/9 16:48
 */
public interface AddressMapper {
    List<Address> list(@Param("id") Integer id);

    Integer insert(@Param("address") Address address);

    void update(@Param("address") Address address);

    void delete(@Param("id") Integer id);
}
