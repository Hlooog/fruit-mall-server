package com.hl.fruitmall.mapper;

import com.hl.fruitmall.entity.vo.BalanceVO;
import org.apache.ibatis.annotations.Param;

/**
 * 余额表(Balance)表数据库访问层
 *
 * @author hl
 * @since 2020-11-21 00:33:28
 */
public interface BalanceMapper {

    BalanceVO select(@Param("id") Integer id);

    void update(@Param("id") Integer id, @Param("vo") BalanceVO balanceVO);
}