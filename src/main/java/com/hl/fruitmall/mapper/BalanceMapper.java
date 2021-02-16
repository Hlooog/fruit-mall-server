package com.hl.fruitmall.mapper;

import com.hl.fruitmall.entity.vo.BalanceVO;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;

/**
 * 余额表(Balance)表数据库访问层
 *
 * @author hl
 * @since 2020-11-21 00:33:28
 */
public interface BalanceMapper {

    BalanceVO select(@Param("phone") String phone);

    void update(@Param("phone") String phone, @Param("vo") BalanceVO balanceVO);

    void insert(@Param("shopId") Integer id,@Param("phone") String phone);

    void increaseFrozen(@Param("shopId") Integer shopId,@Param("price") BigDecimal price);

    void decreaseFrozen(@Param("shopId") Integer shopId,@Param("price") BigDecimal price);

    void increaseWithdrawAble(@Param("shopId") Integer shopId, @Param("price") BigDecimal price);
}