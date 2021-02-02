package com.hl.fruitmall.mapper;

import com.hl.fruitmall.entity.bean.Withdraw;
import com.hl.fruitmall.entity.vo.WithdrawRecordVO;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * 提现记录表(Withdraw)表数据库访问层
 *
 * @author hl
 * @since 2020-11-21 00:33:48
 */
public interface WithdrawMapper {

    void insert(@Param("bo") Withdraw withdraw);

    List<WithdrawRecordVO> page(@Param("phone") String phone,
                                @Param("cur") Integer cur,
                                @Param("start") Date start,
                                @Param("end") Date end,
                                @Param("status") Integer status);

    Integer getTotal(@Param("phone") String phone,
                     @Param("start") Date start,
                     @Param("end") Date end);

    WithdrawRecordVO selectByFiled(@Param("field") String field,
                                   @Param("value") Object value);

    void updateById(@Param("id") Integer id, @Param("code") Integer code);
}