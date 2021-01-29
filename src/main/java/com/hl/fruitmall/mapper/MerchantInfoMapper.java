package com.hl.fruitmall.mapper;

import com.hl.fruitmall.entity.vo.ApplyVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Hl
 * @create 2021/1/25 21:51
 */
public interface MerchantInfoMapper {
    void updateByField(@Param("field1") String field1,
                       @Param("value1") Object value1,
                       @Param("field2") String field2,
                       @Param("value2") Object value2);

    List<ApplyVO> getList(@Param("cur") Integer cur, @Param("status") Integer status);

    Integer getTotal(@Param("status") Integer status);
}
