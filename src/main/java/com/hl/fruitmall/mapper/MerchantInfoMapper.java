package com.hl.fruitmall.mapper;

import com.hl.fruitmall.entity.vo.MerchantVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Hl
 * @create 2021/1/25 21:51
 */
public interface MerchantInfoMapper {
    void updateByField(@Param("id") Integer id, @Param("field") String field, @Param("value") Object value);

    List<MerchantVO> getList(@Param("cur") Integer cur, @Param("status") Integer status);

    Integer getTotal(@Param("status") Integer status);
}
